package fishman.fish.wxdemo.bean;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class TextMassage extends BaseMassage {
  @XStreamAlias("Content")
  private String content;  //文本消息

  public TextMassage(Map<String, String> map, String content){
    super(map);
    this.msgType = "text";
    this.content = content;
  }
}
