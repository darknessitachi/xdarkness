package com.sinosoft.fsky.util
{
	import flash.events.Event;
	import flash.net.URLLoader;
	import flash.net.URLRequest;
	import mx.controls.Alert;
	
	public class XMLLoader
	{
		private var _url:String;
		private var _onComplete:Function;
		
		public function XMLLoader(url:String, onComplete:Function)
		{
			this._url = url;
			this._onComplete = onComplete;
			
			loadXml();
		}
		
		private function loadXml():void
		{
			var loader:URLLoader = new URLLoader();
			var request:URLRequest = new URLRequest(this._url);
			loader.load(request);
			loader.addEventListener(Event.COMPLETE,onLoadComplete);
		}
		
		private function onLoadComplete(event:Event):void{
			var loader:URLLoader = event.target as URLLoader;
			if(loader != null){
				var xml:XML = new XML(loader.data);
				this._onComplete(xml);
			} else {
				Alert.show("请求的XML资源["+this._url+"]不存在！","提示");
			}
		}
	}
}