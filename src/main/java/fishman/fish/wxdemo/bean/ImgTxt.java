package fishman.fish.wxdemo.bean;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author
 * @Package fishman.fish.wxdemo.bean
 * @date 2021/3/20 10:01
 * @Copyright
 */
@Data
@AllArgsConstructor
@XStreamAlias("item")
public class ImgTxt {
  @XStreamAlias("Title")
  private String title;
  @XStreamAlias("Description")
  private String description;
  @XStreamAlias("PicUrl")
  private String picUrl;
  @XStreamAlias("Url")
  private String url;
}
