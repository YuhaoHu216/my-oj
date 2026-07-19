package space.huyuhao.myojquestion;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "space.huyuhao.myojclient")
@ComponentScan(basePackages = {"space.huyuhao.myojquestion", "space.huyuhao.myojcommon"})
@MapperScan("space.huyuhao.myojquestion.mapper")
public class MyOjQuestionServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyOjQuestionServiceApplication.class, args);
    }
}
