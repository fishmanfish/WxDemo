package fishman.fish.wxdemo.controller;

import com.alibaba.fastjson.JSONObject;
import fishman.fish.wxdemo.util.WXUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
  @Autowired
  private WXUtils wxUtils;

  @GetMapping("/init")
  @ResponseBody
  public String initGet(@RequestParam("signature") String signature,  //微信加密签名
                        @RequestParam("timestamp") String timestamp,  //时间戳
                        @RequestParam("nonce") String nonce,  //随机数
                        @RequestParam("echostr") String echostr) {  //随机字符串
    System.out.println("微信加密签名：" + signature);
    System.out.println("时间戳：" + timestamp);
    System.out.println("随机数：" + nonce);
    System.out.println("随机字符串：" + echostr);
    if (wxUtils.checkInit(timestamp, nonce, signature)) {
      System.out.println("接口配置成功");
      return echostr;
    } else {
      System.out.println("接口配置失败");
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
        System.out.println("键：" + kvs.getKey() + ", 值：" + kvs.getValue());
      });

      returnMsg = wxUtils.getReturnMsg(map);
    } catch (IOException e) {
      e.printStackTrace();
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
  public JSONObject getTicket(@RequestParam("requestUrl") String requestUrl) {
    JSONObject json = wxUtils.getJSAPIConfig(requestUrl);
    return json;
  }

  @RequestMapping("/index")
  public String index(HttpServletRequest request){
    request.setAttribute("requestUrl", request.getRequestURI());
    return "index";
  }


}
