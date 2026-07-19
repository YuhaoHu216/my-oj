# 单体项目拆分为微服务 — 实现计划

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** 将当前单体 my-oj 项目拆分为 7 个 Maven 子模块 + 1 个独立代码沙箱服务，使用 Spring Cloud Alibaba 微服务体系。

**Architecture:** 父 POM 管理 7 个子模块，common/model/service-client 为共享库，gateway 做 API 网关 + 鉴权，user/question/judge 为独立微服务。question-service 通过 RabbitMQ 发送提交消息，judge-service 消费消息后通过 HTTP 调用独立沙箱服务执行代码。服务间通过 OpenFeign + Nacos 通信，Redis 存储分布式 Session。

**Tech Stack:** Spring Boot 2.6.13, Spring Cloud Alibaba 2021.0.5.0, Nacos, OpenFeign + LoadBalancer, Spring Cloud Gateway, RabbitMQ, Redis + Spring Session, Knife4j 网关聚合

**参考源码:**
- 微服务架构: `D:\HYH\Desktop\在线判题体统资料\yuoj-backend-microservice-master`
- 沙箱服务: `D:\HYH\Desktop\在线判题体统资料\每一期单独的源码\yuoj-code-sandbox-seven`

---

## 架构总览

```
my-oj/
├── pom.xml                          (parent: pom packaging, 7 modules)
├── my-oj-common/                    (JAR: 通用类、注解、异常)
├── my-oj-model/                     (JAR: 实体、DTO、VO、枚举、沙箱模型)
├── my-oj-service-client/           (JAR: Feign 接口)
├── my-oj-gateway/                  (Spring Boot: API 网关, port 8101)
├── my-oj-user-service/             (Spring Boot: 用户服务, port 8102)
├── my-oj-question-service/         (Spring Boot: 题目服务, port 8103)
├── my-oj-judge-service/            (Spring Boot: 判题服务, port 8104)
└── my-oj-code-sandbox/             (Spring Boot: 沙箱服务, port 8090)
```

### 模块依赖图

```
my-oj-common ← (no deps)
my-oj-model ← my-oj-common
my-oj-service-client ← my-oj-common, my-oj-model
my-oj-gateway ← my-oj-common, my-oj-model, my-oj-service-client
my-oj-user-service ← my-oj-common, my-oj-model, my-oj-service-client
my-oj-question-service ← my-oj-common, my-oj-model, my-oj-service-client
my-oj-judge-service ← my-oj-common, my-oj-model, my-oj-service-client
my-oj-code-sandbox ← (独立, 无依赖)
```

### 公共包名映射

| 模块 | GroupId | 包名 |
|------|---------|------|
| my-oj-common | space.huyuhao | space.huyuhao.myojcommon |
| my-oj-model | space.huyuhao | space.huyuhao.myojmodel |
| my-oj-service-client | space.huyuhao | space.huyuhao.myojclient |
| my-oj-gateway | space.huyuhao | space.huyuhao.myojgateway |
| my-oj-user-service | space.huyuhao | space.huyuhao.myojuser |
| my-oj-question-service | space.huyuhao | space.huyuhao.myojquestion |
| my-oj-judge-service | space.huyuhao | space.huyuhao.myojjudge |
| my-oj-code-sandbox | space.huyuhao | space.huyuhao.myojsandbox |

---

## Phase 0: 项目根目录重构

### Task 0.1: 重写父 POM

**Files:** 
- Modify: `pom.xml`

将当前单体 pom.xml 改为多模块父 POM，参考资料 `D:\HYH\Desktop\在线判题体统资料\yuoj-backend-microservice-master\pom.xml`

**关键配置:**
- `<packaging>pom</packaging>`
- `<modules>` 包含 7 个子模块
- `<properties>` Spring Boot 2.6.13, Spring Cloud Alibaba 2021.0.5.0, Java 1.8
- `<dependencyManagement>` 管理 Spring Boot + Cloud Alibaba 版本
- 公共依赖: spring-boot-starter-web, mybatis-plus 3.5.2, hutool 5.8.8, gson, commons-lang3, lombok, mysql-connector-j, redis, spring-session-data-redis
- 微服务依赖: nacos-discovery, openfeign, loadbalancer, sentinel

