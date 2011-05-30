package com.nano.widgets {
	import com.nano.core.JComponent;
	import com.nano.core.SysEventMgr;
	import com.nano.core.event.SelectionEvent;
	import com.nano.core.SysClassMgr;
	import com.nano.widgets.link.JLinkAble;
	
	import flash.display.GradientType;
	import flash.display.InterpolationMethod;
	import flash.display.SpreadMethod;
	import flash.events.MouseEvent;
	import flash.geom.Point;

	/**
	 * 可填充的形状
	 * <p>
	 * 指圆、椭圆、矩形、三角形、多边形等同时
	 * 具有边框和内部填充的形状
	 * </p>
	 * @version	2.0
	 * @author	章小飞 zhangxf5@asiainfo-linkage.com
	 * @since  	2009-04-06
	 */
	public class JFilledShape extends JWidget implements JLinkAble {
		include "/link/__linkable.as";
		/**
		 * 选中状态的虚框矩形
		 */
		protected var selRect:JRect = null;
		/**
		 * 是否使用渐变填充
		 */
		public var useFill:Boolean=true;
		/**
		 * 渐变填充的类型，默认为线性渐变
		 */
		public var fillType:*=FILL_TYPE.PLAIN;
		/**
		 * 静态常量，填充的类型
		 */ 
		public static const FILL_TYPE:*={GRADIENT:0,PLAIN:1,BITMAP:2}
		/**
		 * 渐变填充默认参数，在使用渐变填充时，外部直接修改该参数中对应的属性，即可提供特定的实现，
		 */
		public var gradientFillVars:*={type:GradientType.LINEAR,colors:[0xffffff,0xcccccc],alphas:[1,0.8],ratios:[0,255],matrix:null,spreadMethod:SpreadMethod.PAD,interpolationMethod:InterpolationMethod.RGB,focalPointRatio:0};
		
		/**
		 * 构造方法
		 */
		public function JFilledShape(){
			super();
			SysEventMgr.on(this,SelectionEvent.REMOVE, _onDeleteHandler);
		}

		/**
		 * 处理绘制对象时鼠标移动事件
		 * 如果子类需要特定的实现，必须覆盖此方法
		 * @param	e
		 */
		override protected function _drawMouseMove(e:MouseEvent):void {
			var p:Point = new Point(e.stageX, e.stageY);
			p = this.globalToLocal(p);
			this.setEndPoint(p.x, p.y);
		}

		/**
		 * 对象被删除事件处理函数
		 * @param	e
		 */
		protected function _onDeleteHandler(e:SelectionEvent):void{
			if (!this.parent){//对象已经从显示列表中删除，直接返回
				return;
			}
			this.fireEvent("b4remove");
			for each(var temp:JLink in this.linkFrom){
				this.removeListenerByScope(temp);
			}
			for each(var temp2:JLink in this.linkTo){
				this.removeListenerByScope(temp2);
			}
			this.linkFrom.removeAll();
			this.linkTo.removeAll();
			this.parent.removeChild(this);
		}
		
		/**
		 * 设定填充形状的尺寸，左上角坐标维持不变
		 * @param	w
		 * @param	h
		 * @param	update
		 */
		public function setSize(w:Number, h:Number, update:Boolean = true):void {
			this.setEndPoint(w, h, update);
		}

		/**
		 * 获取起始点位置
		 * @return
		 */
		public function getBeginPoint():Point {
			return new Point(this.x, this.y);
		}

		/**
		 * 在对象绘制完成后重新设定起始点位置
		 * @param	newX
		 * @param	newY
		 */
		public function setBeginPoint(newX:Number, newY:Number, update:Boolean = true):void {
			this.x = newX;
			this.y = newY;
			if (update){
				drawWidget();
			}
		}

		/**
		 * 获取结束点位置
		 * @return
		 */
		public function getEndPoint():Point {
			return new Point(this.x + this.width, this.y + this.height);
		}

		/**
		 * 设置宽度
		 * @param	w
		 * @param	update
		 */
		public function setW(w:Number, update:Boolean = true):void {
			this.endX = this.width;
			if (update){
				drawWidget();
			}
		}

		/**
		 * 设置高度
		 * @param	h
		 * @param	update
		 */
		public function setH(h:Number, update:Boolean = true):void {
			this.endY = this.height;
			if (update){
				drawWidget();
			}
		}
		
		/**
		 * 根据指定的填充类别绘制填充的形状
		 */
		protected function fillShape():void{}
		
		/**
		 * 设置组件位置
		 * @param	newX
		 * @param	newY
		 */
		override public function setPosition(newX:Number, newY:Number):void {
			super.setPosition(newX, newY);
		}

		/**
		 * 绘制组件位置
		 * @param	tx
		 * @param	ty
		 */
		override public function updatePosition(tx:Number, ty:Number):void {
			super.updatePosition(tx, ty);
		}
		
		/**
		 * 获取宽度
		 * @return
		 */
		public function getW():Number {
			return this.width;
		}

		/**
		 * 获取高度
		 * @return
		 */
		public function getH():Number {
			return this.height;
		}
		
		override public function clone(...args):JComponent{
			var ClassType:Class=SysClassMgr.getClasFromObj(this);
			var newObj:JFilledShape=new ClassType(this.x,this.y);
			newObj.strokeStyle(this.strokeW,this.strokeColor,this.strokeTrans);
			newObj.fillStyle(this.fillColor,this.fillTrans);
			newObj.useFill=this.useFill;
			newObj.fillType=this.fillType;
			newObj.endX=this.endX;
			newObj.endY=this.endY;
			newObj.drawWidget();
			newObj.transformMatrix=this.transform.matrix;
			return newObj;
		}
	}
}