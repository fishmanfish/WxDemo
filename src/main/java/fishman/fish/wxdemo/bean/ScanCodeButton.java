package fishman.fish.wxdemo.bean;

import lombok.Data;

import java.util.List;

/**
 * @author
 * @Package fishman.fish.wxdemo.bean
 * @date 2021/3/20 13:37
 * @Copyright
 */
@SuppressWarnings("all")
@Data
public class ScanCodeButton extends AbstractBtn {
  private String type = "scancode_push";  //扫码
  private String key;
  private List<SubButton> subBtns;

  public ScanCodeButton(String name, String key, List<SubButton> subBtns) {
    super(name);
    this.key = key;
    this.subBtns = subBtns;
  }
}
