package fishman.fish.wxdemo.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.thoughtworks.xstream.XStream;
import fishman.fish.wxdemo.bean.AbstractBtn;
import fishman.fish.wxdemo.bean.AccessToken;
import fishman.fish.wxdemo.bean.BaseMassage;
import fishman.fish.wxdemo.bean.Button;
import fishman.fish.wxdemo.bean.ClickButton;
import fishman.fish.wxdemo.bean.Image;
import fishman.fish.wxdemo.bean.ImageMassage;
import fishman.fish.wxdemo.bean.ImgTxt;
import fishman.fish.wxdemo.bean.ImgTxtMassage;
import fishman.fish.wxdemo.bean.JSApiTicket;
import fishman.fish.wxdemo.bean.Music;
import fishman.fish.wxdemo.bean.MusicMassage;
import fishman.fish.wxdemo.bean.PhotoButton;
import fishman.fish.wxdemo.bean.Robot;
import fishman.fish.wxdemo.bean.ScanCodeButton;
import fishman.fish.wxdemo.bean.ScopeAccessToken;
import fishman.fish.wxdemo.bean.SubButton;
import fishman.fish.wxdemo.bean.TextMassage;
import fishman.fish.wxdemo.bean.Video;
import fishman.fish.wxdemo.bean.VideoMassage;
import fishman.fish.wxdemo.bean.ViewButton;
import fishman.fish.wxdemo.bean.Voice;
import fishman.fish.wxdemo.bean.VoiceMassage;
import fishman.fish.wxdemo.bean.WXBean;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Formatter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author
 * @Package fishman.fish.wxdemo.util
 * @date 2021/3/19 15:11
 * @Copyright
 */
@SuppressWarnings("all")
@Component
public class WXUtils {
  private Logger log = LoggerFactory.getLogger(WXUtils.class);

  private final String PostRobotURL = "http://www.weilaitec.com/cigirlrobot.cgr?key=%s&msg=%s&ip=%s&userid=%s&appid=%s";    //道翰天琼的免费智能机器人聊天的URL
  private final String GetTokenURL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";  //获取微信access_token
  private final String PostIndustryURL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=%s";  //菜单定义URL
  private final String SetIndustryURL = "https://api.weixin.qq.com/cgi-bin/template/api_set_industry?access_token=%s";  //设置所属行业URL
  private final String GetIndustryURL = "https://api.weixin.qq.com/cgi-bin/template/get_industry?access_token=%s";  //获取所属行业URL
  private final String GetTempIDURL = "https://api.weixin.qq.com/cgi-bin/template/api_add_template?access_token=%s";  //获取模板ID URL
  private final String PostSendTempURL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=%s";  //发送消息模板URL@Autowired
  private final String GetJSApiTicketURL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=%s&type=jsapi";  //获取调用微信接口临时票据的URL
  private final String GetAuthorizeURL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";  //scope为snsapi_userinfo的网页授权获取code
  private final String GetScopeTokenURL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";  //获取网页授权的access_token
  private final String GetRefreshTokenURL = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=%s&grant_type=refresh_token&refresh_token=%s";  //刷新网页授权的access_token
  private final String GetUserInfo = "https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s&lang=zh_CN";  //刷新网页授权的access_token

  @Autowired
  private HttpUtils httpUtils;
  @Autowired
  private WXBean wx;
  @Autowired
  private Robot robot;
  @Autowired
  private AccessToken accessToken;
  @Autowired
  private ScopeAccessToken scopeToken;
  @Autowired
  private JSApiTicket jsApiTicket;

  /**
   * 初始化校验
   */
  public boolean checkInit(String timestamp, String nonce, String signature) {
    if (!StringUtils.hasLength(signature)) {
      return false;
    }
    //1）将token、timestamp、nonce三个参数进行字典序排序
    String[] tempArr = {wx.getToken(), timestamp, nonce};
    log.info("字典排序前：" + Arrays.toString(tempArr));
    Arrays.sort(tempArr);
    log.info("字典排序后：" + Arrays.toString(tempArr));

    //2）将三个参数字符串拼接成一个字符串进行sha1加密
    String tempStr = tempArr[0] + tempArr[1] + tempArr[2];
    log.info("加密前的字符串：" + tempStr);
    tempStr = shaEncry(tempStr);
    log.info("加密后的字符串：" + tempStr);
    //3）开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
    if (signature.equalsIgnoreCase(tempStr)) {
      return true;
    }
    return false;
  }