### Task 0.2: 备份旧单体代码

将现有 `src/` 目录重命名为 `src-backup/`，后续逐步迁移代码到各模块。

---

## Phase 1: my-oj-common（公共模块）

### Task 1.1: 创建模块结构和 pom.xml

**Files:**
- Create: `my-oj-common/pom.xml`
- Create: `my-oj-common/src/main/java/space/huyuhao/myojcommon/...`

pom.xml 依赖: spring-boot-starter-web, lombok, commons-lang3

从中移入以下包（保持原有代码不变，仅修改 package 声明）:

### Task 1.2: 迁移 annotation 包

**Files:**
- Create: `my-oj-common/src/main/java/space/huyuhao/myojcommon/annotation/AuthCheck.java`

从 `src-backup/.../annotation/AuthCheck.java` 复制，修改 package 为 `space.huyuhao.myojcommon.annotation`

### Task 1.3: 迁移 common 包

**Files:**
- Create: `BaseResponse.java`, `DeleteRequest.java`, `ErrorCode.java`, `PageRequest.java`, `ResultUtils.java`

package: `space.huyuhao.myojcommon.common`

**ErrorCode 需保留已有的 API_REQUEST_ERROR(50010)**

### Task 1.4: 迁移 config 包

**Files:**
- Create: `my-oj-common/src/main/java/space/huyuhao/myojcommon/config/JsonConfig.java`

### Task 1.5: 迁移 constant 包

**Files:**
- Create: `CommonConstant.java`, `FileConstant.java`, `UserConstant.java`

package: `space.huyuhao.myojcommon.constant`

### Task 1.6: 迁移 exception 包

**Files:**
- Create: `BusinessException.java`, `GlobalExceptionHandler.java`, `ThrowUtils.java`

package: `space.huyuhao.myojcommon.exception`

### Task 1.7: 迁移 utils 包

**Files:**
- Create: `NetUtils.java`, `SqlUtils.java`

package: `space.huyuhao.myojcommon.utils`

---

## Phase 2: my-oj-model（数据模型模块）

### Task 2.1: 创建模块结构和 pom.xml

**Files:**
- Create: `my-oj-model/pom.xml`

pom.xml 依赖: my-oj-common, lombok, mybatis-plus-boot-starter, gson, commons-lang3

### Task 2.2: 迁移 codesandbox 模型

**Files:**
- Create: `my-oj-model/src/main/java/space/huyuhao/myojmodel/model/codesandbox/ExecuteCodeRequest.java`
- Create: `my-oj-model/src/main/java/space/huyuhao/myojmodel/model/codesandbox/ExecuteCodeResponse.java`
- Create: `my-oj-model/src/main/java/space/huyuhao/myojmodel/model/codesandbox/JudgeInfo.java`

从 `src-backup/.../judge/codesandbox/model/` 复制，修改 package。

### Task 2.3: 迁移 dto 包

**Files:**
- `model/dto/question/`: `JudgeCase.java`, `JudgeConfig.java`, `QuestionAddRequest.java`, `QuestionEditRequest.java`, `QuestionQueryRequest.java`, `QuestionUpdateRequest.java`
- `model/dto/questionsubmit/`: `QuestionSubmitAddRequest.java`, `QuestionSubmitQueryRequest.java`
- `model/dto/user/`: `UserAddRequest.java`, `UserLoginRequest.java`, `UserQueryRequest.java`, `UserRegisterRequest.java`, `UserUpdateMyRequest.java`, `UserUpdateRequest.java`
- `model/dto/file/`: `UploadFileRequest.java`

### Task 2.4: 迁移 entity 包

**Files:**
- `model/entity/`: `Question.java`, `QuestionSubmit.java`, `User.java`

