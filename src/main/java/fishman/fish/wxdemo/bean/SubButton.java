package fishman.fish.wxdemo.bean;

import lombok.Data;

import java.util.List;

/**
 * @author
 * @Package fishman.fish.wxdemo.bean
 * @date 2021/3/20 13:40
 * @Copyright
 */
@SuppressWarnings("all")
@Data
public class SubButton extends AbstractBtn {
  private List<AbstractBtn> sub_button;

  public SubButton(String name, List<AbstractBtn> sub_button) {
    super(name);
    this.sub_button = sub_button;

  }
}
