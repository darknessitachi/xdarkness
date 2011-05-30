package com.nano.core {
	import flash.display.DisplayObject;
	import flash.display.DisplayObjectContainer;
	import flash.display.Sprite;

	/**
	 * 显示列表叠放次序（Z-index）管理器，
	 * 控制显示对象的叠放次序，
	 * 该类不能实例化
	 * @version	1.0
	 * @author 章小飞 zhangxf5@asiainfo-linkage.com
	 * @since  2010-04-21
	 */
	public class SysDepthManager {
		public function SysDepthManager(){
			throw new Error("不能实例化叠放深度管理工具类。");
		}
		
		/**
		 * 将obj3添加到obj1和obj2中叠放次序比较低的之下
		 * obj1、 obj2、obj3必须有相同的parent，否则叠放次序的判断会产生错误
		 * @param	obj1
		 * @param	obj2
		 * @param	obj3
		 */
		public static function addToLower(obj1:DisplayObject,obj2:DisplayObject,obj3:DisplayObject):void {
			var index1:Number = obj1.parent.getChildIndex(obj1);
			var index2:Number = obj2.parent.getChildIndex(obj2);
			var index3:int=Math.min(index1,index2)-1;
			if(index3<0) index3=0;
			obj1.parent.addChildAt(obj3,index3);
		}
		
		/**
		 * 将obj3添加到obj1和obj2中叠放次序比较高的之上
		 * obj1、 obj2、obj3必须有相同的parent，否则叠放次序的判断会产生错误
		 * @param	obj1
		 * @param	obj2
		 * @param	obj3
		 */
		public static function addToHigher(obj1:DisplayObject,obj2:DisplayObject,obj3:DisplayObject):void {
			var index1:Number = obj1.parent.getChildIndex(obj1);
			var index2:Number = obj2.parent.getChildIndex(obj2);
			obj1.parent.addChildAt(obj3,Math.max(index1,index2)+1);
		}
		
		/**
		 * 将obj2添加到obj1的上一层
		 * obj1和obj2必须有相同的父对象，否则叠放的次序将会出错
		 * @param	obj1
		 * @param	obj2
		 */
		public static function addToAbove(obj1:Sprite, obj2:Sprite):void {
			var index:Number = obj1.parent.getChildIndex(obj1);
			obj1.parent.addChildAt(obj2,index+1);
		}
		
		/**
		 * 将obj2添加到obj1的下一层
		 * obj1和obj2必须有相同的父对象，否则叠放的次序将会出错
		 * @param	obj1
		 * @param	obj2
		 */
		public static function addToBlow(obj1:Sprite, obj2:Sprite):void {
			var index:Number = obj1.parent.getChildIndex(obj1);
			obj1.parent.addChildAt(obj2,index==0?index:index-1);
		}
		
		/**
		 * 将obj2设置到obj1的上一层
		 * obj1和obj2必须有相同的父对象，否则叠放的次序将会出错
		 * @param	obj1
		 * @param	obj2
		 */
		public static function bringToAbove( obj1:Sprite, obj2:Sprite):void {
			var index:Number = obj1.parent.getChildIndex(obj1);
			obj1.parent.addChildAt(obj2,index+1);
		}
		
		/**
		 * 将obj2设置到obj1的下一层
		 * obj1和obj2必须有相同的父对象，否则叠放的次序将会出错
		 * @param	obj1
		 * @param	obj2
		 */
		public static function bringToBlow(obj1:Sprite, obj2:Sprite):void {
			var index:Number = obj1.parent.getChildIndex(obj1);
			obj1.parent.addChildAt(obj2,index==0?index:index-1);
		}

		/**
		 * 将组件设置到其父对象的最底层
		 * @param	obj
		 */
		public static function bringToBottom(obj:DisplayObject):void {
			var parent:DisplayObjectContainer = obj.parent;
			if (!parent){
				return;
			}
			parent.setChildIndex(obj, 0);
		}

		/**
		 * 将组件设置到其父对象的最顶层
		 * @param	obj
		 */
		public static function bringToTop(obj:DisplayObject):void {
			var parent:DisplayObjectContainer = obj.parent;
			if (!parent){
				return;
			}
			parent.setChildIndex(obj, parent.numChildren - 1);
		}

		/**
		 * 判断显示对象是否处于其父对象显示列表的最底层
		 * @param	obj
		 * @return
		 */
		public static function isBottom(obj:DisplayObject):Boolean {
			var parent:DisplayObjectContainer = obj.parent;
			if (parent.getChildIndex(obj) == 0){
				return true;
			}
			return false;
		}

		/**
		 * 判断显示对象是否处于其父对象显示列表的最顶层
		 * @param	obj
		 * @return
		 */
		public static function isTop(obj:DisplayObject):Boolean {
			var parent:DisplayObjectContainer = obj.parent;
			if (parent.getChildIndex(obj) == parent.numChildren - 1){
				return true;
			}
			return false;
		}

		/**
		 * 判断一个显示对象是否刚好位于另一个显示对象的下一层
		 * @param	obj
		 * @param	aboveObj
		 * @return
		 */
		public static function isJustBlow(obj:DisplayObject, aboveObj:DisplayObject):Boolean {
			var parent:DisplayObjectContainer = obj.parent;
			if (!parent){
				return false;
			}
			if (parent !== aboveObj.parent){
				return false;
			}

			return parent.getChildIndex(obj) == (parent.getChildIndex(aboveObj) - 1);
		}

		/**
		 * 判断一个显示对象是否刚好位于另一个显示对象的上一层
		 * @param	obj
		 * @param	blowObj
		 * @return
		 */
		public static function isJustAbove(obj:DisplayObject, blowObj:DisplayObject):Boolean {
			return isJustBlow(blowObj, obj);
		}
	}
}