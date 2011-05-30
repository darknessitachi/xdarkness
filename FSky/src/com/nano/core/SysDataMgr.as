package com.nano.core{
	import com.nano.data.JHashMap;
	import com.nano.plugins.LightMsg;
	import com.nano.serialization.json.JSON;
	import com.nano.util.CollectionUtil;
	
	import flash.events.Event;
	import flash.events.HTTPStatusEvent;
	import flash.events.IOErrorEvent;
	import flash.events.ProgressEvent;
	import flash.events.SecurityErrorEvent;
	import flash.net.Socket;
	import flash.net.URLLoader;
	import flash.net.URLRequest;
	import flash.net.URLRequestMethod;
	import flash.net.URLVariables;
	
	import mx.controls.Alert;
	import mx.rpc.events.FaultEvent;
	
	/**
	 * 该类为全局数据加载管理器，所有数据加载操作都通过该类的接口进行。
	 * 加载数据操作分为三类：
	 * 1、使用HTTP请求的字符串数据；
	 * 2、二进制数据；
	 * 3、使用Socket进行的推送数据。
	 * 注意：整个cmp工程所有加载数据的操作都通过此接口实现，请不要自己重复实现加载数据的代码，
	 * 以避免产生因为flash player版本升级而导致的问题。
	 * @version	1.2
	 * @author 章小飞 zhangxf5@asiainfo-linkage.com
	 * @since  2010-04-21
	 */ 
	public class SysDataMgr{
		private static var socket:Socket;
		private static var dataListeners:JHashMap=new JHashMap();
		
		/**
		 * 构造方法
		 */
		public function SysDataMgr() {
			throw new Error("不能实例化数据管理工具类");
		}
		
		/**
		 * 请求数据
		 * 已过期，推荐使用更灵活的SysDataMgr.req(config:*)方法
		 */ 
		public static function request(url:String,dataObj:*,successFunc:Function=null,scope1:*=null,errorFunc:Function=null,scope2:*=null):* {
			req({
				url:url,
				param:dataObj,
				success:{
					scope:scope1,
					fn:successFunc
				},
				failure:{
					scope:scope2,
					fn:errorFunc
				}
			});
			return true;
		}
		
		/**
		 * 此方法用来替代上面的request
		 * 对参数做了更灵活的处理，建议使用此方法
		 * @param	conf
		 * @return
		 */
		public static function req(conf:Object):* {
			if (!conf||!conf.url) {
				if (conf && conf.shortUrl) {
					conf.url = SysConfMgr.getUrl(conf.shortUrl);
				} else {
					Alert.show("使用SysDataMgr加载数据必须包含url参数","参数错误");
					return false;
				}
			}
			try{
				//请求参数
				var data:URLVariables=new URLVariables();
				if(conf.param&&conf.param is Object){
					NanoSystem.apply(data,conf.param);
				}
				data.killCache=new Date().getTime();
				
				var loader:URLLoader=new URLLoader();
				
				//加载成功回调
				if(conf.success){
					var fn:Function = NanoSystem.emptyFn;
					var scope:Object={};
					if(conf.success is Function){
						fn=conf.success;
						scope=conf.success;
					}else if(conf.success is Object){
						fn=conf.success.fn is Function?conf.success.fn:fn;
						scope=conf.success.scope?conf.success.scope:scope;
					}
					loader.addEventListener(Event.COMPLETE,function(e:Event):void{
						var data:*=loader.data;
						var uncatch:Boolean = conf.uncatch || false;
						if(data && !uncatch){
							try{
								data=JSON.decode(data);
								if(data&&(data.errorMsg||data.error)){
									Alert.show(data.errorMsg||data.error||"加载数据出错","提示");
								}
							}catch(error:*){
								trace("SysDataMgr自动解析响应失败，不是标准的响应数据格式。");
							}
						}
						fn.call(scope,loader.data);
					});
				}
				
				//加载失败回调
				var failureFn:Function=NanoSystem.emptyFn;
				var failureScope:Object={};
				if(conf.failure){
					if(conf.failure is Function){
						failureFn=conf.failure;
						failureScope=conf.failure;
					}else if(conf.failure is Object){
						failureFn=conf.failure.fn is Function?conf.failure.fn:failureFn;
						failureScope=conf.failure.scope?conf.failure.scope:scope;
					}
					loader.addEventListener(FaultEvent.FAULT,function(e:Event):void{
						failureFn.call(failureScope,loader.data);
					});	
				}
				
				
				loader.addEventListener(HTTPStatusEvent.HTTP_STATUS,function(e:HTTPStatusEvent):void{	
					//var msg:LightMsg=new LightMsg();
					//msg.show({title:'提示',msg:"登录超时或权限不足，请重新登录！"});					
					if(e.status == 403 ){					
						//Alert.show("登录超时或权限不足，请重新登录！"+e.status);						
						//Login.closeAndLogout();
					}
				});
				
				//必须处理流错误，否则页面会弹出异常框框
				loader.addEventListener(IOErrorEvent.IO_ERROR,function(...args):void{
					failureFn.call(failureScope,loader.data);
				});
				
				var urlReq:URLRequest=new URLRequest(conf.url);
				urlReq.method=URLRequestMethod.POST;
				urlReq.data=data;
				loader.load(urlReq);
				
			}catch(err:Error){
				trace(err);
				return false;
			}
			return true;
		}
		
		/**
		 * 监听Socket推送的数据
		 * 后台主动推送的数据必须满足如下结构：
		 * [
		 * 	{title:'alarm',data:{}},
		 * 	{title:'route',data:{}}
		 * ]
		 * title表示数据“主题”，data部分表示数据体。
		 * 主题title必须是字符串，data部分必须是格式合法的JSON字符串，否则将会抛出JSON对象解析异常。
		 * SysDataMgr接受到推送的数据之后将调用监听对应主题的函数，监听函数将会被以data为参数进行调用。
		 * 注意：SysDataMgr默认不会与服务端建立Socket连接，直到第一次调用此方法。
		 * 	    SysDataMgr建立Socket连接之后不会主动释放连接。
		 * @param	title
		 * @param	fn
		 */
		public static function listen(title:String,fn:Function):void{
			connect();
			//注册监听函数
			var arr:Array=dataListeners.get(title);
			if(!arr){
				arr=[fn];
			}else{
				if(arr.indexOf(fn)!=-1){//已经包含了相同的监听函数
					return;
				}
				arr.push(fn);
			}
			dataListeners.put(title+"",arr);
		}
		
		/**
		 * 注销监听函数
		 */ 
		public static function unlisten(title:String,fn:Function):void{
			var arr:Array=dataListeners.get(title);
			if(arr){
				var index:int=arr.indexOf(fn);
				if(index!=-1){
					arr.splice(index,1);
				}
			}
		}
		
		/**
		 * 使用socket发送数据
		 * @param	msg
		 */
		public static function sendData(msg:String):void{
			connect();
			socket.writeUTFBytes(msg);
			socket.flush();
		}
		
		/**
		 * 私有
		 * 尝试进行Socket连接
		 */
		private static function connect():void {
			try{
				if(!socket){
					socket=new Socket();
					socket.addEventListener(Event.CONNECT,function(...args):void{
						trace("尝试Socket连接成功...");
						LightMsg.alert("提示","尝试Socket连接成功。");
					});
					socket.addEventListener(IOErrorEvent.IO_ERROR,function(...args):void{
						trace("连接失败...");
						LightMsg.alert("提示","尝试Socket连接失败。");
					});
					socket.addEventListener(Event.CLOSE,function(...args):void{
						trace("连接关闭...");
						LightMsg.alert("提示","Socket连接被关闭。");
					});
					socket.addEventListener(SecurityErrorEvent.SECURITY_ERROR,function(...args):void{
						trace("安全沙箱冲突...");
						LightMsg.alert("提示","Socket连接安全沙箱冲突。");
					});
					socket.addEventListener(ProgressEvent.SOCKET_DATA,function():void{
						try{
							var data:String="";
							while (socket.bytesAvailable){
								data+=socket.readUTFBytes(socket.bytesAvailable);
							}
							var dataArr:Array=JSON.decode(data);
							CollectionUtil.each(dataArr,function(item:Object):void{
								var fnArr:Array=dataListeners.get(item.title+"");
								//trace("主题>"+item.title);
								CollectionUtil.each(fnArr,function(fn:Object):void{
									//trace("调用监听函数");
									if(fn&&fn is Function){
										(fn as Function).call(fn,item.data);
									}
								});
							});
						}catch(err:Error){
							trace(err);
						}
					});
					socket.connect(SysConfMgr.getUrl("socketIp",true),int(SysConfMgr.getUrl("socketPort",true)));
				}
				if(!socket.connected){
					socket.connect(SysConfMgr.getUrl("socketIp",true),int(SysConfMgr.getUrl("socketPort",true)));
				}
			}catch(err:Error){
				trace(err);
				Alert.show("SysDataMgr尝试Socket连接失败，IP地址>"+SysConfMgr.getUrl("socketIp",true)+"，端口>"+SysConfMgr.getUrl("socketPort",true),"严重");
			}
		}
	}
}