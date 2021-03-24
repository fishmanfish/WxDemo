package fishman.fish.wxdemo.bean;

import lombok.Data;

import java.util.List;

/**
 * @author
 * @Package fishman.fish.wxdemo.bean
 * @date 2021/3/24 11:29
 * @Copyright
 */
@Data
public class Dept {
  private String deptID;
  private String deptName;
  private List<Emp> emps;
}
