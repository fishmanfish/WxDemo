package fishman.fish.wxdemo.mapper;


import fishman.fish.wxdemo.bean.Dept;
import fishman.fish.wxdemo.bean.Test;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TestMapper {
  Test findTestByID(@Param("id") String id);

  Test findTestByOpenID(@Param("openID") String openID);

  void addTest(Test test);
}
