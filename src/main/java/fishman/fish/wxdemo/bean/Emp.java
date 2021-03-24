package fishman.fish.wxdemo.bean;

import lombok.Data;

/**
 * @author
 * @Package fishman.fish.wxdemo.bean
 * @date 2021/3/24 11:29
 * @Copyright
 */
@Data
public class Emp {
  private String empID;
  private String empName;
  private Dept dept;
}
