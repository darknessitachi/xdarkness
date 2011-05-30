package com.nano.widgets {
	import com.nano.math.geom.JLineUtil;
	/**
	 * 虚线
	 * <pre>
	 * 		var line:JDashedLine=new JDashedLine();
	 * 		this.addChild(line);
	 * 		line.strokeStyle(3,0xff0000,0.8);
	 * 		line.moveTo(0,0);
	 * 		line.lineTo(100,100);
	 * </pre>
	 * @version	1.2
	 * @author	章小飞 zhangxf5@asiainfo-linkage.com
	 * @since  	2009-09-22
	 */
	public class JDashedLine extends JLine {
		/**
		 * 一段完整的虚线中，长线、空白、短线、空白按次序所占的像素数
		 */
		public static var arr:Array = new Array(5, 5, 5, 5);
		
		/**
		 * 构造方法
		 * @param	x			起始点x坐标
		 * @param	y			起始点y坐标
		 * @param	strokeW		线宽
		 * @param	strokeColor	颜色
		 * @param	strokeTrans	不透明度
		 * @param	arr			虚线、空白数组
		 */
		public function JDashedLine(x1:Number = 0,y1:Number=0,strokeW:Number=1,strokeColor:Number=0x000000,strokeTrans:Number=1,arr:Array=null) {
			super(x1,y1,strokeW,strokeColor,strokeTrans);
			if (arr!=null&&arr.length>0) {
				arr = arr;
			}
		}
		
		/**
		 *	方法覆盖：绘制组件
		 */
		override public function drawWidget():void {
			this.graphics.clear();
			this.graphics.lineStyle(this.strokeW, this.strokeColor, this.strokeTrans);
			this.graphics.moveTo(0,0);
			//计算坐标轴上的变化方向
			var directX:Number = 0>this.endX? -1:1;	
			var directY:Number = 0>this.endY? -1:1;
			//记录当前已经绘制到的坐标点
//			var currX:Number = this.x1;
//			var currY:Number = this.y1;
			//计算斜率
			var slope:Number = (this.endY-0) / (this.endX -0);
			//绘制虚线
			JDashedLine.createDashedLine(this,0,0, this.endX, this.endY);
		}
		
		/**
		 * 工具方法：在两点之间绘制一条虚线
		 * @param	target
		 * @param	x1
		 * @param	y1
		 * @param	endX
		 * @param	endY
		 */
		public static function createDashedLine(target:*,x1:Number,y1:Number,endX:Number,endY:Number):void {
			target.graphics.moveTo(x1, y1);
			//计算坐标轴上的变化方向
			var directX:Number = x1 > endX? -1:1;	
			var directY:Number = y1 > endY? -1:1;
			//记录当前已经绘制到的坐标点
			var currX:Number = x1;
			var currY:Number = y1;
			//计算斜率
			var slope:Number = (endY - y1) / (endX - x1);
			//开始绘制
			loop1:while (Math.abs(x1-currX)<Math.abs(x1-endX)||Math.abs(y1-currY)<Math.abs(y1-endY)) {
				var len:Number = arr.length;
				for (var i:Number = 0; i < len; i++ ) {
					var dist:Number = arr[i];
					var xInc:Number = JLineUtil.getCoords(dist, slope).x;
					var yInc:Number = JLineUtil.getCoords(dist, slope).y;
					if (Math.abs(x1-currX)+xInc<Math.abs(x1-endX)||Math.abs(y1-currY)+yInc<Math.abs(y1-endY)) {
						currX += xInc * directX;
						currY += yInc * directY;
						if (i % 2 == 0) {
							target.graphics.lineTo(currX, currY);
						}else {
							target.graphics.moveTo(currX, currY);
						}
					}else {
						break loop1;
					}
				}
			}
			
			//填充未能画完的剩余部分
			target.graphics.lineTo(endX, endY);
		}
	}
}