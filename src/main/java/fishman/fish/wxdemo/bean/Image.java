package fishman.fish.wxdemo.bean;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author
 * @Package fishman.fish.wxdemo.bean
 * @date 2021/3/20 10:19
 * @Copyright
 */
@Data
@AllArgsConstructor
@XStreamAlias("xml")
public class Image {
  @XStreamAlias("MediaId")
  private String mediaId;
}
