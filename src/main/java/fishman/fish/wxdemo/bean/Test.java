package fishman.fish.wxdemo.bean;

import lombok.Data;

/**
 * @author
 * @Package fishman.fish.wxdemo.bean
 * @date 2021/3/24 11:29
 * @Copyright
 */
@Data
public class Test {
  private String testID;
  private String testName;
  private String stel;
  private String openID;
  private String created;
  private String idowner;
  private String iddep;
  private Emp emp;
  private Dept dept;
}
