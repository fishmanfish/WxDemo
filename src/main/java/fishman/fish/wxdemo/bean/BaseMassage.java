package fishman.fish.wxdemo.bean;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.util.Map;

/**
 * @author
 * @Package fishman.fish.wxdemo.bean
 * @date 2021/3/19 17:13
 * @Copyright
 */
@Data
public class BaseMassage {
  @XStreamAlias("ToUserName")
  public String toUserName;  //接收方帐号（收到的OpenID）
  @XStreamAlias("FromUserName")
  public String fromUserName;  //开发者微信号
  @XStreamAlias("CreateTime")
  public String createTime;  //消息创建的时间，单位：秒
  @XStreamAlias("MsgType")
  public String msgType;  //消息类型

  public BaseMassage(Map<String, String> map){
    this.toUserName = map.get("FromUserName");
    this.fromUserName = map.get("ToUserName");
    this.createTime = System.currentTimeMillis() / 1000 + "";
  }
}
