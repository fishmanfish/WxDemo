package fishman.fish.wxdemo.controller;

import com.alibaba.fastjson.JSONObject;
import fishman.fish.wxdemo.bean.Test;
import fishman.fish.wxdemo.service.TestService;
import fishman.fish.wxdemo.util.HttpUtils;
import fishman.fish.wxdemo.util.Oid;
import fishman.fish.wxdemo.util.WXUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
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
@RequestMapping("/wx")
public class WXControlller {
  private Logger log = LoggerFactory.getLogger(WXControlller.class);
  @Autowired
  private WXUtils wxUtils;

  @Autowired
  private TestService testService;

  @GetMapping("/init")
  @ResponseBody
  public String initGet(@RequestParam("signature") String signature,  //微信加密签名
                        @RequestParam("timestamp") String timestamp,  //时间戳
                        @RequestParam("nonce") String nonce,  //随机数
                        @RequestParam("echostr") String echostr) {  //随机字符串
    log.info("微信加密签名：" + signature);
    log.info("时间戳：" + timestamp);
    log.info("随机数：" + nonce);
    log.info("随机字符串：" + echostr);
    if (wxUtils.checkInit(timestamp, nonce, signature)) {
      log.info("接口配置成功");
      return echostr;
    } else {
      log.info("接口配置失败");
      return "";
    }
  }

  /**
   *用来接收消息的
   * */
  @PostMapping("/init")
  @ResponseBody
  public String initPost(HttpServletRequest request) {
    String returnMsg = "";
    try {
      Map<String, String> map = wxUtils.xmlToMap(request.getInputStream());
      map.entrySet().forEach((Map.Entry<String, String> kvs) -> {
        log.info("键：" + kvs.getKey() + ", 值：" + kvs.getValue());
      });

      returnMsg = wxUtils.getReturnMsg(map);
    } catch (IOException e) {
      log.error("initPost()方法出现错误：" + e.getMessage());
    }
    return returnMsg;
  }

  /**
   * 获取微信JSApi接口的配置信息
   * @param requestUrl  请求地址
   * @return
   */
  @PostMapping("/getJsApiTickt")
  @ResponseBody
  public JSONObject getJsApiTickt(@RequestParam("requestUrl") String requestUrl) {
    JSONObject json = wxUtils.getJSAPIConfig(requestUrl);
    return json;
  }


  /**
   * 获取用户基本信息
   * @param code
   * @return
   */
  @GetMapping("/getWxUserInfo")
  public String getWxUserInfo(@RequestParam("code") String code, @RequestParam("state") String state, HttpServletRequest request) {
    log.info("获取到的code：" + code);
    String toUrl = "";
    JSONObject json = wxUtils.getWxUserInfo(code);
    String openID = json.getString("openid");
    Test test = testService.findTestByOpenID(openID);
    if(test != null && StringUtils.hasLength(test.getTestID())){   //已经注册了的联系人
      if("sqfw".equals(state)){
        toUrl = state;
      }else if("mysb".equals(state)){
        toUrl = state;
      }
      request.setAttribute("requestUrl", request.getRequestURI());
    }else{
      request.setAttribute("openID", openID);
      request.getSession().setAttribute("token", Oid.getOid());
      toUrl = "regist";
    }
    return toUrl;
  }


}
