package com.sinosoft.fsky.component
{
	import flash.display.DisplayObject;
	import flash.display.InteractiveObject;
	import flash.events.Event;
	import flash.events.MouseEvent;
	import flash.geom.Point;
	import flash.geom.Rectangle;
	import flash.utils.setTimeout;
	
	import mx.core.FlexGlobals;
	import mx.core.UIComponent;
	import mx.effects.Parallel;
	import mx.effects.Zoom;
	import mx.events.CloseEvent;
	import mx.events.SandboxMouseEvent;
	import mx.managers.PopUpManager;
	
	import spark.components.Image;
	import spark.components.SkinnableContainer;
	import spark.effects.Fade;
	
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
	 * @date 2011-05-31 10:36 PM
	 * @version 1.1 添加拖动功能
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
		
		/**
		 * 当用户在移动区域按下鼠标按钮，相对于该窗口原水平位置的水平位置。
		 */
		private var offsetX:Number;

		/**
		 * 当用户在移动区域按下鼠标按钮，相对于该窗口原垂直位置的垂直位置。
		 */
		private var offsetY:Number;
		
		/**
		 * 是否可拖动窗口。
		 */
		public var draggable:Boolean = true;
		
		[SkinPart(required="false")]
		/**
		 * 用户必须单击并拖动才可移动窗口的区域。
		 */
		public var moveArea:InteractiveObject;
		
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
		public function show(parent:DisplayObject,modal:Boolean = false, target:Object=null):void
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
			} else {
				getFade().play();
			}
			
			this.visible=true;
		}
		
		/**
		 * 移除窗体
		 */
		public function hide():void { 
			
			if(zoomStart) {
				zoomEnd.play();
				setTimeout(removeFromWindow, 500);
			} else {
				getFade(false).play();
				setTimeout(removeFromWindow, 500);
			}
			
			this.removeEventListener(CloseEvent.CLOSE, closeFormWindow);
		}
		
		private var fade:Fade;// 淡入淡出效果
		
		/**
		 * 窗体默认淡入淡出效果
		 */
		private function getFade(fadeIn:Boolean=true):Fade {
			
			if(fade == null) {
				fade = new Fade();
				fade.target = this;
				fade.duration = 500;
			}
			
			if(fadeIn) {
				fade.alphaFrom = 0;
				fade.alphaTo = 1;
			} else {
				fade.alphaFrom = 1;
				fade.alphaTo = 0;
			}
			
			return fade;
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
				zoomStart.duration = 500;
				
				var ox:Number =  targetPosition.x - moveX + target.width/2;
				var oy:Number = targetPosition.y - moveY + target.height/2;
				zoomStart.originX = ox;
				zoomStart.originY = oy;
				
				zoomEnd.zoomHeightFrom = 1;
				zoomEnd.zoomHeightTo = 0;
				zoomEnd.zoomWidthFrom = 1;
				zoomEnd.zoomWidthTo = 0;
				zoomEnd.target = this;
				zoomEnd.duration = 500;
				
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
			else if (instance == moveArea)
			{
				if (this.draggable)
				{
					moveArea.addEventListener(MouseEvent.MOUSE_DOWN, moveArea_mouseDownHandler);
				}
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
		
		/**
		 * 处理移动区域鼠标按下事件，当用户开始拖动窗口时调用。
		 * @param event
		 * 
		 */
		protected function moveArea_mouseDownHandler(event:MouseEvent):void
		{
			if (!draggable)
				return;
			
			offsetX = event.stageX - x;
			offsetY = event.stageY - y;
			var sbRoot:DisplayObject = systemManager.getSandboxRoot();
			sbRoot.addEventListener(MouseEvent.MOUSE_MOVE, moveArea_mouseMoveHandler, true);
			sbRoot.addEventListener(MouseEvent.MOUSE_UP, moveArea_mouseUpHandler, true);
			sbRoot.addEventListener(SandboxMouseEvent.MOUSE_UP_SOMEWHERE, moveArea_mouseUpHandler)
			systemManager.deployMouseShields(true); 
		}
		
		/**
		 * 处理移动区域鼠标移动事件，如果没有正在改变大小的窗口则可以移动窗口。
		 * @param event
		 * 
		 */
		protected function moveArea_mouseMoveHandler(event:MouseEvent):void
		{
//			if (resizer == null)
//			{
				var afterBounds:Rectangle = new Rectangle(Math.round(event.stageX - this.offsetX),Math.round(event.stageY - this.offsetY),width,height);
				var point:Point = new Point();
				point.x = afterBounds.x;
				point.y = (afterBounds.y<0)?0:(afterBounds.y>parent.height-60)?parent.height-60:afterBounds.y;
				setPosition(point);
//			}
		}
		
		/**
		 * 处理移动区域鼠标松开事件，删除移动区域的事件侦听。
		 * @param event
		 * 
		 */
		protected function moveArea_mouseUpHandler(event:Event):void
		{
			var sbRoot:DisplayObject = systemManager.getSandboxRoot();
			sbRoot.removeEventListener(MouseEvent.MOUSE_MOVE, moveArea_mouseMoveHandler, true);
			sbRoot.removeEventListener(MouseEvent.MOUSE_UP, moveArea_mouseUpHandler, true);
			sbRoot.removeEventListener(SandboxMouseEvent.MOUSE_UP_SOMEWHERE, moveArea_mouseUpHandler);
			offsetX = NaN;
			offsetY = NaN;
		}
		
		/**
		 * 调整窗口原点的坐标，使其当前x坐标为 p.x，当前y坐标为 p.y。 
		 * @param p
		 * 
		 */
		public function setPosition(p:Point):void
		{
			this.x = p.x;
			this.y = p.y;
		}
	}
}