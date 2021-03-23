package fishman.fish.wxdemo.bean;

import lombok.Data;

/**
 * @author
 * @Package fishman.fish.wxdemo.bean
 * @date 2021/3/20 13:37
 * @Copyright
 */
@SuppressWarnings("all")
@Data
public class ClickButton extends AbstractBtn {
  private String type = "click";  //点击
  private String key;

  public ClickButton(String name, String key) {
    super(name);
    this.key = key;
  }
}