### Task 2.5: 迁移 enums 包

**Files:**
- `model/enums/`: `JudgeInfoMessageEnum.java`, `QuestionSubmitLanguageEnum.java`, `QuestionSubmitStatusEnum.java`, `UserRoleEnum.java`, `FileUploadBizEnum.java`

### Task 2.6: 迁移 vo 包

**Files:**
- `model/vo/`: `LoginUserVO.java`, `QuestionSubmitVO.java`, `QuestionVO.java`, `UserVO.java`

**注意:** VO 类中的 import 需指向新的 model 包路径。

---

## Phase 3: my-oj-service-client（Feign 客户端）

### Task 3.1: 创建模块结构和 pom.xml

**Files:**
- Create: `my-oj-service-client/pom.xml`

依赖: my-oj-common, my-oj-model, spring-cloud-starter-openfeign, spring-cloud-loadbalancer

### Task 3.2: 创建 UserFeignClient

**Files:**
- Create: `my-oj-service-client/src/main/java/space/huyuhao/myojclient/service/UserFeignClient.java`

参照 `D:\HYH\Desktop\在线判题体统资料\yuoj-backend-microservice-master\yuoj-backend-service-client\src\main\java\com\yupi\yuojbackendserviceclient\service\UserFeignClient.java`

核心内容:
```java
@FeignClient(name = "my-oj-user-service", path = "/api/user/inner")
public interface UserFeignClient {
    @GetMapping("/get/id")
    User getById(@RequestParam("userId") long userId);
    
    @GetMapping("/get/ids")
    List<User> listByIds(@RequestParam("idList") Collection<Long> idList);
    
    // 默认方法: getLoginUser, isAdmin, getUserVO
}
```

### Task 3.3: 创建 QuestionFeignClient

**Files:**
- Create: `my-oj-service-client/src/main/java/space/huyuhao/myojclient/service/QuestionFeignClient.java`

```java
@FeignClient(name = "my-oj-question-service", path = "/api/question/inner")
public interface QuestionFeignClient {
    @GetMapping("/get/id")
    Question getQuestionById(@RequestParam("questionId") long questionId);
    
    @GetMapping("/question_submit/get/id")
    QuestionSubmit getQuestionSubmitById(@RequestParam("questionSubmitId") long questionSubmitId);
    
    @PostMapping("/question_submit/update")
    boolean updateQuestionSubmitById(@RequestBody QuestionSubmit questionSubmit);
}
```

### Task 3.4: 创建 JudgeFeignClient

**Files:**
- Create: `my-oj-service-client/src/main/java/space/huyuhao/myojclient/service/JudgeFeignClient.java`

```java
@FeignClient(name = "my-oj-judge-service", path = "/api/judge/inner")
public interface JudgeFeignClient {
    @PostMapping("/do")
    QuestionSubmit doJudge(@RequestParam("questionSubmitId") long questionSubmitId);
}
```

---

## Phase 4: my-oj-gateway（API 网关）

### Task 4.1: 创建模块结构和 pom.xml

**Files:**
- Create: `my-oj-gateway/pom.xml`
- Create: `my-oj-gateway/src/main/java/space/huyuhao/myojgateway/...`

pom.xml 依赖: spring-cloud-starter-gateway, nacos-discovery, loadbalancer, my-oj-common, my-oj-model, spring-boot-starter-data-redis
**注意:** gateway 使用 spring-cloud-starter-gateway 而非 spring-boot-starter-web（Reactive 模式）

### Task 4.2: 创建启动类

**Files:**
- Create: `MyOjGatewayApplication.java`

### Task 4.3: 创建 Gateway 配置

**Files:**
- Create: `application.yml` (port: 8101)

