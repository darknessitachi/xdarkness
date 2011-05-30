package com.nano.core{
	import com.nano.serialization.json.JSON;
	import com.nano.util.CollectionUtil;
	import com.nano.util.FuncUtil;
	
	import flash.display.DisplayObject;
	import flash.display.DisplayObjectContainer;
	import flash.display.Loader;
	import flash.display.Sprite;
	import flash.display.Stage;
	import flash.filters.BitmapFilter;
	import flash.net.LocalConnection;
	import flash.net.URLRequest;
	import flash.system.System;
	import flash.utils.Timer;
	
	import mx.core.FlexGlobals;
	import mx.core.UIComponent;
	import mx.managers.ISystemManager;
	import com.nano.util.GuidUtil;
	
	/**
	 * 系统工具类
	 * @version	1.0 2010-05-19
	 * @author	章小飞 zhangxf5@asiainfo-linkage.com
	 * @since  	V1.0
	 */
	public class NanoSystem {
		private static var loader:Loader;
		/**
		 * 方法别名，快捷方式
		 */
		public static var each:Function=CollectionUtil.each;
		
		/**
		 * 空函数
		 */
		public static var emptyFn:Function = function(...args):void { };
		/**
		 * 自动垃圾收集时间间隔
		 */
		public static var GC_INTERVAL:int=30000;
		
		public function NanoSystem(){
			throw new Error("尝试实例化工具类NanoSystem.");
		}
		
		/**
		 * 为对象添加一个滤镜
		 * @param	DisplayObject
		 * @param	filter
		 */
		public static function addFilter(obj:DisplayObject,filter:BitmapFilter):void {
			var newArr:Array = arrayCopy(obj.filters);
			newArr.push(filter);
			obj.filters = newArr;
		}
		
		/**
		 * 从对象上删除一个滤镜(未实现)
		 * @param	obj
		 * @param	filter
		 */
		public static function removeFilter(obj:DisplayObject,filter:BitmapFilter):void {
			var newArr:Array = arrayCopy(obj.filters);
			obj.filters = newArr;
		}
		
		/**
		 * 数组浅拷贝
		 * @param	oldArr
		 * @return
		 */
		public static function arrayCopy(oldArr:Array):Array {
			var newArr:Array = new Array();
			var len:Number = oldArr.length;
			if (oldArr != null && len == 0) {
				return newArr;
			}
			for (var i:Number = 0; i < len;i++ ) {
				newArr.push(oldArr.pop());
			}
			oldArr.length = 0;
			return newArr;
		}
		
		/**
		 * 为创建出来的对象产生全局唯一的id
		 * 这里使用时间的毫秒数作为id种子，保证全局唯一性
		 */ 
		public static function id():String{
			return "ID@"+(GuidUtil.create());
		}
		
		/**
		 * 判断一个对象是否是另一个对象的祖先
		 * 如果是另一个对象的祖先或者是该对象自己，返回true
		 * @param	ancestor
		 * @param	child
		 * @return
		 */
		public static function isAncestor(ancestor:DisplayObjectContainer,child:DisplayObject):Boolean {
			if (!ancestor||!child) {
				return  false;
			}
			if (ancestor.contains(child)) {
				return true;
			}
			return false;
		}
		
		/**
		 * 属性覆盖
		 * @param	des		目标对象
		 * @param	src		源对象
		 * @return
		 */
		public static function apply(des:*=null,src:*=null):*{
			if(!des){
				des={};
			}
			if(!src){
				return des;
			}
			for(var p:* in src){
				des[p]=src[p]
			}
			return des;
		}
		
		/**
		 * 有条件属性覆盖
		 * 如果obj1存在obj2中的属性，则不覆盖
		 * @param	obj1		目标对象
		 * @param	obj2		源对象
		 * @return
		 */
		public static function applyIf(obj1:*=null,obj2:*=null):*{
			if(!obj1){
				obj1={};
			}
			if(!obj2){
				return obj1;
			}
			for (var p:* in obj2) {
				if (!obj1[p]) {
					obj1[p]=obj2[p]
				}
			}
			return obj1;
		}
		
		public static function simpleCpy(obj:Object):*{
			if(!obj){
				return null;
			}
			var result:Object={};
			apply(result,obj);
			return result;
		}
		
		public static function deepCpy(obj:Object):*{
			return JSON.decode(JSON.encode(obj));
		}
		
		/**
		 * 强制系统进行垃圾收集
		 */
		public static function gc():void{
			trace("强制垃圾收集...");
			var lastMem:Number=System.totalMemory;
			trace("收集前内存使用量>"+lastMem+"字节");
			var conn1:LocalConnection=null;
			var conn2:LocalConnection=null;
			try{
				conn1=new LocalConnection();
				conn2=new LocalConnection();
				conn1.connect(":com.linkage.nanosys");
				conn2.connect(":com.linkage.nanosys");
			}catch(error : Error){
				trace(error);
			}finally{
				conn1=null;
				conn2=null;
				System.gc();
				System.gc();
				var currMem:Number=System.totalMemory;
				trace("收集后内存使用量>"+currMem+"字节");
				trace("回收>"+(lastMem-currMem)+"字节");
				//trace("此强制回收机制不可靠，容易造成player崩溃，已废弃...");
			}
		}
		
		/**
		 * 加载配置文件
		 */
		public static function loadConf():void{
			if(!loader){
				loader=new Loader();
			}
			try{
				loader.load(new URLRequest("conf/modules.xml"));
			}catch(error:Error){
				trace("加载xml文件失败");
			}
		}
		
		/**
		 * 对请求进行HTTP编码
		 */
		public static function httpEncoding(param:String):String{
		    return encodeURIComponent(param);
		}
		
		/**
		 * HTTP特殊字符解码
		 */ 
		public static function httpDecodding(param:String):String{
			return decodeURIComponent(param);
		}
		
		/**
		 * 判定一个对象是否为空
		 */
		public static function isNull(obj:*):Boolean{
			if(obj==null||obj==undefined||obj=="null"||obj=="undefined"||obj==""){
				return true;
			}
			return false;
		}
		
		public static function println(obj:*):void{
			trace(obj+"\n");
		}
		
		/**
		 * 将一个Flash导出组件添加到Flex标签组件中
		 * @param	child   从Flash中导出的MovieClip或者Sprite
		 * @param	parent
		 * @return
		 */
		public static function addToFlex(child:DisplayObject,parent:UIComponent,index:int=-1):UIComponent{
			var uc:UIComponent=new UIComponent();
			uc.x=0;
			uc.y=0;
			uc.width=child.width;
			uc.height=child.height;
			uc.addChild(child);
			if(index>=0){
				parent.addChildAt(uc,index);
			}else{
				parent.addChild(uc);
			}
			return uc;
		}
		
		/**
		 * 获取Stage对象
		 */
		public static function getStage():Stage{
			var sm:ISystemManager = ISystemManager(FlexGlobals.topLevelApplication.systemManager);
			var mp:Object = sm.getImplementation("mx.managers.IMarshallPlanSystemManager");
			var parent:Sprite=null;
			if (mp && mp.useSWFBridge()){
				parent = Sprite(sm.getSandboxRoot());
			}else{
				parent = Sprite(FlexGlobals.topLevelApplication);
			}
			return parent.stage;
		}
		
		/**
		 * 启动自动垃圾收集线程
		 * @return
		 */
		public static function startAutoGC():Timer {
			var timer:Timer =FuncUtil.timerExcutor(function():void{gc();},GC_INTERVAL);
			return timer;
		}
	}
}