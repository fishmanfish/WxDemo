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
public class PhotoButton extends AbstractBtn {
  private String type = "pic_sysphoto";  //拍照或上传图片
  private String key;
  private List<SubButton> subBtns;

  public PhotoButton(String name, String key, List<SubButton> subBtns) {
    super(name);
    this.key = key;
    this.subBtns = subBtns;
  }
}
