package com.nano.widgets {
	import flash.display.Sprite;
	/**
	 * 虚线矩形
	 * <pre>
	 * 		使用示例:
	 * 		var rect:JDashedRect=new JDashedRect(0,0);
	 * 		this.addChild(rect);
	 * 		rect.strokeStyle(3,0xff0000,0.8);
	 * 		rect.fillStyle(0x00ff00,0.5);
	 * 		rect.setEndPoint(100,100);
	 * </pre>
	 * @version	1.1
	 * @author	章小飞 zhangxf5@asiainfo-linkage.com
	 * @since  	2009-09-22
	 */
	public class JDashedRect extends JRect {
		/**
		 * 边框
		 */
		private var stroke:Sprite = new Sprite();
		/**
		 * 填充
		 */
		private var fill:Sprite = new Sprite();
		
		/**
		 * 构造方法
		 * @param	beginX
		 * @param	beginY
		 * @param	w
		 * @param	h
		 */
		public function JDashedRect(x:Number=0,y:Number=0) {
			super(x, y);
			this.addChild(this.fill);
			this.addChild(this.stroke);
		}
		
		/**
		 * 移动画笔
		 * @param	x
		 * @param	y
		 */
		private function moveTo(x:Number,y:Number) :void {
			this.stroke.graphics.moveTo(x, y);
			this.fill.graphics.moveTo(x, y);
		}
		
		/**
		 * 画笔画线
		 * @param	x
		 * @param	y
		 */
		private function lineTo(x:Number,y:Number):void {
			this.stroke.graphics.lineTo(x, y);
			this.fill.graphics.lineTo(x, y);
		}
		
		/**
		 * 清理
		 */
		private function clear():void {
			this.stroke.graphics.clear();
			this.stroke.graphics.lineStyle(this.strokeW,this.strokeColor,this.strokeTrans);
			this.fill.graphics.clear();
			this.beginFill(this.fillColor, this.fillTrans);
			this.moveTo(0, 0);
		}
		
		/**
		 * 开始填充
		 * @param	fillColor
		 * @param	fillTrans
		 */
		public function beginFill(fillColor:Number=0xffffff,fillTrans:Number=0):void {
			this.fillColor = fillColor;
			this.fillTrans = fillTrans;
			this.fill.graphics.beginFill(this.fillColor,this.fillTrans);
		}
		
		/**
		 * 结束填充
		 */
		public function endFill():void {
			this.fill.graphics.endFill();
		}
		
		/**
		 * 方法覆盖：更新矩形视图
		 */
		override public function drawWidget():void {
			this.clear();
			var x1:Number = this.strokeW;
			var y1:Number = this.strokeW;
			var x2:Number = this.endX-this.strokeW;
			var y2:Number = this.endY-this.strokeW;
			
			this.fill.graphics.moveTo(x1, y1);
			this.fill.graphics.lineTo(x2, y1);
			this.fill.graphics.lineTo(x2, y2);
			this.fill.graphics.lineTo(x1, y2);
			this.fill.graphics.lineTo(x1, y1);
			
			JDashedLine.createDashedLine(this.stroke,0, 0, x2+this.strokeW, 0);
			JDashedLine.createDashedLine(this.stroke,x2+this.strokeW, 0, x2+this.strokeW, y2+this.strokeW);
			JDashedLine.createDashedLine(this.stroke,x2+this.strokeW, y2+this.strokeW, 0, y2+this.strokeW);
			JDashedLine.createDashedLine(this.stroke,0, y2+this.strokeW, 0, 0);
		}
	}
}