package fishman.fish.wxdemo.bean;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @author
 * @Package fishman.fish.wxdemo.bean
 * @date 2021/3/20 12:20
 * @Copyright
 */
@SuppressWarnings("all")
@Component
public class JSApiTicket {
  private String ticket;   //获取临时票据
  private long expireTime;  //过期时间

  public String getTicket() {
    return ticket;
  }

  public void setTicket(String ticket) {
      this.ticket = ticket;
  }

  public long getExpireTime() {
    return expireTime;
  }

  public void setExpireTime(String expireTime) {
    this.expireTime = System.currentTimeMillis() + Long.parseLong(expireTime) * 1000;
  }


  /**
   * 判断当前ticket是否过期
   * */
  public boolean isExpire(){
    return System.currentTimeMillis() > expireTime;
  }
}
