package fishman.fish.wxdemo.controller;

import fishman.fish.wxdemo.service.TestService;
import fishman.fish.wxdemo.util.WXUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
  private WXUtils wxUtils;

  /**
   * 申请服务
   * @param id
   * @return
   */
  @RequestMapping("/sqfw")
  public String sqfw(){
    String url = wxUtils.toAuthScope("sqfw");
    return "redirect:" + url;
  }

  /**
   * 我的设备
   * @param id
   * @return
   */
  @RequestMapping("/mysb")
  public String mysb(){
    String url = wxUtils.toAuthScope("mysb");
    return "redirect:" + url;
  }
}
