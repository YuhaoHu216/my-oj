package space.huyuhao.myojjudge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "space.huyuhao.myojclient")
@ComponentScan(basePackages = {"space.huyuhao.myojjudge", "space.huyuhao.myojcommon"})
public class MyOjJudgeServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyOjJudgeServiceApplication.class, args);
    }
}