路由配置:
```yaml
spring:
  cloud:
    nacos:
      discovery:
        server-addr: 127.0.0.1:8848
    gateway:
      routes:
        - id: my-oj-user-service
          uri: lb://my-oj-user-service
          predicates: - Path=/api/user/**
        - id: my-oj-question-service
          uri: lb://my-oj-question-service
          predicates: - Path=/api/question/**
        - id: my-oj-judge-service
          uri: lb://my-oj-judge-service
          predicates: - Path=/api/judge/**
  main:
    web-application-type: reactive
server:
  port: 8101
```

Knife4j 网关聚合配置。

### Task 4.4: 创建全局鉴权过滤器

**Files:**
- Create: `config/CorsConfig.java`
- Create: `filter/GlobalAuthFilter.java`

GlobalAuthFilter 实现 GlobalFilter，从 Redis Session 校验登录态。公开路径直接放行:
- `/api/user/login`, `/api/user/register`
- `/api/user/get/login` (获取登录信息)
- Knife4j 文档路径

### Task 4.5: 迁移 AOP 鉴权 (AuthInterceptor)

**Files:**
- Create: `aop/AuthInterceptor.java`

从旧项目迁移 AuthInterceptor（`@Aspect` + `@Around("@annotation(authCheck)")`），但不需要放到 gateway。此文件应放在 **my-oj-common** 中，随 common 模块被各服务引用。

---

## Phase 5: my-oj-user-service（用户服务）

### Task 5.1: 创建模块结构和 pom.xml

**Files:**
- Create: `my-oj-user-service/pom.xml`
- 依赖: my-oj-common, my-oj-model, my-oj-service-client, mybatis-plus, mysql, redis, session-data-redis

### Task 5.2: 创建启动类 + 配置

**Files:**
- Create: `MyOjUserServiceApplication.java` (package: `space.huyuhao.myojuser`)
- Create: `resources/application.yml` (port: 8102, context-path: /api/user, nacos discovery)

```java
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "space.huyuhao.myojclient")
@MapperScan("space.huyuhao.myojuser.mapper")
public class MyOjUserServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyOjUserServiceApplication.class, args);
    }
}
```

### Task 5.3: 迁移 Mapper

**Files:**
- Create: `mapper/UserMapper.java` — 从旧项目迁移，修改 package

### Task 5.4: 迁移 Service 层

**Files:**
- Create: `service/UserService.java`, `service/impl/UserServiceImpl.java`
- 从 `src-backup/.../service/impl/UserServiceImpl.java` 迁移

### Task 5.5: 创建 Controller

**Files:**
- Create: `controller/UserController.java`

从旧项目迁移，但改为通过 `UserFeignClient` 获取用户（因为现在需要跨服务），实际上用户服务内部可以直接用 UserService。

**关键:** 使用 `HttpServletRequest` 参数获取 Session 中的登录用户。

### Task 5.6: 创建内部 Feign Controller

**Files:**
- Create: `controller/inner/UserInnerController.java`

```java
@RestController
@RequestMapping("/inner")
public class UserInnerController implements UserFeignClient {
    @Resource
    private UserService userService;
    
    @Override
    @GetMapping("/get/id")
    public User getById(@RequestParam("userId") long userId) {
        return userService.getById(userId);
    }
    
    @Override
    @GetMapping("/get/ids")
    public List<User> listByIds(@RequestParam("idList") Collection<Long> idList) {
        return userService.listByIds(idList);
    }
}
```

### Task 5.7: 创建 MyBatisPlus 配置

**Files:**
- Create: `config/MyBatisPlusConfig.java`

---

## Phase 6: my-oj-question-service（题目服务）

### Task 6.1: 创建模块结构和 pom.xml

**Files:**
- Create: `my-oj-question-service/pom.xml`
- 依赖: common, model, service-client, mybatis-plus, mysql, redis, rabbitmq

### Task 6.2: 创建启动类 + 配置

**Files:**
- Create: `MyOjQuestionServiceApplication.java`
- Create: `application.yml` (port: 8103, context-path: /api/question, rabbitmq config)

### Task 6.3: 迁移 Mapper

**Files:**
- Create: `mapper/QuestionMapper.java`, `mapper/QuestionSubmitMapper.java`

