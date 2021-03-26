package fishman.fish.wxdemo.controller;

import com.alibaba.fastjson.JSONObject;
import fishman.fish.wxdemo.bean.Test;
import fishman.fish.wxdemo.service.TestService;
import fishman.fish.wxdemo.util.HttpUtils;
import fishman.fish.wxdemo.util.Oid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author
 * @Package fishman.fish.wxdemo.controller
 * @date 2021/3/19 14:57
 * @Copyright
 */
@SuppressWarnings("all")
@Controller
@RequestMapping("/test")
public class TestControlller {
  private Logger log = LoggerFactory.getLogger(TestControlller.class);

  @Autowired
  private TestService testService;
  @Autowired
  private HttpUtils httpUtils;

  @RequestMapping("/get/{id}")
  @ResponseBody
  public Test findTestByID(@PathVariable("id") String id){
    Test test = testService.findTestByID(id);
    log.info("获取到的数据：" + test);
    return test;
  }

  @RequestMapping("/testPush")
  @ResponseBody
  public String testPush(HttpServletRequest request){
    JSONObject json = new JSONObject();
    json.put("sname", "测试推送");
    json.put("idowner", "673da11287c0f13c9a4f2db1fa72c113");
    json.put("iddep", "4028808f069fe7650106a07de9a105b3");
    return httpUtils.post("http://127.0.0.1:8080/testPush", json.toJSONString());
  }

  @PostMapping("/addTest")
  public String addTest(Test test, @RequestParam("token") String token, HttpServletRequest request){
    String page = "success";
    try {
      HttpSession session = request.getSession();
      String sessionToken = (String) session.getAttribute("token");  //存储在session中的token
      if(!StringUtils.hasLength(sessionToken) || StringUtils.hasLength(sessionToken) && !sessionToken.equals(token)){
        log.info("不允许重复提交");
        request.setAttribute("msg", "不允许重复注册");
        return "fwerror";
      }
      if(test != null){
        test.setTestID(Oid.getOid());
        test.setIdowner("673da11287c0f13c9a4f2db1fa72c113");
        test.setIddep("4028808f069fe7650106a07de9a105b3");
        testService.addTest(test);
        log.info("新增的测试记录：" + test.toString());
        request.setAttribute("msg", "注册成功");
        request.getSession().removeAttribute("token");
      }else{
        request.setAttribute("msg", "注册失败");
        page = "fwerror";
      }
    }catch (Exception e){
      log.error("发生错误：" + e.getMessage());
      page = "fwerror";
    }
    return page;
  }

}
