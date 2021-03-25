package fishman.fish.wxdemo.controller;

import com.alibaba.fastjson.JSONObject;
import fishman.fish.wxdemo.bean.Test;
import fishman.fish.wxdemo.service.TestService;
import fishman.fish.wxdemo.util.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author
 * @Package fishman.fish.wxdemo.controller
 * @date 2021/3/19 14:57
 * @Copyright
 */
@SuppressWarnings("all")
@Controller
@RequestMapping("/fwqq")
public class FwqqControlller {
  private Logger log = LoggerFactory.getLogger(FwqqControlller.class);

  @Autowired
  private TestService testService;
  @Autowired
  private HttpUtils httpUtils;

  /**
   * 申请服务
   * @param id
   * @return
   */
  @RequestMapping("/sqfw")
  public String findTestByID(){
    /*Test test = testService.findTestByID(id);
    log.info("获取到的数据：" + test);*/

    return "regist";
  }
}
