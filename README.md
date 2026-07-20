# My-OJ 在线判题系统

基于 **Spring Cloud Alibaba** 微服务架构的在线判题系统，支持代码提交、自动判题、用户管理等功能。

## 项目结构

| 模块 | 端口 | 说明 |
|------|------|------|
| `my-oj-gateway` | 8101 | API 网关，统一入口，路由转发，全局鉴权 |
| `my-oj-user-service` | 8102 | 用户服务，注册/登录/鉴权（JWT），Session 管理 |
| `my-oj-question-service` | 8103 | 题目服务，题目 CRUD、提交管理，通过 RabbitMQ 异步发送判题请求 |
| `my-oj-judge-service` | 8104 | 判题服务，消费 RabbitMQ 判题消息，调用代码沙箱执行判题逻辑 |
| `my-oj-code-sandbox` | 8090 | 代码沙箱，独立运行，接收代码并在安全隔离环境中编译执行 |
| `my-oj-common` | — | 公共模块，通用响应类、错误码、异常处理、工具类、权限注解 |
| `my-oj-model` | — | 模型模块，实体类（User/Question/QuestionSubmit）、DTO、VO、枚举 |
| `my-oj-service-client` | — | 服务调用客户端，Feign 远程调用接口定义 |

### 模块职责速览

```
用户请求 → Gateway (路由 + 鉴权)
              ├── /api/user/**     → user-service (用户注册/登录)
              ├── /api/question/** → question-service (题目管理/提交)
              └── /api/judge/**    → judge-service (判题)
                                       ↓ HTTP 调用
                                   code-sandbox (编译执行代码)
              ↑ 异步解耦：question-service → RabbitMQ → judge-service
```

## 技术栈

| 类别 | 技术 | 版本 |
|------|------|------|
| 基础框架 | Spring Boot | 2.6.13 |
| 微服务 | Spring Cloud | 2021.0.5 |
| 微服务 | Spring Cloud Alibaba | 2021.0.5.0 |
| 注册中心 | Nacos | — |
| 熔断降级 | Sentinel | — |
| 网关 | Spring Cloud Gateway | — |
| ORM | MyBatis-Plus | 3.5.2 |
| 数据库 | MySQL | — |
| 缓存/Session | Redis + Spring Session | — |
| 消息队列 | RabbitMQ | — |
| 鉴权 | JWT (jjwt) | 0.12.6 |
| API 文档 | Knife4j (Swagger) | — |
| 代码沙箱 | Docker | — |
| 工具库 | Hutool | 5.8.8 |
| 工具库 | Lombok | 1.18.36 |
| JSON | Gson | 2.9.1 |

## 环境准备

### 1. 基础环境

- **JDK 1.8+**
- **Maven 3.6+**
- **Docker**（代码沙箱需要，仅 `my-oj-code-sandbox` 模块依赖）

### 2. 中间件（必须）

项目启动前请确保以下中间件已安装并运行：

| 中间件 | 默认地址 | 用途 |
|--------|----------|------|
| MySQL | `localhost:3306` | 数据持久化 |
| Redis | `localhost:6379` | 缓存 + Session 共享 |
| Nacos | `127.0.0.1:8848` | 服务注册与发现 |
| RabbitMQ | `localhost:5672` | 判题消息异步解耦 |

### 3. MySQL 初始化

创建数据库并导入初始化脚本（如有）：

```sql
CREATE DATABASE IF NOT EXISTS myoj DEFAULT CHARACTER SET utf8mb4;
```

各服务使用同一数据库 `myoj`，通过 MyBatis-Plus 自动建表（也可手动执行 DDL）。

### 4. Nacos 配置

确保 Nacos 运行在 `127.0.0.1:8848`，各服务启动后会自动注册。

> **注意**：Gateway 的 Nacos 配置使用 `127.0.0.1:8080`，如你的 Nacos 使用默认端口 `8848`，请修改 `my-oj-gateway/src/main/resources/application.yml` 中的 `spring.cloud.nacos.discovery.server-addr`。

### 5. Redis 配置

默认连接 `localhost:6379`，用于：
- Session 共享（跨服务认证）
- 缓存数据

### 6. RabbitMQ 配置

默认连接 `localhost:5672`（guest/guest），用于：
- 题目提交后异步发送判题消息
- 判题服务消费消息并执行判题

## 启动步骤

### 启动顺序（重要）

1. **中间件**：确保 MySQL、Redis、Nacos、RabbitMQ 均已运行
2. **my-oj-code-sandbox**（端口 8090）— 代码沙箱，判题依赖
3. **my-oj-gateway**（端口 8101）— 网关
4. **my-oj-user-service**（端口 8102）
5. **my-oj-question-service**（端口 8103）
6. **my-oj-judge-service**（端口 8104）

### 启动方式

**方式一：IDE 启动**

在 IntelliJ IDEA 中分别运行各模块的 `Application` 主类：

