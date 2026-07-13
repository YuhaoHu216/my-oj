package space.huyuhao.myoj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("space.huyuhao.myoj.mapper")
public class MyOjApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyOjApplication.class, args);
    }

}
