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
public class ViewButton extends AbstractBtn {
  private String type = "view";  //网页
  private String url;

  public ViewButton(String name, String url) {
    super(name);
    this.url = url;
  }
}
