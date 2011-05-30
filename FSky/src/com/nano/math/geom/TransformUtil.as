package com.nano.math.geom  
{
	import com.linkage.capacity.util.Axt;
	
	import flash.display.DisplayObject;
	import flash.geom.Matrix;
	import flash.geom.Point;
	/**
	 * 仿射渐变工具类
	 * 为对象的移动、旋转、缩放提供一系列工具方法
	 * 此类是一个工具类，不要实例化此类
	 * @author 章小飞 zhangxf5@asiainfo-linkage.com
	 */
	public class TransformUtil
	{
		
		public function TransformUtil() 
		{
			
		}
		
		/**
		 * 将对象移动到指定的点
		 * @param	obj
		 * @param	dest
		 */
		public static function moveTo(obj:DisplayObject,dest:Point):void {
			var matrix:Matrix = obj.transform.matrix;
			var tx:Number = dest.x - obj.x;
			var ty:Number = dest.y - obj.y;
			matrix.translate(tx, ty);
			obj.transform.matrix = matrix;
		}
		
		/**
		 * 将对象旋转指定的角度
		 * @param	obj
		 * @param	angle	角度
		 */
		public static function rotate(obj:DisplayObject,angle:Number):void {
			var rad:Number=Axt.angle2Radian(angle);
			var matrix:Matrix=obj.transform.matrix;
			matrix.rotate(rad);
			obj.transform.matrix=matrix;
		}
		
		/**
		 * 缩放对象
		 * @param	obj
		 * @param	scaleX	x轴缩放倍数
		 * @param	scaleY	y轴缩放倍数
		 */
		public static function scale(obj:DisplayObject,scaleX:Number,scaleY:Number):void {
			var matrix:Matrix=obj.transform.matrix;
			matrix.scale(scaleX,scaleY);
			obj.transform.matrix=matrix;
		}
		
	}
}