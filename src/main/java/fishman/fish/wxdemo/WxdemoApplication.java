package fishman.fish.wxdemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@MapperScan("fishman.fish.wxdemo.mapper")
@EnableCaching
@SpringBootApplication
public class WxdemoApplication {

  public static void main(String[] args) {
    SpringApplication.run(WxdemoApplication.class, args);
  }

}
