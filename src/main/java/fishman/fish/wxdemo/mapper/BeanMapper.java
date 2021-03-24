package fishman.fish.wxdemo.mapper;


import fishman.fish.wxdemo.bean.Test;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BeanMapper {
  Test findBeanByID(@Param("id") String id);
}
