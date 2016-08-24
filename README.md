# 微信公众平台服务端演示

这个demo工程演示使用Java + Spring Boot如何写一个微信公众平台服务端

功能：菜单创建、二维码邀请注册、关注消息、自动回复，语义识别等

使用语义识别，查询机票信息，支持文字输入或者微信语音输入 “我要预订下周五北京到上海的机票”

语义识别使用[讯飞开放平台的JavaSDK](http://www.xfyun.cn/sdk/dispatcher)，略恶心的是不支持Mac OS，所以只能写完部署到Linux或者选择Windows开发调试。

# 准备
请先修改你的微信测试号
 
	$ cp src/main/resources/application-demo.properties  src/main/resources/application.properties
	application.properties
		spring.wechatmp.app-id=
		spring.wechatmp.secret=
		spring.wechatmp.token=
		# 授权查看个人信息，菜单里面的回调地址
		wexin.menu.redirect_uri=
		# Xun fei yun appid, for semantic recognize demo
		xfyun.appid=
		# For semantic recognize demo， customized flight search API
		flight.search_url=
		flight.user_token=
		

# 运行（使用IDE 或者 直接运行jar）

Default port 9000

	$ mvn clean package
	$ nohup java -jar weixin-server-demo-0.0.1.war &

Changed the port when necessary

	$ nohup java -jar weixin-server-demo-0.0.1.war --server.port=8989 &
	
# 本地调试微信公众平台服务端

安装 [ngrok](https://ngrok.com/), Mac,Windows,Linux版本都有的，然后启动。

	ngrok http 9000
	
你会看到类似下面的输出，本机打开 http://127.0.0.1:4040 监控     
	
	ngrok by @inconshreveable                                                             (Ctrl+C to quit)
                                                                                                      
	Tunnel Status                 online                                                                  
	Version                       2.1.3                                                                   
	Region                        United States (us)                                                      
	Web Interface                 http://127.0.0.1:4040                                                   
	Forwarding                    http://cdae7378.ngrok.io -> localhost:9000                              
	Forwarding                    https://cdae7378.ngrok.io -> localhost:9000                             
	                                                                                                      
	Connections                   ttl     opn     rt1     rt5     p50     p90                             
	                              121     0       0.00    0.00    0.67    4.98                            
	                                                                                                      
	HTTP Requests                                                                                         
	-------------                                                                                         
	                                                                                                      
	POST /weixin/connect           200 OK     
	
[微信测试号界面 - 接口配置信息修改](http://mp.weixin.qq.com/debug/cgi-bin/sandboxinfo?action=showinfo&t=sandbox/index)

请填写接口配置信息，此信息需要你有自己的服务器资源，填写的URL需要正确响应微信发送的Token验证，请阅读消息接口使用指南。

	URL http://cdae7378.ngrok.io/weixin/connect
	Token "和上面一致"

                                                            