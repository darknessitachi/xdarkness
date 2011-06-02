package com.sinosoft.fsky.component
{
	import com.sinosoft.fsky.component.window.CursorMgr;
	import com.sinosoft.fsky.component.window.Dimension;
	import com.sinosoft.fsky.component.window.SystemSound;
	
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
	
	import spark.components.BorderContainer;
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
	 * @date 2011-06-01 10:53 PM
	 * @version 1.2 添加改变窗体大小功能
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
			
			init();
		}
		
		/**
		 * 初始化窗口，分发windowCreateEvent、taskbarButtonCreateEvent。
		 * 
		 */
		private function init():void
		{
			this.addEventListener(MouseEvent.MOUSE_DOWN, window_mouseDownHandler);
			this.addEventListener(MouseEvent.MOUSE_MOVE, window_mouseMoveHandler);
			this.addEventListener(MouseEvent.MOUSE_OUT, window_mouseOutHandler);
			FlexGlobals.topLevelApplication.parent.addEventListener(MouseEvent.MOUSE_UP, parent_mouseUpHandler);
			FlexGlobals.topLevelApplication.parent.addEventListener(MouseEvent.MOUSE_MOVE, parent_mouseMoveHandler);
			//this.visible = false;
			//this.includeInLayout = false;
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
			else if (instance == maskLayer)
			{
				maskLayer.visible = false;
				maskLayer.includeInLayout = false;
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
			if (resizer == null)
			{
				var afterBounds:Rectangle = new Rectangle(Math.round(event.stageX - this.offsetX),Math.round(event.stageY - this.offsetY),width,height);
				var point:Point = new Point();
				point.x = afterBounds.x;
				point.y = (afterBounds.y<0)?0:(afterBounds.y>parent.height-60)?parent.height-60:afterBounds.y;
				setPosition(point);
			}
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
		
		[SkinPart(required="true")]
		/**
		 * 窗口禁用时的遮罩层，这是一个必要的外观部件。
		 */
		public var maskLayer:BorderContainer;
		
		/**
		 * 是否可改变窗口大小 
		 */
		public var resizable:Boolean = true;
		
		/**
		 * 当前鼠标形状。 
		 */
		private var cursorState:Number = 0;
		
		/**
		 * 光标在窗口边缘处变换的感应距离。
		 */
		public static var MOUSE_INDUCTION_MARGIN:Number = 4;
		
		/**
		 * 当前正在改变大小的窗口。 
		 */
		private static var resizer:XWindow;
		
		private var _disabled:Boolean = false;
		
		private var originalSize:Dimension;
		private var originalPos:Point;
		private var originalMousePos:Point;
		
		/**
		 * 是否禁用此窗口，不响应任何鼠标键盘事件
		 * @return 
		 * 
		 */
		public function get disabled():Boolean
		{
			return _disabled;
		}
		public function set disabled(value:Boolean):void
		{
			_disabled = value;
			maskLayer.visible = value;
			maskLayer.includeInLayout = value;
		}
		/**
		 * 处理窗口鼠标按下事件，置前该窗口。 
		 * @param event
		 * 
		 */
		protected function window_mouseDownHandler(event:MouseEvent):void
		{
			if(disabled)
			{
				SystemSound.DING.play();
			}
//			show();
			if (this.cursorState != CursorMgr.SIDE_OTHER)
			{
				resizer = this;
				resizer.originalSize = this.getSize();
				resizer.originalPos = this.getPosition();
				resizer.originalMousePos = this.localToGlobal(new Point(resizer.mouseX, resizer.mouseY));
			}
		}
		protected function window_mouseMoveHandler(event:MouseEvent):void
		{
			if (resizer == null && !disabled && resizable)
			{
				var px:Number = FlexGlobals.topLevelApplication.parent.mouseX; 
				var py:Number = FlexGlobals.topLevelApplication.parent.mouseY;
				//右下
				if(px >= (this.x + this.width - XWindow.MOUSE_INDUCTION_MARGIN) && py >= (this.y + this.height - XWindow.MOUSE_INDUCTION_MARGIN))
				{
					CursorMgr.setCursor(CursorMgr.NWSE, -6, -6);
					this.cursorState = CursorMgr.SIDE_RIGHT | CursorMgr.SIDE_BOTTOM;
				}
					//左上
				else if(px <= (this.x + XWindow.MOUSE_INDUCTION_MARGIN) && py <= (this.y + XWindow.MOUSE_INDUCTION_MARGIN))
				{
					CursorMgr.setCursor(CursorMgr.NWSE, -10, -10);
					this.cursorState = CursorMgr.SIDE_LEFT | CursorMgr.SIDE_TOP;
				}
					//左下
				else if(px <= (this.x + XWindow.MOUSE_INDUCTION_MARGIN) && py >= (this.y + this.height - XWindow.MOUSE_INDUCTION_MARGIN))
				{
					CursorMgr.setCursor(CursorMgr.NESW, -10, -6);
					this.cursorState = CursorMgr.SIDE_LEFT | CursorMgr.SIDE_BOTTOM;
				}
					//右上
				else if(px >= (this.x + this.width - XWindow.MOUSE_INDUCTION_MARGIN) && py <= (this.y + XWindow.MOUSE_INDUCTION_MARGIN))
				{
					CursorMgr.setCursor(CursorMgr.NESW, -6, -10);
					this.cursorState = CursorMgr.SIDE_RIGHT | CursorMgr.SIDE_TOP;
				}
					//右
				else if(px >= (this.x + this.width - XWindow.MOUSE_INDUCTION_MARGIN))
				{
					CursorMgr.setCursor(CursorMgr.EW, -8, 0);
					this.cursorState = CursorMgr.SIDE_RIGHT;
				}
					//左
				else if(px <= (this.x + XWindow.MOUSE_INDUCTION_MARGIN))
				{
					CursorMgr.setCursor(CursorMgr.EW, -14, 0);
					this.cursorState = CursorMgr.SIDE_LEFT;
				}
					//下
				else if(py >= (this.y + this.height - XWindow.MOUSE_INDUCTION_MARGIN))
				{
					CursorMgr.setCursor(CursorMgr.NS, 0, -10);
					this.cursorState = CursorMgr.SIDE_BOTTOM;
				}
					//上
				else if(py <= (this.y + XWindow.MOUSE_INDUCTION_MARGIN))
				{
					CursorMgr.setCursor(CursorMgr.NS, 0, -14);
					this.cursorState = CursorMgr.SIDE_TOP;
				}
				else
				{
					CursorMgr.setCursor(null, 0, 0);
					this.cursorState = CursorMgr.SIDE_OTHER;
				}
			}
		}

		protected function window_mouseOutHandler(event:MouseEvent):void
		{
			if (resizer == null)
			{
				CursorMgr.setCursor(null, 0, 0);
				this.cursorState = CursorMgr.SIDE_OTHER;
			}
		}
		
		protected function parent_mouseUpHandler(event:MouseEvent):void
		{
			resizer = null;
		}
		
		protected function parent_mouseMoveHandler(event:MouseEvent):void
		{
			if (resizer != null && resizable)
			{
				var px:Number = FlexGlobals.topLevelApplication.parent.mouseX - resizer.originalMousePos.x;
				var py:Number = FlexGlobals.topLevelApplication.parent.mouseY - resizer.originalMousePos.y;
				
				switch(this.cursorState)
				{
					case CursorMgr.SIDE_RIGHT | CursorMgr.SIDE_BOTTOM :
						resizer.setSize(
							new Dimension(resizer.originalSize.width + px > this.minWidth ? resizer.originalSize.width + px : this.minWidth,
								resizer.originalSize.height + py > this.minHeight ? resizer.originalSize.height + py : this.minHeight)
						);
						break;
					case CursorMgr.SIDE_LEFT | CursorMgr.SIDE_TOP:
						resizer.setPosition(
							new Point(px < resizer.originalSize.width - this.minWidth ? resizer.originalPos.x + px : resizer.x,
								py < resizer.originalSize.height - this.minHeight ? resizer.originalPos.y + py : resizer.y)
						);
						resizer.setSize(
							new Dimension(resizer.originalSize.width - px > this.minWidth ? resizer.originalSize.width - px : this.minWidth,
								resizer.originalSize.height - py > this.minHeight ? resizer.originalSize.height - py : this.minHeight)
						);
						break;
					case CursorMgr.SIDE_LEFT | CursorMgr.SIDE_BOTTOM:
						resizer.x = px < resizer.originalSize.width - this.minWidth ? resizer.originalPos.x + px: resizer.x;
						resizer.setSize(
							new Dimension(resizer.originalSize.width - px > this.minWidth ? resizer.originalSize.width - px : this.minWidth,
								resizer.originalSize.height + py > this.minHeight ? resizer.originalSize.height + py : this.minHeight)
						);
						break;
					case CursorMgr.SIDE_RIGHT | CursorMgr.SIDE_TOP:
						resizer.y = py < resizer.originalSize.height - this.minHeight ? resizer.originalPos.y + py : resizer.y;
						resizer.setSize(
							new Dimension(resizer.originalSize.width + px > this.minWidth ? resizer.originalSize.width + px : this.minWidth,
								resizer.originalSize.height - py > this.minHeight ? resizer.originalSize.height - py : this.minHeight)
						);
						break;
					case CursorMgr.SIDE_RIGHT:
						resizer.width = resizer.originalSize.width + px > this.minWidth ? resizer.originalSize.width + px : this.minWidth;
						break;
					case CursorMgr.SIDE_LEFT:
						resizer.x = px < resizer.originalSize.width - this.minWidth ? resizer.originalPos.x + px : resizer.x;
						resizer.width = resizer.originalSize.width - px > this.minWidth ? resizer.originalSize.width - px : this.minWidth;
						break;
					case CursorMgr.SIDE_BOTTOM:
						resizer.height = resizer.originalSize.height + py > this.minHeight ? resizer.originalSize.height + py : this.minHeight;
						break;
					case CursorMgr.SIDE_TOP:
						resizer.y = py < resizer.originalSize.height - this.minHeight ? resizer.originalPos.y + py : resizer.y;
						resizer.height = resizer.originalSize.height - py > this.minHeight ? resizer.originalSize.height - py : this.minHeight;
						break;
				}
			}
		}
		
		
		/**
		 * 以 Dimension 对象的形式返回窗口的大小。Dimension 对象的 height 字段包含此窗口的高度，而 Dimension 对象的 width 字段则包含此窗口的宽度。
		 * @return 
		 * 
		 */
		public function getSize():Dimension
		{
			return new Dimension(this.width, this.height);
		}
		
		/**
		 * 调整窗口的大小，使其宽度为 d.width，高度为 d.height。 
		 * @param d
		 * 
		 */
		public function setSize(d:Dimension):void
		{
			this.width = d.width;
			this.height = d.height;
		}
		
		/**
		 * 以 Point 对象的形式返回窗口原点的坐标。Point 对象的 x 字段包含此窗口原点的 x 坐标，而 Point 对象的 y 字段则包含此窗口原点的 y 坐标。
		 * @return 
		 * 
		 */
		public function getPosition():Point
		{
			return new Point(this.x, this.y);
		}
	}
}