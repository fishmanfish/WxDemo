package fishman.fish.wxdemo.bean;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author
 * @Package fishman.fish.wxdemo.bean
 * @date 2021/3/20 9:55
 * @Copyright
 */
@Data
@AllArgsConstructor
@XStreamAlias("Voice")
public class Voice {
  @XStreamAlias("MediaId")
  private String mediaId;

}
