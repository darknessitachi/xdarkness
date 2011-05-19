package com.sinosoft.fsky.remoting
{
	import flash.net.SharedObject;
	
	import mx.controls.Alert;
	import mx.messaging.Channel;
	import mx.messaging.ChannelSet;
	import mx.messaging.channels.AMFChannel;
	import mx.messaging.messages.ErrorMessage;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	import mx.rpc.remoting.mxml.RemoteObject;
	
	public class RemoteObjectManager
	{
		public static function getAMFURL():String {
			var app:SharedObject = SharedObject.getLocal("app", "/");   
			// 该对象只能在该域访问  
			return app.data.config.*.url.toString();  
		}
		
		private static var channelUri:String = "my-amf.endpoint";
		
		public static function getRemoteObject(destination:String):RemoteObject {
			
			var service : RemoteObject=new RemoteObject();
			
			var channel:Channel = new AMFChannel("my-amf", channelUri);// getAMFURL());
			
			// Action Message Format
			
			var cs : ChannelSet = new ChannelSet();
			cs.addChannel(channel);
			service.channelSet = cs;//这里指定channelSet，否则跨域访问报错
			service.destination = destination;
			service.showBusyCursor = true;
			//service.addEventListener(ResultEvent.RESULT, resultHandler);
			service.addEventListener(FaultEvent.FAULT, faultHandler);
			 
			return service;
		}
		
		private static function resultHandler(event : ResultEvent) : void
		{
			if (event.token.resultHandler == null)
			{
				return;
			}
			
			event.token.resultHandler(event.result);
		}
		
		private static function faultHandler(event : FaultEvent) : void
		{
			var errorMessage:ErrorMessage = event.message as ErrorMessage;
			
			Alert.show(errorMessage.faultString, errorMessage.faultCode.toString());
			
//			var rootCause : Object = event.fault.rootCause;
//			
//			if (event.token.faultHandler != null)
//			{
//				if (rootCause != null)
//				{
//					event.token.faultHandler(event.fault.rootCause);
//				}
//				return;
//			}
//			else if(event.fault.faultDetail==null)
//			{
//				Alert.show("Unknown Error, Please contact Administrator.");
//			}
//			else if(event.fault.faultCode == "Channel.Connect.Failed")
//			{
//				Alert.show("发送请求失败，请稍后重试!");
//			}
//			else
//			{
//				Alert.show("原因: \n"+rootCause+"\nExtra Details:\n"+event.fault.faultDetail);
//			}
		}
	}
}