package com.nano.widgets {
	import flash.geom.Point;
	/**
	 * 虚线形状
	 * @version	1.1
	 * @author	章小飞 zhangxf5@asiainfo-linkage.com
	 * @since  	2009-09-22
	 */
	public class JDashedShape extends JFreePencil {
		/**
		 * 构造方法
		 * @param	x
		 * @param	y
		 * @param	isFill
		 * @param	strokeWidth
		 * @param	strokeColor
		 * @param	strokeTrans
		 */
		public function JDashedShape(x:Number=0,y:Number=0,isFill:Boolean = false,strokeWidth:Number=1, strokeColor:Number=0x000000,strokeTrans:Number=1) {
			super(x,y,isFill,strokeWidth,strokeColor,strokeTrans);
		}
		
		/**
		 * 绘制组件
		 */
		override public function drawWidget():void {
//			var firstPoint:Point = this.path[0];
//			this.beginFill();
//			this.moveTo(firstPoint.x, firstPoint.y,false);
//			var len:Number = this.path.length;
//			for (var i:Number = 1; i < len; i++ ) {
//				var lastPoint:Point = this.path[i - 1];
//				var currPoint:Point = this.path[i];
//				this.fill.graphics.lineTo(currPoint.x, currPoint.y);
//				JDashedLine.createDashedLine(this.stroke,lastPoint.x,lastPoint.y,currPoint.x,currPoint.y);
//			}
//			this.endFill();
		}
	}
}