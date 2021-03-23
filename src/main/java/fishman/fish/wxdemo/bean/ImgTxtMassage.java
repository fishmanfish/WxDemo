package fishman.fish.wxdemo.bean;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.util.List;
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
public class ImgTxtMassage extends BaseMassage {
  @XStreamAlias("Articles")
  private List<ImgTxt> imgTxts;
  @XStreamAlias("ArticleCount")
  private int articleCount;

  public ImgTxtMassage(Map<String, String> map, List<ImgTxt> imgTxts, int articleCount){
    super(map);
    this.msgType = "news";
    this.imgTxts = imgTxts;
    this.articleCount = articleCount;
  }
}
