package com.nano.math.geom {
	import flash.display.DisplayObject;
	import flash.geom.Point;
	import flash.geom.Rectangle;

	/**
	 * 矩形运算工具
	 */
	public class JRectUtil {
		public function JRectUtil(){
		    throw new Error("不能实例化矩形运算工具类。");
		}
		
		/**
		 * 计算des在src中的居中位置
		 * @param	src
		 * @param	des
		 * @return
		 */
		public static function getCenterPosition(src:*,des:*):Point{
			if(src is Rectangle&&des is Rectangle){
				var x1:Number=(src as Rectangle).width/2-(des as Rectangle).width/2;
				var y1:Number = (src as Rectangle).height/2-(des as Rectangle).height/2;
				return new Point(x1,y1);
			}else if(src is DisplayObject&&des is DisplayObject){
				var x2:Number = (src.width - des.width) / 2;
				var y2:Number = (src.height - des.height) / 2;
				return new Point(x2,y2);
			}
			return new Point();
		}
		
		/**
		 * 获得显示对象的中心点位置
		 * @param	obj
		 * @return
		 */
		public static function getCenterPoint(obj:DisplayObject):*{
			if(!obj){
				return null;
			}
			return {x:obj.x+obj.width/2,y:obj.y+obj.height/2};
		}
		
		/**
		 * 获得2个显示对象中比较大的高度和宽度
		 * @param	obj1
		 * @param	obj2
		 * @return
		 */
		public static function getMaxWH(obj1:DisplayObject,obj2:DisplayObject):*{
			return {w:obj1.width>obj2.width?obj1.width:obj2.width,h:obj1.height>obj2.height?obj1.height:obj2.height}
		}
		
		/**
		 * 获得一个显示对象（假定为矩形区域），边框上8个方向的控制点以及中心点坐标，数据格式为：
		 * {lt:{x,y},t:{x,y},rt:{x,y},r:{x,y},rb:{x,y},b:{x,y},lb:{x,y},l:{x,y},c:{}}
		 * @param	obj
		 * @return
		 */
		public static function getBoundPoint(obj:DisplayObject):*{
			var _w:Number=obj.width;
			var _h:Number=obj.height;
			var _x:Number=obj.x;
			var _y:Number=obj.y;
			return {lt:{x:_x,y:_y},
					t:{x:_x+_w/2,y:_y},
					rt:{x:_x+_w,y:_y},
					r:{x:_x+_w,y:_y+_h/2},
					rb:{x:_x+_w,y:_y+_h},
					b:{x:_x+_w/2,y:_y+_h},
					lb:{x:_x,y:_y+_h},
					l:{x:_x,y:_y+_h/2},
					c:{x:_x+_h/2,y:_y+_h/2}
				};
		}
	}
}