| 模块 | 主类 |
|------|------|
| my-oj-gateway | `MyOjGatewayApplication` |
| my-oj-user-service | `MyOjUserServiceApplication` |
| my-oj-question-service | `MyOjQuestionServiceApplication` |
| my-oj-judge-service | `MyOjJudgeServiceApplication` |
| my-oj-code-sandbox | `YuojCodeSandboxApplication` |

**方式二：Maven 命令行**

```bash
# 先编译整个项目
mvn clean install -DskipTests

# 分别启动各模块
mvn spring-boot:run -pl my-oj-code-sandbox &
mvn spring-boot:run -pl my-oj-gateway &
mvn spring-boot:run -pl my-oj-user-service &
mvn spring-boot:run -pl my-oj-question-service &
mvn spring-boot:run -pl my-oj-judge-service &
```

## 配置说明

各模块配置文件位于 `src/main/resources/application.yml`，主要配置项：

### Gateway (`my-oj-gateway`)

```yaml
server.port: 8101                              # 网关端口
spring.cloud.nacos.discovery.server-addr        # Nacos 地址（默认 127.0.0.1:8080，注意确认端口）
spring.cloud.gateway.routes                     # 路由规则，按路径前缀转发
```

### 用户服务 (`my-oj-user-service`)

```yaml
server.port: 8102                              # 服务端口
spring.datasource.url                           # 数据库连接
spring.redis.host / port                        # Redis 连接
spring.session.timeout: 2592000                 # Session 超时（30天）
```

### 题目服务 (`my-oj-question-service`)

```yaml
server.port: 8103                              # 服务端口
spring.rabbitmq.*                               # RabbitMQ 连接
```

### 判题服务 (`my-oj-judge-service`)

```yaml
server.port: 8104                              # 服务端口
codesandbox.type: remote                        # 沙箱类型：remote（远程调用）/ example（示例）/ thirdParty（第三方）
spring.rabbitmq.*                               # RabbitMQ 连接
```

### 代码沙箱 (`my-oj-code-sandbox`)

```yaml
server.port: 8090                              # 沙箱端口
auth.request-header: auth                       # 鉴权请求头名称
auth.secret-key: your-secret-key                # 鉴权密钥
```

## 核心业务流程

### 判题流程

```
1. 用户提交代码 → question-service
2. question-service 保存提交记录 → 发送 RabbitMQ 消息
3. judge-service 消费消息 → 调用 code-sandbox 编译执行代码
4. code-sandbox 返回执行结果（耗时、内存、输出）
5. judge-service 执行判题策略（比对输出、检查边界）→ 更新提交状态
```

### 判题策略

- `DefaultJudgeStrategy`：默认判题策略
- `JavaLanguageJudgeStrategy`：Java 语言专用判题策略（针对 Java 运行特性优化）

### 代码沙箱安全

代码沙箱支持多种安全策略：
- **Docker 隔离**：在 Docker 容器中执行用户代码（推荐）
- **SecurityManager**：JVM 安全管理器限制危险操作
- 危险操作拦截：内存溢出、文件读写、进程执行、线程睡眠等

## API 文档

启动 Gateway 后访问 Knife4j 文档：

```
http://localhost:8101/doc.html
```

Gateway 已配置 Knife4j 聚合模式，可在一个页面查看所有服务的 API 文档。

## 项目特性

- ✅ **微服务架构**：Spring Cloud Alibaba，服务独立部署，易于扩展
- ✅ **异步判题**：RabbitMQ 消息队列解耦提交与判题，削峰填谷
- ✅ **网关统一入口**：Spring Cloud Gateway 路由转发 + 全局鉴权
- ✅ **JWT 认证**：无状态 Token 鉴权，Redis Session 共享
- ✅ **代码沙箱隔离**：Docker 容器安全执行用户代码
- ✅ **多种判题策略**：支持按语言切换判题逻辑
- ✅ **API 文档聚合**：Knife4j Gateway 模式统一查看文档
- ✅ **逻辑删除**：MyBatis-Plus 逻辑删除保护数据

## 常见问题

### Q: 启动时 Nacos 连接失败？

确保 Nacos 已启动，且配置中的 `server-addr` 端口正确。Gateway 单独配置为 `127.0.0.1:8080`，其他服务为 `127.0.0.1:8848`，请注意保持一致。

### Q: 判题服务无法连接代码沙箱？

检查 `my-oj-judge-service` 中 `codesandbox.type` 配置是否为 `remote`，并确保 `my-oj-code-sandbox` 已在 `8090` 端口启动。

### Q: 如何切换代码沙箱模式？

修改 `my-oj-judge-service` 的 `application.yml`：

```yaml
codesandbox:
  type: remote       # example | remote | thirdParty
```

### Q: Docker 沙箱未生效？

确保 Docker 已安装并运行，`my-oj-code-sandbox` 模块使用了 `docker-java` 库进行容器管理。

### Q: Maven 编译报 lombok 相关错误？

请确保 IDE 已安装 Lombok 插件，并开启注解处理器（Annotation Processing）。
