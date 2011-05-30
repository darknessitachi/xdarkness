package com.nano.widgets {
	import com.nano.math.geom.JLineUtil;
	
	import flash.display.Sprite;
	import flash.geom.Point;
	/**
	 * 虚线的圆或椭圆
	 * <pre>
	 * 		var circle:JDashedCircle=new JDashedCircle();
	 * 		this.addChild(circle);
	 *		circle.strokeStyle(3,0xff0000,0.8);
	 * 		circle.setBeginPoint(0,0);
	 * 		circle.setEndPoint(100,100);
	 * </pre>
	 * @version	1.1
	 * @author	章小飞 zhangxf5@asiainfo-linkage.com
	 * @since  	2009-09-22
	 */
	public class JDashedCircle extends JCircle {
		/**
		 * 长轴长度
		 */
		private var a:Number=0;
		/**
		 * 短轴长度
		 */
		private var b:Number=0;
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
		 */
		public function JDashedCircle(x:Number=0,y:Number=0) {
			super(x, y);
			this.addChild(this.fill);
			this.addChild(this.stroke);
		}
		
		/**
		 * 方法覆盖：绘制组件
		 */
		override public function drawWidget():void {
			this.a=this.endX/2;
			this.b=this.endY/2;
			//计算原点偏移量
			var tempX:Number =this.a;
			var tempY:Number =this.b;
			//计算椭圆上点的坐标---每隔0.5度取一个点
			var arrTemp:Array = new Array();
			var point:Point = null;
			for (var i:Number = 0; i < 360; i+=0.5 ) {
				point = getPoint(i,this.a,this.b);
				arrTemp.push(point);
			}
			//开始绘制
			point = arrTemp[0];
			this.stroke.graphics.clear();
			this.fill.graphics.clear();
			this.stroke.graphics.lineStyle(this.strokeW, this.strokeColor, this.strokeTrans);
			this.fill.graphics.beginFill(this.fillColor,this.fillTrans);
			this.stroke.graphics.moveTo(point.x + tempX, point.y + tempY);
			this.fill.graphics.moveTo(point.x+tempX, point.y+tempY);
			var len:Number = arrTemp.length;
			for (var j:Number = 1; j < len; j++ ) {
				point = arrTemp[j];			//当前点
				if (j % 5 == 0) {
					this.stroke.graphics.lineTo(point.x+tempX, point.y+tempY);
				}else {
					this.stroke.graphics.moveTo(point.x+tempX, point.y+tempY);
				}
				this.fill.graphics.lineTo(point.x+tempX, point.y+tempY);
			}
			point = arrTemp[0];
			this.stroke.graphics.lineTo(point.x + tempX, point.y + tempY);
			this.fill.graphics.lineTo(point.x + tempX, point.y + tempY);
			this.fill.graphics.endFill();
		}
		
		/**
		 * 根据角度、长轴、短轴获得椭圆上的坐标点
		 * @param	angle
		 * @param	a
		 * @param	b
		 * @return
		 */
		public static function getPoint(angle:Number,a:Number,b:Number):Point {
			var radian:Number = JLineUtil.angle2Radian(angle);
			var x:Number = a * Math.sin(radian);
			var y:Number = b * Math.cos(radian);
			return new Point(x, y);
		}
	}
}