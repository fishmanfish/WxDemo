package fishman.fish.wxdemo.service.serviceImp;

import fishman.fish.wxdemo.bean.Test;
import fishman.fish.wxdemo.mapper.BeanMapper;
import fishman.fish.wxdemo.service.BeanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author
 * @Package fishman.fish.wxdemo.service.serviceImp
 * @date 2021/3/24 11:48
 * @Copyright
 */

@Service
@CacheConfig(cacheNames = "beanService")
public class BeanServiceImp implements BeanService {

  @Resource
  private BeanMapper mapper;

  @Override
  @Cacheable(condition = "#id.length() > 0")
  public Test findBeanByID(String id) {
    return mapper.findBeanByID(id);
  }
}
