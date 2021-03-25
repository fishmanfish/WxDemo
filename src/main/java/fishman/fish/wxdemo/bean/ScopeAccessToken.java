package fishman.fish.wxdemo.bean;

import org.springframework.stereotype.Component;

/**
 * @author
 * @Package fishman.fish.wxdemo.bean
 * @date 2021/3/20 12:20
 * @Copyright
 */
@SuppressWarnings("all")
@Component
public class ScopeAccessToken {
  private String token;   //获取到的token
  private long expireTime;  //过期时间
  private String refreshToken;  //刷新token

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getRefreshToken() {
    return refreshToken;
  }

  public void setRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
  }

  public long getExpireTime() {
    return expireTime;
  }

  public void setExpireTime(String expireTime) {
    this.expireTime = System.currentTimeMillis() + Long.parseLong(expireTime) * 1000;
  }

  /**
   * 判断当前token是否过期
   * */
  public boolean isExpire(){
    return System.currentTimeMillis() > expireTime;
  }
}
