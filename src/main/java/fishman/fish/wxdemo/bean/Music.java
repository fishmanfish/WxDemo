package fishman.fish.wxdemo.bean;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author
 * @Package fishman.fish.wxdemo.bean
 * @date 2021/3/20 9:57
 * @Copyright
 */
@Data
@AllArgsConstructor
@XStreamAlias("xml")
public class Music {
  @XStreamAlias("MediaId")
  private String mediaId;
  @XStreamAlias("Title")
  private String title;  //音乐标题
  @XStreamAlias("Description")
  private String description; //音乐描述
  @XStreamAlias("MusicUrl")
  private String musicUrl;  //音乐链接
  @XStreamAlias("HQMusicUrl")
  private String hQMusicUrl;  //高质量音乐链接
  @XStreamAlias("ThumbMediaId")
  private String thumbMediaId;  //缩略图的媒体id
}
