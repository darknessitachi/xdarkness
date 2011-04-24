程序使用说明
1  index页面是创建支付url,使用ItemUrl方法拼凑一个url。
   index页面的参数已经是必要参数，可以不用修改。可以稍许微调整。
   把自己网站的相对应的变量，赋值给对应参数后面即可，比如：
   String out_trade_no		= date.getOrderNum();	
   这个客户订单号，取的系统时间，调用UtilDate类中的getOrderNum()方法作为订单号（即外部订单号），这个可以是自己的订单
   
   
2  alipay_notify.jsp为对支付宝返回通知处理，
   所以对应给notify_url这个参数设置。它的数据交互是通过服务器间进行数据交互，服务器post消息到异步返回处理页面，
   需要客户技术在异步返回处理页面处理相关的数据处理，然后每一步操作都要返回给支付宝success（不能包含其他的HTML脚本语言，不可以做页面跳转。），
   这个返回处理如果集成OK，那么基本不会出现掉单，因为支付宝会在24小时之内分6~10次将订单信息返回个给客户网站，直到支付宝捕获success。
   
3  alipay_return.jsp为对支付宝返回通知处理，
   支付宝通过get方式跳转到这个地址，并且带有参数给这个页面。是一种可视化的返回，ie页面跳转通知，只要支付成功，
   客户获取信息受到买家操作的影响。如果买家支付完成后客户服务器响应比较慢，
   买家在显示支付宝提示的“即时到账支付成功“时关闭页面，那么客户网站是获取不到信息，我们这边称为” 掉单“。
   而且这个返回处理是一次性调取，即支付成功后才调取同步返回处理。

4  如果异步和同步想做写日志的动作，请到src下的com.alipay.util包里边的SignatureHelper_return（同步）和SignatureHelper  （异步）的sign方法中有个写日志的动作。
  
5  在接口中我们并没有将买家的收货信息参数写入，如果集成事需要，请将该信息加入：
        /*收货人信息*/
	String receive_name = "支付宝";  //收货人姓名
	String receive_address = "上海市长宁区长宁路1027号兆丰广场29楼";  //收货人地址
	String receive_zip = "200050";  //收货人邮编
	String receive_phone = "057188156688";  //收货人电话
	String receive_mobile = "";  //收货人手机
   同事需要修改src下com.alipay.util中的Payment.java类。

6 java程序要注意的中文乱码问题，一定要配置上去中文filter,
  注意：一定要在web.xml中配置过滤器。每个项目中都配置了这个过滤器，具体可以直接打开
webcontent文件夹下，web-inf文件夹下的web.xml文件。
  可以参考下面文章：
   http://blog.csdn.net/lixinye0123/archive/2006/03/26/639402.aspx
  例如：
  <filter>
		<filter-name>Set Character Encoding</filter-name>
		<filter-class>filters.SetCharacterEncodingFilter</filter-class>
		<init-param>
			<param-name>encoding</param-name>
			<param-value>GBK</param-value>
		</init-param>
	</filter>
	<filter-mapping>
		<filter-name>Set Character Encoding</filter-name>
		<url-pattern>/*</url-pattern>
	</filter-mapping>