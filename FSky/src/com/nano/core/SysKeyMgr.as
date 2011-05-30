package com.nano.core{
	import com.nano.widgets.JPen;
	
	import flash.display.Stage;
	import flash.events.KeyboardEvent;
	import flash.external.ExternalInterface;
	/**
	 * 键盘按键管理器， 由于flash无法准确检测组合按键，整个应用的按键管理全部由SysKeyMgr类完成。
	 * 对于一支画笔JPen，只有一个SysKeyMgr的实例，SysKeyMgr监测整个stage的所有事件，并负责与外部的JavaScript脚本交互。
	 * 在获得外部JavaScript监测到的按键事件之后再进行转发，由于事件判定问题，暂不支持FF等浏览器
	 * @version	1.0
	 * @author 章小飞 zhangxf5@asiainfo-linkage.com
	 * @since  2010-04-21
	 */
	public class SysKeyMgr{
		/**
		 * 舞台对象
		 */
		private var stage:Stage;
		/**
		 * 事件管理器所属的画笔
		 */
		private var pen:JPen;
		
		/**
		 * 构造函数
		 * @param	pen
		 */
		public function SysKeyMgr(pen:JPen){
			this.pen=pen;
			stage=pen.stage;
		}
		
		/**
		 * 页面脚本回调函数
		 * @param	keyResult
		 */
		private function sendKeyResult(keyResult:String):void{
			switch(keyResult){
				case 'ctrl+a':
					//pen.selectAll();
					pen.fireEvent("ctrl+a");
					break;
				case 'ctrl+s':
					pen.fireEvent("ctrl+s");
					break;
				case 'ctrl+c':
					pen.fireEvent("ctrl+c");//通过pen转发事件
					break;
				case 'ctrl+v':
					pen.fireEvent("ctrl+v");//通过pen转发事件
					break;
				default:
					break;
			}
		}
			
		/**
		 * 私有方法
		 * 键按下事件监测
		 * @param	e
		 */
		private function _keyDownHandler(event:KeyboardEvent):void{
			if(event.keyCode==17){
				ExternalInterface.call("htmlFocus");
				return;
			}
			switch(event.keyCode){
				case 46:		//delete	删除选中的对象
					pen.delSelectedObjs();
					break;
				default:
					break;
			}
		}
		
		/**
		 * 私有方法
		 * 键弹起事件监测
		 * @param	e
		 */
		private function _keyUpHandler(e:KeyboardEvent):void{
			
		}
		
		/**
		 * 开始键盘交互
		 */
		public function startInteraction():void{
			stage.addEventListener(KeyboardEvent.KEY_DOWN,_keyDownHandler);
			ExternalInterface.addCallback("sendKeyResult",sendKeyResult);
		}
		
		/**
		 * 停止键盘交互
		 */
		public function stopInteraction():void{
			stage.removeEventListener(KeyboardEvent.KEY_DOWN,_keyDownHandler);
		}
	}
}