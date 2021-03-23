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
public class MusicMassage extends BaseMassage {
  @XStreamAlias("Music")
  private Music music;

  public MusicMassage(Map<String, String> map, Music music){
    super(map);
    this.msgType = "music";
    this.music = music;
  }
}