### Task 6.4: 迁移 Service 层

**Files:**
- Create: `service/QuestionService.java`, `service/QuestionSubmitService.java`
- Create: `service/impl/QuestionServiceImpl.java`, `service/impl/QuestionSubmitServiceImpl.java`

**QuestionSubmitServiceImpl 关键变更:**
- 提交后不再直接调用 `CompletableFuture.runAsync(judgeService.doJudge)`，改为通过 **RabbitMQ** 发送消息:
```java
myMessageProducer.sendMessage("code_exchange", "my_routingKey", String.valueOf(questionSubmitId));
```
- 使用 `UserFeignClient` 替代直接注入 `UserService`

### Task 6.5: 创建 Controller

**Files:**
- Create: `controller/QuestionController.java`

从 `src-backup/.../controller/QuestionController.java` 完整迁移（已包含提交接口）。

### Task 6.6: 创建内部 Feign Controller

**Files:**
- Create: `controller/inner/QuestionInnerController.java`

```java
@RestController
@RequestMapping("/inner")
public class QuestionInnerController implements QuestionFeignClient {
    // getQuestionById, getQuestionSubmitById, updateQuestionSubmitById
}
```

### Task 6.7: 创建 RabbitMQ 生产者

**Files:**
- Create: `rabbitmq/MyMessageProducer.java`

```java
@Component
public class MyMessageProducer {
    @Resource
    private RabbitTemplate rabbitTemplate;
    
    public void sendMessage(String exchange, String routingKey, String message) {
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
    }
}
```

---

## Phase 7: my-oj-judge-service（判题服务）

### Task 7.1: 创建模块结构和 pom.xml

**Files:**
- Create: `my-oj-judge-service/pom.xml`
- 依赖: common, model, service-client, mybatis-plus, mysql, redis, rabbitmq

### Task 7.2: 创建启动类 + 配置

**Files:**
- Create: `MyOjJudgeServiceApplication.java`
- Create: `application.yml` (port: 8104, context-path: /api/judge, codesandbox.type: remote)

### Task 7.3: 迁移判题模块全部代码

**Files (从 src-backup 迁移到 my-oj-judge-service):**
- Create: `judge/JudgeService.java`
- Create: `judge/JudgeServiceImpl.java`
- Create: `judge/JudgeManager.java`
- Create: `judge/codesandbox/CodeSandbox.java`
- Create: `judge/codesandbox/CodeSandboxFactory.java`
- Create: `judge/codesandbox/CodeSandboxProxy.java`
- Create: `judge/codesandbox/impl/ExampleCodeSandbox.java`
- Create: `judge/codesandbox/impl/RemoteCodeSandbox.java`
- Create: `judge/codesandbox/impl/ThirdPartyCodeSandbox.java`
- Create: `judge/strategy/JudgeStrategy.java`
- Create: `judge/strategy/JudgeContext.java`
- Create: `judge/strategy/DefaultJudgeStrategy.java`
- Create: `judge/strategy/JavaLanguageJudgeStrategy.java`

**关键变更:** 
- 所有 import 改为引用 my-oj-model 和 my-oj-common 中的类
- JudgeServiceImpl 中通过 `QuestionFeignClient` 获取题目和提交信息，而非直接调用 Mapper/Service

### Task 7.4: 创建内部 Feign Controller

**Files:**
- Create: `controller/inner/JudgeInnerController.java`

```java
@RestController
@RequestMapping("/inner")
public class JudgeInnerController implements JudgeFeignClient {
    @Resource
    private JudgeService judgeService;
    
    @Override
    @PostMapping("/do")
    public QuestionSubmit doJudge(@RequestParam("questionSubmitId") long questionSubmitId) {
        return judgeService.doJudge(questionSubmitId);
    }
}
```

### Task 7.5: 创建 RabbitMQ 消费者

**Files:**
- Create: `rabbitmq/InitRabbitMq.java` — 声明队列和交换机
- Create: `rabbitmq/InitRabbitMqBean.java` — 初始化 RabbitMQ Bean
- Create: `rabbitmq/MyMessageConsumer.java`

