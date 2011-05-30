package com.nano.math.geom {
	import flash.display.Graphics;
	import flash.geom.Point;
	/**
	 * 2D向量，
	 * 定义2D空间的向量和向量之间的运算，
	 * 该类使用笛卡尔坐标法表示2D向量
	 * @version	1.3 2010-05-18
	 * @author	章小飞 zhangxf5@asiainfo-linkage.com
	 * @since  	V1.0
	 */
	public class Vector2D {
		/**
		 * x轴分量
		 */
		public var x:Number=0;
		/**
		 * y轴分量
		 */
		public var y:Number =0;
		
		/**
		 * 构造函数
		 * @param	config
		 */
		public function Vector2D(config:*= null) {
			if (config) {
				this.x=config.x;
				this.y=config.y;
			}
		}
		
		/**
		 * 绘制出该向量
		 * @param	gra
		 * @param	color
		 */
		//尾部箭头
		private var arrowAngle:Number = Math.PI / 8;
		private var arrowLen:Number = 10;
		private var exAngle:Number = Math.PI + arrowAngle;
		public function draw(gra:Graphics,color:Number=0x000000):void {
			gra.lineStyle(1, color, 1);
			//尾部箭头
			var p1:Point = Point.polar(this.getLen(), this.getAngle());
			var p2:Point = Point.polar(this.arrowLen,this.getAngle()-exAngle);
			var p3:Point = Point.polar(this.arrowLen,this.getAngle()+exAngle);
			p2.offset(p1.x, p1.y);
			p3.offset(p1.x, p1.y);
			gra.moveTo(0, 0);
			gra.lineTo(p1.x,p1.y);
			gra.beginFill(color,1);
			gra.moveTo(p1.x,p1.y);
			gra.lineTo(p2.x,p2.y);
			gra.lineTo(p3.x,p3.y);
			gra.lineTo(p1.x,p1.y);
			gra.endFill();
		}
		
		/**
		 * 克隆当前向量
		 * @return
		 */
		public function clone():Vector2D {
			var result:Vector2D = new Vector2D();
			result.x = this.x;
			result.y = this.y;
			return result;
		}
		
		/**
		 * 向量相加
		 * 返回新的向量，而不改变2个相加的因子
		 * @param	v
		 * @return
		 */
		public function plus(v:Vector2D):Vector2D{
			var result:Vector2D = this.clone();
			result.x=this.x+v.x;
			result.y=this.y+v.y;
			return result;
		}
		
		/**
		 * 求反向向量
		 */
		public function reverse():void{
//			var _angle:Number=this.getAngle()+Math.PI;
//			var _len:Number=this.getLen();
//			this.x=_len*Math.cos(_angle);
//			this.y=_len*Math.sin(_angle);
			this.x=-this.x;
			this.y=-this.y;
		}
		
		/**
		 * 向量相减
		 * 返回新的向量，而不改变2个相加的因子
		 * @param	v
		 * @return
		 */
		public function minus(v:Vector2D):Vector2D {
			return new Vector2D({x:this.x-v.x,y:this.y-v.y});
		}
		
		/**
		 * 向量除以一个值
		 * @param	value
		 */
		public function divide(value:Number):Vector2D {
			return new Vector2D({x:this.x/value,y:this.y/value});
		}
		
		/**
		 * 向量乘以一个值
		 * @param	value
		 * @return
		 */
		public function multiply(value:Number):Vector2D {
			return new Vector2D({x:this.x*value,y:this.y*value});
		}
		
		/**
		 * 截断
		 * @param	max
		 * @return
		 */
		public function truncate(max:Number):Vector2D {
			if(this.getLen()>max){
				var angle:Number=this.getAngle();
				this.x=max*Math.cos(angle);
				this.y=max*Math.sin(angle);
			}
			return this;
		}
		
		/**
		 * 归一化
		 * @return
		 */
		public function normalize():void {
			if (this.getLen()==0) {
				this.x = 1;
				this.y = 0;
				return;
			}
			this.x /= this.getLen();
			this.y /= this.getLen();
		}
		
		/**
		 * 求2个向量点之间的距离
		 * @param	v
		 * @return
		 */
		public function dist(v:Vector2D):Number {
			var dx:Number = this.x - v.x;
			var dy:Number = this.y - v.y;
			return Math.sqrt(dx*dx+dy*dy);
		}
		
		/**
		 * 将当前向量设置为0向量
		 * @return
		 */
		public function zero():Vector2D {
			this.x = 0;
			this.y = 0;
			return this;
		}
		
		/**
		 * 判断当前向量是否为0向量
		 * @return
		 */
		public function isZero():Boolean {
			return (this.x==0)&&(this.y==0);
		}
		
		/**
		 * 获得向量长度
		 * @return
		 */
		public function getLen():Number {
			return Math.sqrt(this.x*this.x+this.y*this.y);
		}
		
		/**
		 * 获得向量角度
		 * @return
		 */
		public function getAngle():Number {
			var angle:Number=Math.atan2(this.y,this.x);
			return angle;
		}
	}
}