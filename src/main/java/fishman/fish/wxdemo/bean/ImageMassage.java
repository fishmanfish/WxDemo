package fishman.fish.wxdemo.bean;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.util.Map;

/**
 * @author
 * @Package fishman.fish.wxdemo.bean
 * @date 2021/3/19 17:14
 * @Copyright
 */
@SuppressWarnings("all")
@Data
@XStreamAlias("xml")
public class ImageMassage extends BaseMassage {
  @XStreamAlias("Image")
  private Image image;

  public ImageMassage(Map<String, String> map,  Image image){
    super(map);
    this.msgType = "image";
    this.image = image;
  }
}
