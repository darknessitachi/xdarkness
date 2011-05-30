package com.nano.widgets {
	import com.nano.core.JComponent;
	
	import flash.geom.Point;
	/**
	 * 带箭头的直线
	 * @version	1.0
	 * @author 章小飞 zhangxf5@asiainfo-linkage.com
	 * @since  2010-04-21
	 */
	public class JArrowLine extends JLine {
		/**
		 * 静态常量：箭头类型，0终点箭头；1起点箭头；2两端都有箭头
		 */
		public static const ARROW_TYPE:* = { END_ARROW:0, STRAT_ARROW:1, BOTH_ARROW:2};
		/**
		 * 箭头类型，默认为双端箭头
		 */
		public var arrowType:Number = ARROW_TYPE.BOTH_ARROW;
		/**
		 * 箭头三角型侧边长度
		 */
		public var arrowLen:Number = 10;
		/**
		 * 箭头一边与直线之间的夹角
		 */
		public var arrowAngle:Number = Math.PI / 8;
		
		private var exAngle:Number = Math.PI+arrowAngle;
		
		/**
		 * 构造方法
		 * @param	x1
		 * @param	y1
		 * @param	strokeW
		 * @param	strokeColor
		 * @param	strokeTrans
		 */
		public function JArrowLine(x1:Number=0,y1:Number=0,strokeW:Number=1,strokeColor:Number=0x000000,strokeTrans:Number=1){
			super(x1,y1,strokeW,strokeColor,strokeTrans);
			this.useShadow=false;
		}
		
		/**
		 * 方法覆盖：绘制组件
		 */
		override public function drawWidget():void {
			this.graphics.clear();
			this.graphics.lineStyle(this.strokeW, this.strokeColor, this.strokeTrans);
			this.graphics.moveTo(0,0);
			
			var angle:Number=this.getSlopAngle();
			var distance:Number=this.getDistance();
			var p1:Point=Point.polar(distance,angle);
			var p2:Point = Point.polar(this.arrowLen,angle-exAngle);
			var p3:Point = Point.polar(this.arrowLen,angle+exAngle);
			p2.offset(p1.x, p1.y);
			p3.offset(p1.x, p1.y);
			
			//绘制头部箭头
			if(this.arrowType==1||this.arrowType==2){
				var temp2:Point = Point.polar(this.arrowLen,angle + arrowAngle);
				var temp3:Point = Point.polar(this.arrowLen,angle - arrowAngle);
				this.graphics.beginFill(this.strokeColor,this.strokeTrans);
				this.graphics.moveTo(0,0);
				this.graphics.lineTo(temp2.x,temp2.y);
				this.graphics.lineTo(temp3.x,temp3.y);
				this.graphics.lineTo(0,0);
				this.graphics.endFill();
			}
			
			this.graphics.lineTo(p1.x,p1.y);
			//绘制尾部箭头
			if(this.arrowType==0||this.arrowType==2){
				this.graphics.beginFill(this.strokeColor,this.strokeTrans);
				this.graphics.moveTo(p1.x,p1.y);
				this.graphics.lineTo(p2.x,p2.y);
				this.graphics.lineTo(p3.x,p3.y);
				this.graphics.lineTo(p1.x,p1.y);
				this.graphics.endFill();
			}
		}
	}
}