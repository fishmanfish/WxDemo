package fishman.fish.wxdemo.bean;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author
 * @Package fishman.fish.wxdemo.bean
 * @date 2021/3/20 10:00
 * @Copyright
 */
@Data
@AllArgsConstructor
@XStreamAlias("Video")
public class Video {
  @XStreamAlias("MediaId")
  private String mediaId;
  @XStreamAlias("Title")
  private String title;  //标题
  @XStreamAlias("Description")
  private String description; //描述
}
