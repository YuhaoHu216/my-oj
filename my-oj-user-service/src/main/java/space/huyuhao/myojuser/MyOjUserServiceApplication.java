package space.huyuhao.myojuser;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "space.huyuhao.myojclient")
@ComponentScan(basePackages = {"space.huyuhao.myojuser", "space.huyuhao.myojcommon"})
@MapperScan("space.huyuhao.myojuser.mapper")
public class MyOjUserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyOjUserServiceApplication.class, args);
    }
}
