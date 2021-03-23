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
public class VoiceMassage extends BaseMassage {
  @XStreamAlias("Voice")
  private Voice voice;

  public VoiceMassage(Map<String, String> map, Voice voice){
    super(map);
    this.msgType = "voice";
    this.voice = voice;
  }
}
