package fishman.fish.wxdemo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author
 * @Package fishman.fish.wxdemo.util
 * @date 2021/3/21 10:40
 * @Copyright
 */
@Component
public class HttpUtils {

  @Autowired
  private WXUtils wxUtils;

  /**
   * 发送POST请求
   */
  public String post(String postUrl, String data) {
    try {
      URL url = new URL(String.format(postUrl, wxUtils.getAccessToken()));
      URLConnection connection = url.openConnection();
      connection.setDoOutput(true);  //设置允许写入数据
      OutputStream os = connection.getOutputStream();
      os.write(data.getBytes());
      os.close();

      InputStream is = connection.getInputStream();
      byte[] bytes = new byte[1024];
      int len = 0;
      StringBuilder sb = new StringBuilder();
      while ((len = is.read(bytes)) != -1) {
        sb.append(new String(bytes, 0, len));
      }
      is.close();
      return sb.toString();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return "";
  }

  public String get(String getUrl) {
    try {
      URL url = new URL(getUrl);
      URLConnection connection = url.openConnection();//开始连接
      InputStream is = connection.getInputStream();//获取流对象
      byte[] bytes = new byte[1024];
      int len = 0;
      StringBuilder sb = new StringBuilder();
      if ((len = is.read(bytes)) != -1) {
        sb.append(new String(bytes, 0, len));  //获取数据
      }
      is.close();
      return sb.toString();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return "";
  }
}
