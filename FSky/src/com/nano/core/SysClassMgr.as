package com.nano.core{
	
	import com.nano.flcomp.JHost;
	import com.nano.flcomp.JIcon;
	import com.nano.flcomp.chart.JGlassFish;
	import com.nano.flcomp.chart.JNumberShow;
	import com.nano.flcomp.chart.JPan;
	import com.nano.flcomp.chart.JResourceManager;
	import com.nano.flcomp.chart.JTemperature;
	import com.nano.widgets.JArrowLine;
	import com.nano.widgets.JCircle;
	import com.nano.widgets.JDashedCircle;
	import com.nano.widgets.JDashedLine;
	import com.nano.widgets.JDashedRect;
	import com.nano.widgets.JDashedShape;
	import com.nano.widgets.JFreePencil;
	import com.nano.widgets.JLine;
	import com.nano.widgets.JRect;
	import com.nano.widgets.JRoundRect;
	import com.nano.widgets.JText;
	import com.nano.widgets.link.JFluxLink;
	
	import flash.utils.getDefinitionByName;

	/**
	 * 系统Class类型管理器
	 * 所有需要动态根据类名获得类定义（反射）的类必须在这里定义一个变量
	 * 以强制编译器把类编译到swf中去，否则无法动态根据类名获得类定义
	 * @version	1.0
	 * @author 章小飞 zhangxf5@asiainfo-linkage.com
	 * @since  2010-04-21
	 */
	public class SysClassMgr {
		/**
		 * 重要：请勿删除这里的变量定义，否则无法通过反射的方式加载AS3的类
		 */ 
		/**
		 * 鼠标绘图相关组件
		 */ 
		public static var LINE:JLine;
		public static var ARROW_LINE:JArrowLine;
		public static var FLUX_LINK:JFluxLink;
		public static var DASHED_LINE:JDashedLine;
		public static var RECT:JRect;
		public static var DASHED_RECT:JDashedRect;
		public static var ROUND_RECT:JRoundRect;
		public static var CIRCLE:JCircle;
		public static var DASHED_CIRCLE:JDashedCircle;
		public static var SHAPE:JFreePencil;
		public static var DASHED_SHAPE:JDashedShape;
		public static var JTEXT:JText;
		
		/**
		 * 仪表盘
		 */
		public static var PAN:JPan;
		public static var TEMPERATURE:JTemperature;
		public static var RESOURCEMANAGER:JResourceManager;
		public static var GLASSFISH:JGlassFish;
		public static var NUMBERSHOW:JNumberShow;
		/**
		 * 云计费相关组件
		 */ 
		public static var JICON:JIcon;
		public static var JHOST:JHost;
		
		public function SysClassMgr(){
			throw new Error("不能实例化工具类SysClassMgr");
		}
		
		/**
		 * 根据类名获得类定义---反射
		 * 如果需要使用此方法来获得类的定义，目标类必须在SysSkinMgr中注册过
		 * @param	className	类的完全路径名
		 * @return
		 */
		public static function getClassFromName(className:String):Class{
			var result:Object=null;
			try{
				result=flash.utils.getDefinitionByName(className);
			}catch(e:Error){
				throw new Error("指定的类["+className+"]不存在。");
				return null;
			}
			return result as Class;
		}
		
		/**
		 * 根据类的实例获取完整类名
		 * @param	obj
		 * @return
		 */
		public static function getClassName(obj:*):String{
			var instanceInfo:XML=flash.utils.describeType(obj);
			var className:String=instanceInfo.@name;
			if(className&&className.indexOf("::")!=-1){
				className=className.replace("::",".");	
			}
			return className;
		}
		
		/**
		 * 根据类的实例获得类的定义
		 * @param	obj
		 * @return
		 */
		public static function getClasFromObj(obj:*):Class {
			return getClassFromName(getClassName(obj));
		}
	}
}