package fishman.fish.wxdemo.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author
 * @Package fishman.fish.wxdemo.bean
 * @date 2021/3/20 13:29
 * @Copyright
 */
@SuppressWarnings("all")
@Data
public class Button{
  private List<AbstractBtn> button;  //定义所有按钮集合
  public Button(List<AbstractBtn> button){
    this.button = button;
  }
}
