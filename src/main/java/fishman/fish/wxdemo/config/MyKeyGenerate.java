package fishman.fish.wxdemo.config;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author
 * @Package fishman.fish.wxdemo.config
 * @date 2021/3/24 17:15
 * @Copyright
 * 定义缓存key生成策略
 */
@Configuration
public class MyKeyGenerate {

  @Bean("myKeyGenerator")
  public KeyGenerator myKeyGenerator(){
    return new KeyGenerator() {
      @Override
      public Object generate(Object target, Method method, Object... params) {
        return method.getName() + Arrays.asList(params).toString();
      }
    };
  }
}
