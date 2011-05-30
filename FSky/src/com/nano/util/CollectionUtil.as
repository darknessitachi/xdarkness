package com.nano.util {
	import com.nano.data.JHashMap;
	
	import mx.collections.ArrayCollection;

	/**
	 * 集合数据工具类，该类提供快速迭代集合的工具方法，该类不可实例化
	 * @author	章小飞 zhangxf5@asiainfo-linkage.com
	 * @since  	2010-05-18
	 */
	public class CollectionUtil {
		public function CollectionUtil() {
			throw new Error("尝试实例化集合工具类。");
		}
		
		/**
		 * 在集合中的每个对象上调用指定的函数
		 * 参数为集合中的对象
		 * @param	collection
		 * @param	func
		 * @param	scope
		 * @param	escape	是否快速退出迭代过程，如果此参数为true，当func返回true时退出迭代过程
		 */
		public static function each(collection:*,func:Function,scope:Object=null,escape:Boolean=false):void {
			try{
				if (collection is Array||collection is ArrayCollection) {
					for each(var item:* in collection) {
						var result:*=func.apply(scope||func,[item]);
						if (escape&&result) {
							return;
						}
					}
				}else if (collection is JHashMap) {
					CollectionUtil.each(collection.values(),func,scope);
				}
			}catch(e:Error){
				trace(e.toString());
			}
		}
		
		/**
		 * 清除数组中的空对象
		 * 注意：此方法会返回一新数组。
		 * @param	obj
		 */
		public static function rmNull(obj:Array):Array{
			var result:Array = [];
			if (obj is Array) {
				for (var i:int = 0; i < obj.length;i++ ) {
					if (obj[i]) {
						result.push(obj[i]);
					}
				}
			}
			return result;
		}
		
		/**
		 * 删除数组中指定的项目
		 * 如果指定的项目不在数组中，则什么都不做
		 * 注意：此方法会返回一新数组。
		 * @param	arr
		 * @param	obj
		 * @return
		 */
		public static function rmItem(arr:Array,obj:*):void{
			var index:int=getIndex(arr,obj);
			if(index>=0){
				arr.splice(index,1);
			}
		}
		
		/**
		 * 根据指定的函数删除数组中的元素
		 * @param	arr
		 * @param	fn
		 */
		public static function rmBy(arr:Array,fn:Function):void{
			var items:Array=filterBy(arr,fn);
			each(items,function(item:*):void{
				rmItem(arr,item);
			});
		}
		
		/**
		 * 根据所给的正则表达式，从数组中剔除对象
		 * @param	arr
		 * @param	reg
		 * @return
		 */
		public static function rmByReg(arr:Array,reg:RegExp):Array{
			var result:Array = [];
			if (arr is Array) {
				for (var i:int = 0; i < arr.length;i++ ) {
					if (!reg.test(arr[i])) {
						result.push(arr[i]);
					}
				}
			}
			return result;
		}
		
		/**
		 * 根据类型过滤对象
		 */ 
		public static function filterByClass(arr:Array,classType:Class):Array{
			if(!arr||!classType){
				return [];
			}
			var result:Array=arr.filter(function(element:*, index:int, arr:Array):Boolean {
				return (element is classType);
			});
			return result;
		}
		
		/**
		 * 根据指定的函数过滤元素
		 * fn签名模板:fn(item:*):Boolean
		 *     fn必须能接受一个item参数，在对集合进行迭代的过程中会把当前访问的项目传递给fn函数。
		 * 	   fn必须返回一个布尔型数值，表示当前元素是否符合过滤要求。
		 *     如果fn返回true，则item被包含在结果集中，否则将被过滤掉。
		 * @param	arr
		 * @param	fn
		 * @return
		 */
		public static function filterBy(arr:Object,fn:Function,single:Boolean=false):*{
			var result:Array=null;
			if(arr is Array){
				result=doFilter(arr as Array,fn);
			}else if(arr is ArrayCollection){
				result=doFilter((arr as ArrayCollection).source,fn);
			}else if(arr is JHashMap){
				result=doFilter((arr as JHashMap).values(),fn);
			}
			if(single){
				return result[0];
			}else{
				return result;
			}
		}
		
		/**
		 * 私有工具方法
		 * @param	arr
		 * @param	fn
		 * @return
		 */
		private static function doFilter(arr:Array,fn:Function):Array{
			var result:Array=[];
			each(arr,function(item:*):void{
				if(fn.call(fn,item)){
					result.push(item);
				}
			});
			return result;
		}
		
		/**
		 * 差集
		 * 数组相减，如果arr1中存在与arr2相同的元素，则从arr1中删掉该元素
		 * 返回删除相同元素后的arr1
		 */ 
		public static function minusCollection(arr1:Array,arr2:Array):Array{
			if(!arr1||!arr2){
				return [];
			}
			arr2.forEach(function(element:*, index:int, arr:Array):void {
				var _index:int=arr1.indexOf(element);
				if(_index!=-1){
					arr1.splice(_index,1);
				}
        	});
        	return arr1;
		}
		
		/**
		 * 交集：同时在数组1和数组2中出现的元素
		 */ 
		public static function crossCollection(arr1:Array,arr2:Array):Array{
			if(!arr1||!arr2){
				return [];
			}
			var result:Array=[];
			arr2.forEach(function(element:*, index:int, arr:Array):void {
				var _index:int=arr1.indexOf(element);
				if(_index!=-1){
					result.push(element);
				}
        	});
        	return result;
		}
		
		/**
		 * 并集
		 */ 
		public static function uniteCollection(arr1:Array,arr2:Array):Array{
			if(!arr1||!arr2){
				return [];
			}
			CollectionUtil.each(arr2,function(item:*):void{
				var _index:int=arr1.indexOf(item);
				if(_index==-1){
					arr1.push(item);
				}
			});
			return arr1;
		}
		
		/**
		 * 并集，别名
		 */
		public static var addAll:Function=uniteCollection;
		
		/**
		 * 获得指定对象在数组中的索引
		 * @param	arr
		 * @param	obj
		 * @return
		 */
		public static function getIndex(arr:Array,obj:Object):int{
			if(!arr||arr.length==0){
				return -1;
			}
			for(var i:int=0;i<arr.length;i++){
				if(arr[i]==obj){
					return i;
				}
			}
			return -1;
		}
		
		/**
		 * 使用指定的对象填充数组
		 * @param	arr
		 * @param	obj
		 */
		public static function fillArray(arr:Array,obj:Object):void{
			for (var i:int = 0; i < arr.length;i++ ) {
				arr[i] = obj;
			}
		}
		
		/**
		 * 数组浅拷贝，可以是Array/ArrayColleciton/JHashMap，如果是HashMap，项目必须有id属性
		 * @param	dest
		 * @param	src
		 */
		public static function arrCpy(dest:*,src:*):void{
			if(!dest||!src){
				throw new Error("数组不能为空");
			}
			CollectionUtil.each(src,function(item:*):void{
				if(dest is Array){
					dest.push(item);
				}else if(dest is ArrayCollection){
					dest.addItem(item);
				}else if(dest is JHashMap){
					dest.put(item.id||"",item);
				}
			})
		}
		
		/**
		 * 循环切换
		 * @param	size
		 * @param	currentIndex
		 * @param	plus
		 * @return
		 */
		public static function toogle(size:int,currentIndex:int,plus:int):int{
			if((currentIndex+plus)>(size-1)){
				return (currentIndex+plus)%size;
			}
			if((currentIndex+plus)<0){
				var temp:int=(currentIndex+plus)%size;
				return currentIndex+(size-1)+temp;
			}
			return currentIndex+plus;
		}
	}
}