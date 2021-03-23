package fishman.fish.wxdemo.bean;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author
 * @Package fishman.fish.wxdemo.bean
 * @date 2021/3/22 16:24
 * @Copyright
 */
@Component
@ConfigurationProperties("robot")
@Data
public class Robot {

  private  String userID;   //	平道翰天琼的免费智能机器人聊天台注册账号

  private  String appID;    //道翰天琼的免费智能机器人聊天平台创建的应用id

  private  String apiKey;  //道翰天琼的免费智能机器人聊天平台应用生成的秘钥

  private  String ip;     //	客户端ip要求唯一性,无ip等可以用QQ账号，微信账号，手机MAC地址等代替

}
