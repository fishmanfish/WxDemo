package fishman.fish.wxdemo.service.serviceImp;

import fishman.fish.wxdemo.bean.Dept;
import fishman.fish.wxdemo.mapper.DeptMapper;
import fishman.fish.wxdemo.service.DeptService;
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
public class DeptServiceImp implements DeptService {

  @Resource
  private DeptMapper mapper;

  @Override
  @Cacheable()
  public List<Dept> findDeptsByID() {
    return mapper.findDeptsByID();
  }
}
