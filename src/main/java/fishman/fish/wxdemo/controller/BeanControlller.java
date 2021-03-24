package fishman.fish.wxdemo.controller;

import com.alibaba.fastjson.JSONObject;
import fishman.fish.wxdemo.bean.Test;
import fishman.fish.wxdemo.service.BeanService;
import fishman.fish.wxdemo.util.WXUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

/**
 * @author
 * @Package fishman.fish.wxdemo.controller
 * @date 2021/3/19 14:57
 * @Copyright
 */
@SuppressWarnings("all")
@Controller
@RequestMapping("/bean")
public class BeanControlller {
  private Logger log = LoggerFactory.getLogger(BeanControlller.class);

  @Autowired
  private BeanService beanService;

  @RequestMapping("/find/{id}")
  @ResponseBody
  public Test findBeanByID(@PathVariable("id") String id){
    Test test = beanService.findBeanByID(id);
    log.info("获取到的数据：" + test);
    return test;
  }


}
