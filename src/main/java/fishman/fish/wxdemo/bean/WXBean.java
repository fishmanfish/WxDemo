package fishman.fish.wxdemo.bean;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author
 * @Package fishman.fish.wxdemo.bean
 * @date 2021/3/22 16:07
 * @Copyright
 */
@Data
@ConfigurationProperties("wx")
@Component
public class WXBean {
  private String appID;   //微信公众号平台的应用ID

  private String appSecret;  //微信公众号平台生成的密钥

  private String token;  //微信公众号token

  private  String openID;

  private  String tempID ;

  private  String currentURL;
}
