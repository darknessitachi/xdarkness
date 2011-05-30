package com.nano.math.geom{
	import flash.geom.Point;
	/**
	 * 曲线工具类
	 * @author zhangxf5@asiainfo-linkage.com
	 */
	public class JLineUtil {
		public function JLineUtil(){
			throw new Error('不能实例化曲线工具类。');
		}
		
		/**
		 * 根据距离和斜率计算x和y轴方向上的分量
		 * @return
		 */
		public static function getCoords(dist:Number,slope:Number):Point {
			var angle:Number = Math.atan(slope);
			var horizontal:Number = Math.abs(Math.cos(angle) * dist);
			var vertical:Number = Math.abs(Math.sin(angle) * dist);
			return new Point(horizontal,vertical);
		}
		
		/**
		 * 获取两点之间的距离
		 * @param	startX
		 * @param	startY
		 * @param	endX
		 * @param	endY
		 * @return
		 */
		public static function getDistance(startX:Number,startY:Number,endX:Number,endY:Number):Number{
			return Point.distance(new Point(startX,startY),new Point(endX,endY));
			//var distance:Number = Math.sqrt(Math.pow((endX-startX),2) + Math.pow((endY-startY),2));
			//return distance;
		}
		
		/**
		 * 根据圆心、半径、旋转角度计算圆上点的坐标
		 * @param	center		圆心位置
		 * @param	r			半径
		 * @param	angle		旋转角度
		 * @return
		 */
//		public static function getPoint(center:Point, r:Number, angle:Number):JPoint {
//			return new JPoint(r*Math.sin(angle)+center.x,r*Math.cos(angle)+center.y);
//		}
		
		/**
		 * 获取两个JPoint之间的距离
		 * @param	p1
		 * @param	p2
		 * @return
		 */
		public static function getPointDistance(p1:Point,p2:Point):Number {
			return getDistance(p1.x, p1.y, p2.x, p2.y);
		}
		
		/**
		 * 弧度转角度
		 * @param	radian
		 * @return
		 */
		public static function radian2Angle(radian:Number):Number {
			return radian * 180 / Math.PI;
		}
		
		/**
		 * 角度转弧度
		 * @param	angle
		 * @return
		 */
		public static function angle2Radian(angle:Number):Number {
			return angle * Math.PI / 180;
		}
		
		/**
		 * 根据两点坐标计算斜率
		 * @return
		 */
		public static function getSlope(x1:Number, y1:Number, x2:Number, y2:Number):Number {
			return (y2-y1) / (x2-x1);
		}
		
		/**
		 * 计算直线上到已知一点距离相等的两个点的坐标
		 * 已知：已知点坐标、斜率、距离
		 * @param	p				已知点的坐标
		 * @param	slope			斜率
		 * @param	dis				距离
		 * @return
		 */
		public static function getPointByDisAndSlope(p:Point,slope:Number,dis:Number):*{
			var res:Number = Math.sqrt((dis * dis) / (slope * slope + 1));
			var x1:Number = p.x + res;
			var x2:Number = p.x - res;
			var y1:Number = p.y + slope * (x1 - p.x);
			var y2:Number = p.y + slope * (x2 - p.x);
			return [ { x:x1, y:y1 }, { x:x2, y:y2 } ];
		}
		
		public static function getSlopeAngle(x1:Number,y1:Number,x2:Number,y2:Number):Number{
			return Math.atan2((y2-y1),(x2-x1));
		}
		
		/**
		 * 根据半径和旋转角度计算圆上点的坐标
		 * @param	r
		 * @param	angle
		 * @return
		 */
		private  function getPoint(r:Number,angle:Number):Point {
			return new Point(r*Math.sin(angle),r*Math.cos(angle));
		}
	}
}