package com.nano.widgets {
	import com.nano.core.event.SelectionEvent;
	
	import flash.display.DisplayObject;
	import flash.events.Event;
	import flash.events.MouseEvent;
	import flash.geom.Rectangle;
	import com.nano.core.JComponent;
	import com.nano.core.JEditAble;
	import com.nano.core.SysEventMgr;
	
	/**
	 * 动态绘制型组件，该类极其子类主要设计用来使用graphics对象进行绘图。
	 * 因此，由于原生的sprite.graphics对象没有提供记录绘制起点和终点的API，对动态绘制过程造成了不便。
	 * 该类只要扩展一些protected型的属性，用来记录graphics对象的一些属性，
	 * 例如：绘制起点的x/y坐标，绘制终点的x/y坐标，以及笔触、填充颜色等。
	 * 所有可以使用鼠标进行绘制的组件均继承自此类，JWidget的子类都支持“绘制”功能
	 * 此类组件可以将Sprite对象及其子类看作一块“画板”来进行动态绘图操作。
	 * @version	2.0
	 * @author	章小飞 zhangxf5@asiainfo-linkage.com
	 * @since  	2009-04-06
	 */
	public class JWidget extends JComponent implements JEditAble{
		/**
		 * graphics对象的终点x坐标，相对于本地坐标系统
		 */
		public var endX:Number=0;
		/**
		 * graphics对象的终点y坐标，相对于本地坐标系统
		 */
		public var endY:Number=0;
		/**
		 * 笔触宽度
		 */
		public var strokeW:Number=1;
		/**
		 * 笔触颜色
		 */
		public var strokeColor:Number=0x000000;
		/**
		 * 笔触不透明度
		 */
		public var strokeTrans:Number = 1;
		/**
		 * 填充颜色
		 */
		public var fillColor:Number = 0xffffff;
		/**
		 * 填充不透明度
		 */
		public var fillTrans:Number = 0;
		/**
		 * 是否使用阴影
		 */
		public var useShadow:Boolean = true;
		
		/**
		 * 构造方法
		 */
		public function JWidget() {
			/**
			 * _addedToRootComplete()、_drawMouseMove()、_drawMouseUp()三个方法提供了对象绘制自身所需要的事件机制
			 * 任何JWidget的子类都默认使用相同的机制绘制自身，如果子类需要特殊的实现，必须覆盖这三个方法。
			 * 在使用JPen进行组件绘制时，组件被添加到容器之后会触发一次此事件
			 * 因此，所有的JWidget的子类既可以使用动态绘制的方式（使用JPen进行绘制），也可以在程序中直接new出对象，
			 * 然后手工设置相关的属性
			 */
			SysEventMgr.on(this,"addcomplete",_addedToRootComplete);
//			EventMgr.on(this,Event.ENTER_FRAME,this.validateWidget);
		}
		
		/**
		 * 对象被添加到显示列表结束之后触发此方法
		 * 必须使用此方法来控制对象绘制自身时的事件处理
		 * 不可使用内置的addChild()方法所触发的"added"事件
		 * 因为从"added"事件无法准确获得到stage对象的引用 
		 * @param	e
		 */
		protected function _addedToRootComplete(e:Event):void{
			var temp:DisplayObject=this as DisplayObject;
			SysEventMgr.on(temp.stage,MouseEvent.MOUSE_MOVE,_drawMouseMove);
			SysEventMgr.on(temp.stage,MouseEvent.MOUSE_UP,_drawMouseUp);
		}
		
		override protected function onRemove(...args):void{
			super.onRemove(args);
			SysEventMgr.un(this,"addcomplete",_addedToRootComplete);
//			SysEventMgr.un(this.stage,MouseEvent.MOUSE_MOVE,_drawMouseMove);
//			SysEventMgr.un(this.stage,MouseEvent.MOUSE_UP,_drawMouseUp);
		}
		
		/**
		 * 处理绘制对象时鼠标移动事件
		 * 如果子类需要特定的实现，必须覆盖此方法
		 * @param	e
		 */
		protected function _drawMouseMove(e:MouseEvent):void {
		}
		
		/**
		 * 处理绘制对象时鼠标弹起事件
		 * 如果子类需要特定的实现，必须覆盖此方法
		 * @param	e
		 */
		protected function _drawMouseUp(e:MouseEvent):void {
			var temp:DisplayObject=this as DisplayObject;
			SysEventMgr.un(temp.stage,MouseEvent.MOUSE_MOVE,_drawMouseMove);
			SysEventMgr.un(temp.stage,MouseEvent.MOUSE_UP,_drawMouseUp);
		}
		
		/**
		 * 在<b>开始绘制对象之前</b>用来设置对象样式
		 * @param	strokeW
		 * @param	strokeColor
		 * @param	strokeTrans
		 */
		public function strokeStyle(strokeW:Number,strokeColor:Number,strokeTrans:Number):void {
			this.strokeW = strokeW;
			this.strokeColor = strokeColor;
			this.strokeTrans = strokeTrans;
		}
		
		/**
		 * 在<b>开始绘制对象之前</b>设置填充样式
		 * @param	fillColor
		 * @param	fillTrans
		 */
		public function fillStyle(fillColor:Number,fillTrans:Number):void {
			this.fillColor = fillColor;
			this.fillTrans = fillTrans;
		}
		
		/**
		 * 在<b>对象绘制完成后</b>重新设置对象的笔触样式
		 * <p>调用此方法将引起图形对象graphics进行一次重绘</p>
		 * @param	strokeW
		 * @param	strokeColor
		 * @param	strokeTrans
		 */
		public function setStrokeStyle(strokeW:Number,strokeColor:Number,strokeTrans:Number):void {
			this.strokeW = strokeW;
			this.strokeColor = strokeColor;
			this.strokeTrans = strokeTrans;
			drawWidget();
		}
		
		/**
		 * 在<b>对象绘制完成后</b>重新设置对象的填充样式
		 * <p>调用此方法将引起图形对象graphics进行一次重绘</p>
		 * @param	fillColor
		 * @param	fillTrans
		 */
		public function setFillStyle(fillColor:Number,fillTrans:Number):void{
			this.fillColor = fillColor;
			this.fillTrans = fillTrans;
			drawWidget();
		}
		
		/**
		 * 绘制组件，
		 * 在对象绘制完成后，如果重新设置对象的笔触或者填充样式，
		 * 那么此方法将在内部被调用
		 * <b>
		 * <pre>
		 * 所有子类必须覆盖此方法，否则在对象绘制完成后重新设置组件的样式将没有效果
		 * </pre>
		 * </b>
		 */
		public function drawWidget():void {
			if(this.isSelected){
				this._drawSelRect();
			}
		}
		
		/**
		 * @return
		 */
		override public function getEndX():Number {
			return this.endX
		}

		/**
		 * 重写：获取结束y坐标
		 * @return
		 */
		override public function getEndY():Number {
			return this.endY
		}

		/**
		 * 重写：非选中状态
		 */ 
		override public function onDeSelect(e:SelectionEvent):void{
//			this.isSelected=false;
//			this.drawWidget();
//			this.fireEvent("deselect",this);
			super.onDeSelect(e);
			this.drawWidget();
		}
		
		/**
		 * 重写：绘制选中矩形框
		 */
		override protected function _drawSelRect():void{
			var _rect:Rectangle=this.getBounds(this);
			this.graphics.lineStyle(2,0x0000ff,0.8);
			this.graphics.beginFill(0xffffff,0);
			this.graphics.drawRect(_rect.x-this.strokeW,_rect.y-this.strokeW,_rect.width+this.strokeW*2,_rect.height+this.strokeW*2);
			this.graphics.endFill();
		}
		
		/**
		 * 一堆的getter和setter
		 * @return
		 */
		public function getStrokeW():Number{
			return this.strokeW;
		}
		
		public function setStrokeW(st:Number,flag:Boolean = false):void {
			this.strokeW = st;
			if (flag) {
				this.drawWidget();
			}
		}
		
		public function getStrokeColor():Number {
			return this.strokeColor;
		}
		
		public function setStrokeColor(sc:Number,flag:Boolean = false ):void {
			this.strokeColor = sc;
			if (flag) {
				this.drawWidget();
			}
		}
		
		public function getStrokeTrans():Number {
			return this.strokeTrans;
		}
		
		public function setStrokeTrans(st:Number,flag:Boolean = false):void {
			this.strokeTrans = st;
			if (flag) {
				this.drawWidget();
			}
		}
		
		public function getFillColor():Number {
			return this.fillColor;
		}
		
		public function setFillColor(fc:Number,flag:Boolean = false):void {
			this.fillColor = fc;
			if (flag) {
				this.drawWidget();
			}
		}
		
		public function getFillTrans():Number {
			return this.fillTrans;
		}
		
		public function setFillTrans(ft:Number,flag:Boolean = false):void {
			this.fillTrans = ft;
			if (flag) {
				this.drawWidget();
			}
		}
		
		/**
		 * 在对象绘制完成后重新设定终止点位置
		 * @param	newX
		 * @param	newY
		 */
		public function setEndPoint(newX:Number, newY:Number, update:Boolean = true):void {
			this.endX = newX?newX:0;
			this.endY = newY?newY:0;
			if (update){
				drawWidget();
			}
		}
		
//		protected function validateWidget(event:Event):void{
//			this.drawWidget();
//		}
	}
}