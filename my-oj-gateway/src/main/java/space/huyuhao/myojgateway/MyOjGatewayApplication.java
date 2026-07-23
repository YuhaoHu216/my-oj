package space.huyuhao.myojgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {"space.huyuhao.myojgateway", "space.huyuhao.myojcommon"})
public class MyOjGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyOjGatewayApplication.class, args);
    }
}
