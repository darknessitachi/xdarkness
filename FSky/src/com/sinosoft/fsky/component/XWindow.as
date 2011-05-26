package com.sinosoft.fsky.component
{
	import flash.display.DisplayObject;
	import flash.events.MouseEvent;
	import flash.geom.Point;
	import flash.utils.setTimeout;
	
	import mx.core.FlexGlobals;
	import mx.core.UIComponent;
	import mx.effects.Zoom;
	import mx.events.CloseEvent;
	import mx.managers.PopUpManager;
	
	import spark.components.Image;
	import spark.components.SkinnableContainer;
	
	/**
	 * 窗体面板
	 * 
	 * @author Darkness
	 * @date 2011-05-25 09:00 PM
	 * @version 1.0  从事件源target开始渐渐变大，关闭时缩放回事件源
	 * 
	 * @date 2011-05-26 10:25 AM
	 * @version 1.0.1 修正窗体起始位置为事件源target的位置
	 * @description 
	 * 		第一次需要将其坐标设置居中位置
	 *  	其余将其坐标设置成事件源位置
	 *  	否则窗体效果位置不正确，原因未知
	 *		 
	 *		if(!zoomStart) {
	 *			this.x = moveX;
	 *			this.y = moveY;
	 *		} else {
	 *			this.x = targetPosition.x + target.width/2;
	 *			this.y = targetPosition.y + target.height/2;
	 *		}
	 * 
	 * @date 2011-05-26 08:06 PM
	 * @version 1.0.2 修正target在全局的精确坐标，通过 realTarget.localToGlobal(new Point(0,0))方式转换坐标系，获得精确的全局坐标
	 * 
	 * @date 2011-05-26 08:45 PM
	 * @version 1.0.3 当target为null时不执行Zoom效果
	 * 
	 */
	public class XWindow extends SkinnableContainer
	{
		
		[SkinPart(required="false")]
		public var btnClose:Image;
		
		[Bindable]
		public var title:String = "";
		
		public function XWindow()
		{
			super();
			
			setStyle("skinClass", com.sinosoft.fsky.component.XWindowSkin);
		}
		
		[Bindable]
		private var moveY:Number = 0;// 初始位置Y
		[Bindable]
		private var moveX:Number = 0;// 初始位置X
		
		private var inited:Boolean = false;// 窗体是否已初始化
		private var isOpen:Boolean = false;// 窗体是否打开
		
		private var zoomStart:Zoom;
		private var zoomEnd:Zoom = new Zoom();
		
		override public function initialize():void {
			super.initialize();
			
			// 定位初始化其实坐标，使其居中
			moveY = (FlexGlobals.topLevelApplication.height - this.height) / 2;
			moveX = (FlexGlobals.topLevelApplication.width - this.width) / 2;
		}
		
		/**
		 * 移除窗体
		 */
		private function removeFromWindow():void {
			isOpen = false;
			PopUpManager.removePopUp(this);
		}
		
		/**
		 * 关闭窗体
		 */
		private function closeFormWindow(event:CloseEvent):void {
			hide();
		}
		
		/**
		 * 显示窗体
		 */
		public function show(target:Object, parent:DisplayObject,modal:Boolean = false):void
		{
			// 已经打开，则返回
			if(isOpen) {
				return;
			}
			
			isOpen = true;
			
			this.addEventListener(CloseEvent.CLOSE, closeFormWindow);
			
			PopUpManager.addPopUp(this, parent, modal);
			PopUpManager.centerPopUp(this);
			
			if(target) {
				effectStart(target);
			}
			
			this.visible=true;
		}
		
		/**
		 * 移除窗体
		 */
		public function hide():void { 
			
			if(zoomStart) {
				zoomEnd.play();
				
				setTimeout(removeFromWindow, 1500);
			} else {
				removeFromWindow();
			}
			
			this.removeEventListener(CloseEvent.CLOSE, closeFormWindow);
		}
		
		/**
		 * zoom效果
		 */
		private function effectStart(target:Object):void {
			
			var realTarget:UIComponent = UIComponent(target);
			var targetPosition:Point = realTarget.localToGlobal(new Point(0,0));
			
			/**
			 * 第一次需要将其坐标设置居中位置
			 * 其余将其坐标设置成事件源位置
			 * 否则窗体效果位置不正确，原因未知
			 */
			if(!zoomStart) {
				this.x = moveX;
				this.y = moveY;
			} else {
				this.x = targetPosition.x + target.width/2;
				this.y = targetPosition.y + target.height/2;
			}
			
			if(!zoomStart) {
				
				zoomStart = new Zoom();
				
				zoomStart.zoomHeightFrom = 0;
				zoomStart.zoomHeightTo = 1;
				zoomStart.zoomWidthFrom = 0;
				zoomStart.zoomWidthTo = 1;
				zoomStart.target = this;
				zoomStart.duration = 1000;
				
				var ox:Number =  targetPosition.x - moveX + target.width/2;
				var oy:Number = targetPosition.y - moveY + target.height/2;
				zoomStart.originX = ox;
				zoomStart.originY = oy;
				
				zoomEnd.zoomHeightFrom = 1;
				zoomEnd.zoomHeightTo = 0;
				zoomEnd.zoomWidthFrom = 1;
				zoomEnd.zoomWidthTo = 0;
				zoomEnd.target = this;
				zoomEnd.duration = 1000;
				
				zoomEnd.originX = ox;
				zoomEnd.originY = oy;
				
			}
			
			trace("x:" + this.x + ",y:" + this.y + "height:" + this.height + ",width:" + this.width + "centerX:" + moveX + "," + "centerY:" + moveY + ",originX:" + zoomStart.originX + ",originY:" + zoomStart.originY);
			
			zoomStart.play();
		}
		
		/**
		 * 覆写父类方法：添加part时加入监听事件，给按钮添加监听函数
		 */
		protected override function partAdded(partName:String, instance:Object):void
		{
			super.partAdded(partName, instance);
			
			if (instance == btnClose) {
				btnClose.addEventListener(MouseEvent.CLICK, close);
			} 
		}
		
		/**
		 * 覆写父类方法：移除part时加入监听事件，移除按钮上的监听函数
		 */
		protected override function partRemoved(partName:String, instance:Object):void
		{
			super.partRemoved(partName,instance);
			
			if (instance == btnClose) {
				btnClose.removeEventListener(MouseEvent.CLICK, close);
			} 
		}
		
		private function close(event:MouseEvent):void {
			dispatchEvent(new CloseEvent(CloseEvent.CLOSE));
		}
	}
}