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
public class VideoMassage extends BaseMassage {
  @XStreamAlias("Video")
  private Video video;

  public VideoMassage(Map<String, String> map, Video video){
    super(map);
    this.msgType = "video";
    this.video = video;
  }
}
