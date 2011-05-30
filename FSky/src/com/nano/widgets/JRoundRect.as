package com.nano.widgets {
	import flash.geom.Matrix;
	
	/**
	 * 圆角矩形
	 * @version	1.3 2010-05-18
	 * @author	章小飞 zhangxf5@asiainfo-linkage.com
	 * @since  	V1.0
	 */
	public class JRoundRect extends JRect {
		/**
		 * 圆角宽度
		 */
		public var ellipseWidth:Number = 25;
		/**
		 * 圆角高度
		 */
		public var ellipseHeight:Number = 25;
		
		/**
		 * 构造函数
		 * @param	x
		 * @param	y
		 */
		public function JRoundRect(x:Number=0,y:Number=0) {
			super(x, y);			
		}
		
		/**
		 * 更新对象
		 */
		override public function drawWidget():void {
			this.graphics.clear();
			this.graphics.lineStyle(this.strokeW, this.strokeColor, this.strokeTrans);
			if(this.useFill){
				fillShape();
			}
			this.graphics.drawRoundRect(0,0,this.endX,this.endY,this.ellipseWidth,this.ellipseHeight);
			this.graphics.endFill();
			
			if(this.isSelected){
				this._drawSelRect();
			}
		}
		
		/**
		 * 设置圆角高度
		 * @param	h
		 */
		public function setEllipseHeight(h:Number,update:Boolean=true):void {
			this.ellipseHeight = h;
			if(update){
				drawWidget();
			}
		}
		
		/**
		 * 设置圆角宽度
		 * @param	w
		 */
		public function setEllipseWidth(w:Number,update:Boolean=true):void {
			this.ellipseWidth = w;
			if(update){
				drawWidget();
			}
		}
	}
}