```java
@Component
@Slf4j
public class MyMessageConsumer {
    @Resource
    private JudgeService judgeService;
    
    @SneakyThrows
    @RabbitListener(queues = {"code_queue"}, ackMode = "MANUAL")
    public void receiveMessage(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) {
        long questionSubmitId = Long.parseLong(message);
        try {
            judgeService.doJudge(questionSubmitId);
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            channel.basicNack(deliveryTag, false, false);
        }
    }
}
```

### Task 7.6: 改造 JudgeServiceImpl 适配微服务

**关键改造:**
- 注入 `QuestionFeignClient` 替代 `QuestionService` + `QuestionSubmitService`
- 注入 `UserFeignClient` 替代 `UserService`（如果需要）
- 通过 Feign 调用 question-service 获取题目信息、提交信息、更新提交记录

---

## Phase 8: my-oj-code-sandbox（独立沙箱服务）

### Task 8.1: 创建独立项目

**Files:**
- Create: `my-oj-code-sandbox/pom.xml`
- Create: `my-oj-code-sandbox/src/main/java/space/huyuhao/myojsandbox/...`

参考: `D:\HYH\Desktop\在线判题体统资料\每一期单独的源码\yuoj-code-sandbox-seven`

pom.xml 依赖: spring-boot-starter-web, lombok, hutool, docker-java 3.3.0

### Task 8.2: 迁移沙箱模型

**Files:**
- Create: `model/ExecuteCodeRequest.java`
- Create: `model/ExecuteCodeResponse.java`
- Create: `model/ExecuteMessage.java`
- Create: `model/JudgeInfo.java`

此处的 model 是沙箱服务内部的独立模型（与 my-oj-model 中的沙箱模型结构相同但独立，因为沙箱服务是独立进程）。

### Task 8.3: 迁移沙箱核心代码

**Files:**
- Create: `CodeSandbox.java` (接口)
- Create: `JavaNativeCodeSandbox.java` (原生沙箱)
- Create: `JavaDockerCodeSandbox.java` (Docker 沙箱)
- Create: `controller/MainController.java` (POST /executeCode)
- Create: `utils/ProcessUtils.java`
- Create: `security/MySecurityManager.java`, `DefaultSecurityManager.java`
- Create: `docker/DockerDemo.java`
- Create: `unsafe/*.java` (危险代码测试)

### Task 8.4: 创建沙箱配置

**Files:**
- Create: `resources/application.yml` (port: 8090)
- Create: `resources/security/MySecurityManager.java`

### Task 8.5: 复制测试代码资源

**Files:**
- Create: `resources/testCode/simpleCompute/Main.java`
- Create: `resources/testCode/unsafeCode/*.java`

---

## Phase 9: 清理与验证

### Task 9.1: 删除旧单体源码

`src-backup/` 在验证无遗漏后删除。

### Task 9.2: 全量编译验证

```bash
mvn clean compile -DskipTests
```

### Task 9.3: 验证各模块启动

依次启动: Nacos → Redis → RabbitMQ → 沙箱服务 → 各微服务 → Gateway

---

## 关键注意事项

1. **包名变更:** 所有 Java 文件的 package 声明和 import 需同步修改为新模块的包路径
2. **Feign 跨服务调用:** Service 层通过 Feign 接口调用其他服务，不再直接注入其他 Service
3. **Gateway 路径:** 网关统一前缀 `/api/`，各服务 context-path 为 `/api/user`, `/api/question`, `/api/judge`
4. **Session 共享:** 所有服务通过 Redis 共享 Session，Gateway 做全局鉴权，各服务也可独立校验
5. **RabbitMQ:** question-service 是生产者，judge-service 是消费者，需提前声明 `code_exchange` + `code_queue`
6. **沙箱服务独立:** 不注册到 Nacos，通过固定 URL `localhost:8090` 调用