  /**
   * @param tempStr 需要加密的字符串
   * @return 加密后的字符串
   */
  private String shaEncry(String tempStr) {
    try {
      //获取sha1加密对象
      MessageDigest md = MessageDigest.getInstance("sha1");

      //进行加密
      byte[] bytes = md.digest(tempStr.getBytes());
      //处理加密之后的字节数组
      char[] chars = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};  //定义十六进制数对应的字符
      StringBuilder sb = new StringBuilder();  //接收加密后的字符串
      for (byte b : bytes) {
        sb.append(chars[b >> 4 & 15]);  //每个字节的高四位向右移4位，然后跟00001111 (15)进行 & 操作
        sb.append(chars[b & 15]);  //每个字节的低四位跟00001111 (15)进行 & 操作
      }
      return sb.toString();
    } catch (NoSuchAlgorithmException e) {
      log.error("shaEncry()方法出现错误：" + e.getMessage());
    }
    return "";
  }

  public Map<String, String> xmlToMap(InputStream is) {
    Map<String, String> map = new HashMap<>();
    SAXReader saxReader = new SAXReader();
    try {
      Document document = saxReader.read(is);   //解析流中的xml数据包
      Element rootElement = document.getRootElement();  //获取xml根节点
      List<Element> elements = rootElement.elements();   //获取根节点下的所有子节点
      elements.forEach((Element e) -> {
        map.put(e.getName(), e.getText());   //添加到Map集合中
      });
    } catch (Exception e) {
      log.error("xmlToMap()方法出现错误：" + e.getMessage());
    }
    return map;
  }

  /**
   * 获取返回的消息
   */
  public String getReturnMsg(Map<String, String> map) {
    String returnMsg = "";
    wx.setOpenID(StringUtils.hasLength(map.get("FromUserName")) ? map.get("FromUserName") : wx.getOpenID());
    String content = map.get("Content");  //获取消息类型
    String msgType = map.get("MsgType");  //获取消息类型
    if (StringUtils.hasLength(content) && content.contains("图文")) {
      returnMsg = beanToXml(ImgTxtMassage.class, getImgTxtMsg(map));
      return returnMsg;
    } else if (StringUtils.hasLength(content) && content.contains("简历反馈")) {
      sendTemp();
      return "";
    }
    switch (msgType) {
      case "text":   //文本消息
        returnMsg = beanToXml(TextMassage.class, getTextMsg(map));
        break;
      case "image":   //图片消息
        returnMsg = beanToXml(ImageMassage.class, getImageMsg(map));
        break;
      case "voice":   //语音消息
        returnMsg = beanToXml(VoiceMassage.class, getVoiceMsg(map));
        break;
      case "video":   //视频消息
        returnMsg = beanToXml(VideoMassage.class, getVideoMsg(map));
        break;
      case "shortvideo":   //短视频消息
        break;
      case "location":   //位置消息
        break;
      case "link":   //链接消息
        break;
      case "event":   //菜单响应
        returnMsg = getMenuTextMsg(map);
        break;
      default:
        break;
    }
    return returnMsg;
  }

  private String beanToXml(Class type, BaseMassage massage) {
    XStream xStream = new XStream();
    String xml = "";
    xStream.processAnnotations(type);
    xml = xStream.toXML(massage);
    log.info("发送的XML：\n" + xml);
    return xml;
  }

  /**
   * 发送事件消息
   */
  private String getMenuTextMsg(Map<String, String> map) {
    String xml = "";
    String event = map.get("Event");   //事件类型
    switch (event) {
      case "CLICK":
        xml = beanToXml(ImgTxtMassage.class, getImgTxtMsg(map));
        break;
      case "VIEW":
        //xml = beanToXml(TextMassage.class, getTextMsg(map));
        break;
      default:
        break;
    }
    return xml;
  }

  /**
   * 发送文本消息
   */
  private TextMassage getTextMsg(Map<String, String> map) {
    String toMsg = map.get("Content");
    return new TextMassage(map, chatRobot(toMsg));
  }

  /**
   * 发送图片消息
   */
  private ImageMassage getImageMsg(Map<String, String> map) {
    return new ImageMassage(map, new Image("mediaId"));
  }

  /**
   * 发送语音消息
   */
  private VoiceMassage getVoiceMsg(Map<String, String> map) {
    return new VoiceMassage(map, new Voice("mediaId"));
  }

  /**
   * 发送视频消息
   */
  private VideoMassage getVideoMsg(Map<String, String> map) {
    return new VideoMassage(map, new Video("mediaId", "测试标题", "测试描述"));
  }

  /**
   * 发送音乐消息
   */
  private MusicMassage getMusicMsg(Map<String, String> map) {
    return new MusicMassage(map, new Music("mediaId", "测试标题", "测试描述", "测试链接", "测试高品质链接", "thumbMediaId"));
  }

  /**
   * 发送图文消息
   */
  private ImgTxtMassage getImgTxtMsg(Map<String, String> map) {
    List<ImgTxt> imgTxts = new ArrayList<>();
    ImgTxt imgTxt1 = new ImgTxt("船长的觉悟", "蒙奇.D.路飞", "http://mmbiz.qpic.cn/sz_mmbiz_jpg/5gaLWZFpY853eF7pfAtHibDhSSdib0G2Sicic7pNQc6ALqLbMNkG7FVtOBhYzUfyibxdd3lZcPUkeTmeZGq0ficm01iaA/0", "https://manhua.fzdm.com/2/1006/");
    imgTxts.add(imgTxt1);
    /*ImgTxt imgTxt2 = new ImgTxt("测试标题2", "测试描述2", "测试链接2", "测试高品质链接2");
    imgTxts.add(imgTxt2);*/
    return new ImgTxtMassage(map, imgTxts, 1);
  }

  /**
   * 调用网络机器人
   */
  private String chatRobot(String toMsg) {
    URL url = null;
    HttpURLConnection conn = null;
    InputStream is = null;
    ByteArrayOutputStream baos = null;
    try {
      String robotURL = String.format(PostRobotURL, robot.getApiKey(), toMsg, robot.getIp(), robot.getUserID(), robot.getAppID());
      log.info("请求机器人地址：" + robotURL);
      url = new URL(robotURL);
      conn = (HttpURLConnection) url.openConnection();
      conn.setReadTimeout(5 * 10000);
      conn.setConnectTimeout(5 * 10000);
      conn.setRequestMethod("POST");
      if (conn.getResponseCode() == 200) {
        is = conn.getInputStream();
        baos = new ByteArrayOutputStream();
        int len = -1;
        byte[] buf = new byte[128];

        while ((len = is.read(buf)) != -1) {
          baos.write(buf, 0, len);
        }
        baos.flush();
        String returnMsg = baos.toString();
        return returnMsg;
      } else {
        throw new Exception("服务器连接错误！");
      }

    } catch (Exception e) {
      log.error("chatRobot()方法出现错误：" + e.getMessage());
    } finally {
      try {
        if (is != null)
          is.close();
      } catch (IOException e) {
        log.error("chatRobot()方法finally块出现错误：" + e.getMessage());
      }

      try {
        if (baos != null)
          baos.close();
      } catch (IOException e) {
        log.error("chatRobot()方法关闭流时出现错误：" + e.getMessage());
      }
      conn.disconnect();
    }
    return "";
  }


  /**
   * 发送GET请求获取access_token相关信息
   */
  public String getAccessToken() {
    if (accessToken == null || (accessToken != null && accessToken.isExpire())) {
      String result = httpUtils.get(String.format(GetTokenURL, wx.getAppID(), wx.getAppSecret()));
      log.info("返回的AccessToken相关信息：" + result);
      if (StringUtils.hasLength(result)) {
        JSONObject jsonObject = JSONObject.parseObject(result);
        accessToken.setToken(jsonObject.getString("access_token"));
        accessToken.setExpireTime(jsonObject.getString("expires_in"));
      }
    }
    return accessToken.getToken();
  }

  public void diyMenu() {
    /*String result = httpUtils.get("https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=" + getAccessToken());
    log.info("删除自定义菜单" + result);*/
    List<AbstractBtn> buttons = new ArrayList<>();
    ClickButton clickButton = new ClickButton("点击菜单1", "click");
    buttons.add(clickButton);
    ViewButton viewButton = new ViewButton("网页菜单2", "https://manhua.fzdm.com/2/1006/");
    buttons.add(viewButton);
    List<AbstractBtn> subButtons = new ArrayList<>();
    PhotoButton photoButton = new PhotoButton("拍照", "photo", new ArrayList<>());
    subButtons.add(photoButton);
    ScanCodeButton scanCodeButton = new ScanCodeButton("扫码", "scancode", new ArrayList<>());
    subButtons.add(scanCodeButton);
    SubButton subButton = new SubButton("集合菜单3", subButtons);
    buttons.add(subButton);
    Button button = new Button(buttons);
    JSONObject json = (JSONObject) JSONObject.toJSON(button);
    log.info("自定义菜单JSON：" + json.toJSONString());
    String result = httpUtils.post(PostIndustryURL, json.toJSONString());
    log.info("返回的自定义菜单状态：" + result);
  }

  public void diyMenu2() {
    List<AbstractBtn> buttons = new ArrayList<>();
    List<AbstractBtn> subButtons = new LinkedList<>();
    ClickButton clickButton = new ClickButton("申请服务", wx.getCurrentURL() + "/fwqq/sqfw");
    subButtons.add(clickButton);
    ViewButton viewButton = new ViewButton("我的设备", wx.getCurrentURL() + "/fwqq/mysb");
    subButtons.add(viewButton);
    SubButton subButton = new SubButton("服务请求", subButtons);
    buttons.add(subButton);
    Button button = new Button(buttons);
    JSONObject json = (JSONObject) JSONObject.toJSON(button);
    log.info("自定义菜单JSON：" + json.toJSONString());
    String result = httpUtils.post(PostIndustryURL, json.toJSONString());
    log.info("返回的自定义菜单状态：" + result);
  }

  /**
   * 设置所属行业
   */
  public void setIndustry() {
    JSONObject json = new JSONObject();
    json.put("industry_id1", "1");  //公众号模板消息所属行业编号
    json.put("industry_id2", "2");  //公众号模板消息所属行业编号
    String result = httpUtils.post(String.format(SetIndustryURL, getAccessToken()), json.toJSONString());
    log.info("设置所属行业接口返回结果：" + result);
  }


  /**
   * 获取设置的行业
   */
  public String getIndustry() {
    String result = httpUtils.get(String.format(GetIndustryURL, getAccessToken()));
    return result;
  }

  /**
   * 获取模板ID
   */
  public String getTempID() {
    JSONObject json = new JSONObject();
    String result = httpUtils.post(String.format(GetTempIDURL, getAccessToken()), json.toJSONString());
    log.info("获取的模板ID接口返回结果" + result);
    if (StringUtils.hasLength(result)) {
      JSONObject jsonObject = JSONObject.parseObject(result);
      return jsonObject.getString("template_id_short");
    }
    return "";
  }

  /**
   * 发送模板消息
   */
  public void sendTemp() {
    JSONObject masterJson = new JSONObject();
    masterJson.put("touser", wx.getOpenID());
    masterJson.put("template_id", wx.getTempID());
    masterJson.put("url", "http://fishman.free.idcfengye.com/wx/index");  //增加此选项将会出现 "详情" 字段
    JSONObject dataJson = new JSONObject();
    JSONObject dtJson1 = new JSONObject();
    dtJson1.put("value", "简历反馈通知");
    dtJson1.put("color", "#173177");
    dataJson.put("first", dtJson1);
    JSONObject dtJson2 = new JSONObject();
    dtJson2.put("value", "深圳腾讯科技");
    dtJson2.put("color", "#173177");
    dataJson.put("company", dtJson2);
    JSONObject dtJson3 = new JSONObject();
    dtJson3.put("value", "2021年01月01日");
    dtJson3.put("color", "#173177");
    dataJson.put("time", dtJson3);
    JSONObject dtJson4 = new JSONObject();
    dtJson4.put("value", "恭喜你通过了本公司的面试");
    dtJson4.put("color", "#173177");
    dataJson.put("result", dtJson4);
    JSONObject dtJson5 = new JSONObject();
    dtJson5.put("value", "请下周前来报道");
    dtJson5.put("color", "#173177");
    dataJson.put("remark", dtJson5);
    masterJson.put("data", dataJson);
    log.info("发送的模板消息：" + masterJson);
    String result = httpUtils.post(PostSendTempURL, masterJson.toJSONString());
    log.info("发送模板接口返回结果：" + result);
  }


  /**
   * 获取接口调用的临时票据
   */
  public String getJSApiTikect() {
    if(jsApiTicket == null || (jsApiTicket != null && jsApiTicket.isExpire())){
      String url = String.format(GetJSApiTicketURL, getAccessToken());
      String result = httpUtils.get(url);
      if (StringUtils.hasLength(result)) {
        JSONObject json = JSONObject.parseObject(result);
        jsApiTicket.setTicket(json.getString("ticket"));
        jsApiTicket.setExpireTime(json.getString("expires_in"));
        log.info("获取JSApi_Ticket接口的返回结果：" + json);
      }
    }
    return jsApiTicket.getTicket();
  }

  /**
   *
   * 网页授权，获取用户的基本信息
   * s应用授权作用域，snsapi_base （不弹出授权页面，直接跳转，只能获取用户openid），
   * snsapi_userinfo （弹出授权页面，可通过openid拿到昵称、性别、所在地。
   * 并且， 即使在未关注的情况下，只要用户授权，也能获取其信息
   */
  public void getAuthorizeCode(){
    //获取code
    String result = httpUtils.get(String.format(GetAuthorizeURL, wx.getAppID(), ""));
    log.info("网页授权获取到的code：" + result);
  }

  public String getScopeToken(String code){
    if(scopeToken == null || (scopeToken != null && scopeToken.isExpire())) {
      String result = httpUtils.get(String.format(GetScopeTokenURL, wx.getAppID(), code));
      if (StringUtils.hasLength(result)) {
        JSONObject tokenJson = JSONObject.parseObject(result);
        scopeToken.setToken(tokenJson.getString("access_token"));
        scopeToken.setExpireTime(tokenJson.getString("expires_in"));
        scopeToken.setRefreshToken(tokenJson.getString("refresh_token"));
        log.info("获取到的网页授权access_token：" + tokenJson.toJSONString());
      }
    }
    return scopeToken.getToken();
  }

  public JSONObject getWxUserInfo(String code){
    String scopeToken = getScopeToken(code);
    String result = httpUtils.get(String.format(GetUserInfo, scopeToken, wx.getOpenID()));
    if (StringUtils.hasLength(result)) {
      log.info("获取到的用户授权信息：" + result);
      return JSONObject.parseObject(result);
    }
    return null;
  }

  /**
   * 调用API需要的初始化步骤
   * @return
   */
  public JSONObject getJSAPIConfig(String requestUrl){
    JSONObject json = new JSONObject();
    String ticket = getJSApiTikect();
    String timeTamp = createTimestamp();
    String nonceStr = createRandomStr();
    json.put("appId", wx.getAppID());
    json.put("timestamp", timeTamp);
    json.put("nonceStr", nonceStr);

    //url需要在微信公中平台配置JS安全域名，
    // 举例：如果接口地址是：http://write.blog.csdn.net/postedit
    // 那么安全域名就为：blog.csdn.net
    // 举例：如果接口地址是：http://fishman.free.idcfengye.com/exhibitionhall
    // 那么安全域名就为：fishman.free.idcfengye.com
    String signature = shaEncry(String.format("jsapi_ticket=%s&noncestr=%s&timestamp=%s&url=%s",ticket, nonceStr, timeTamp, wx.getCurrentURL() + requestUrl));
    json.put("signature", signature);
    return json;
  }

  public String createTimestamp(){
    return Long.toString(System.currentTimeMillis() / 1000);
  }

  public String createRandomStr(){
    return UUID.randomUUID().toString();
  }


  /*private String sha1(String str) {
    try {
      MessageDigest crypt = MessageDigest.getInstance("SHA-1");
      crypt.reset();
      crypt.update(str.getBytes("UTF-8"));
      Formatter formatter = new Formatter();
      for (byte b : crypt.digest())
      {
        formatter.format("%02x", b);
      }
      String result = formatter.toString();
      formatter.close();
      return result;
    }catch (Exception e){

    }
    return "";
  }*/
}
