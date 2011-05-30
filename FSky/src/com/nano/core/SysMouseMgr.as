package com.nano.core {
	import com.nano.widgets.JPage;
	import com.nano.widgets.JPen;
	
	import flash.display.Stage;
	import flash.events.MouseEvent;
	
	/**
	 * 鼠标动作管理器，
	 * 对于一支画笔JPen，只有一个SysKeyMgr的实例，
	 * 鼠标动作管理器完成整个应用的画面上下左右移动、缩放的鼠标操作
	 * @version	1.0
	 * @author 章小飞 zhangxf5@asiainfo-linkage.com
	 * @since  2010-04-21
	 */
	public class SysMouseMgr {
		/**
		 * 缩放系数
		 * 滚轮每滚动一格所缩放的比例
		 */ 
		public static var ZOOM_SIZE:Number = 0.1;
		/**
		 * 最小缩放比例
		 */
		public var MIN_ZOOM:Number = 0.3;
		/**
		 * 最大缩放比例
		 */
		public var MAX_ZOOM:Number=4;
		/**
		 * 舞台对象
		 */
		private var stage:Stage;
		/**
		 * 事件管理器所属的画笔
		 */
		private var pen:JPen;
		
		public function SysMouseMgr(pen:JPen) {
			this.pen=pen;
			stage=pen.stage;
		}
		
		/**
		 * 鼠标滚轮事件处理函数
		 * @param	e
		 */
		private function _mouseWheelHandler(e:MouseEvent):void {
			if (e.delta>0) {
				zoomIn(e);
			}else {
				zoomOut(e);
			}
		}
		
		/**
		 * 以鼠标当前点为中心点放大
		 */
		public function zoomIn(e:MouseEvent):void {
			var page:JPage=pen.currPage;
			if(page.scaleX>this.MAX_ZOOM){
				return;
			}
			page.setZoom(ZOOM_SIZE,ZOOM_SIZE);
		}
		
		/**
		 * 以鼠标当前点为中心点缩小
		 */
		public function zoomOut(e:MouseEvent):void {
			var page:JPage=pen.currPage;
			if(page.scaleX<this.MIN_ZOOM){
				return;
			}
			page.setZoom(-ZOOM_SIZE,-ZOOM_SIZE);
		}
		
		/**
		 * 开始监听滚轮事件
		 */
		public function startInteraction():void{
			this.pen.rootContainer.addEventListener(MouseEvent.MOUSE_WHEEL,_mouseWheelHandler);
		}
		
		/**
		 * 停止监听滚轮事件
		 */
		public function stopInteraction():void {
			this.pen.rootContainer.removeEventListener(MouseEvent.MOUSE_WHEEL,_mouseWheelHandler);
		}
	}
}