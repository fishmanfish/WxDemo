package fishman.fish.wxdemo.service;

import fishman.fish.wxdemo.bean.Dept;
import fishman.fish.wxdemo.bean.Test;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author
 * @Package fishman.fish.wxdemo.service
 * @date 2021/3/24 11:47
 * @Copyright
 */
public interface TestService {

  Test findTestByID(String id);

  Test findTestByOpenID(String openID);

  void addTest(Test test);

}
