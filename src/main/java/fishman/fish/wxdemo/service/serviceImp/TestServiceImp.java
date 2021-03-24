package fishman.fish.wxdemo.service.serviceImp;

import fishman.fish.wxdemo.bean.Dept;
import fishman.fish.wxdemo.bean.Test;
import fishman.fish.wxdemo.mapper.TestMapper;
import fishman.fish.wxdemo.service.TestService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author
 * @Package fishman.fish.wxdemo.service.serviceImp
 * @date 2021/3/24 11:48
 * @Copyright
 */

@Service
@CacheConfig(cacheNames = "beanService", keyGenerator = "myKeyGenerator")
public class TestServiceImp implements TestService {

  @Resource
  private TestMapper mapper;

  @Override
  @Cacheable(condition = "#id.length() > 0")
  public Test findTestByID(String id) {
    return mapper.findTestByID(id);
  }
}
