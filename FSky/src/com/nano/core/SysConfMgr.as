package com.nano.core{
	import com.nano.data.JHashMap;
	import com.nano.serialization.json.JSON;
	/**
	 * 系统配置类，与后台交互的所有URL都存储在该类的urls属性中
	 * @version	1.3 2010-09-01
	 * @author	章小飞 zhangxf5@asiainfo-linkage.com
	 * @since  	V1.0
	 */
	public class SysConfMgr{
		/**
		 * 应用根路径
		 * 在调用initConf()方法加载url配置文件之前，必须把该属性设置为实际部署路径
		 * 例如，使用loaderInfo.url获取swf文件的实际加载url
		 */
		public static var baseUrl:String = "http://127.0.0.1:8080/NanoUI";
		/**
		 * url配置获取路径，在对应的后台后台工程中必须含有此处配置的真实路径
		 * 在应用初始化的第一个页面，SysConfMgr将使用此路径读取url配置文件。
		 */
		private static var confUrl:String="/loadSysConf.jsp";
		/**
		 * 请求路径配置哈希表
		 */
		private static var urls:JHashMap=new JHashMap();
		
		/**
		 * SysConfMgr是全局共用单例，不能new该类的对象
		 */ 
		public function SysConfMgr(){
			throw new Error("不能实例化系统配置类");
		}
		
		/**
		 * 从后台加载配置文件
		 */ 
		public static function initConf(conf:Object=null):void{
			urls.clear();
			
			var failureFn:Function=NanoSystem.emptyFn;
			if(conf&&conf.failure&&conf.failure is Function){
				failureFn=conf.failure;
			}
			
			//开始加载配置文件
			SysDataMgr.req({
				url:baseUrl+confUrl,
				success:function(result:String):void{
					var obj:Object=JSON.decode(result);
					/**
					 * 加载成功，解析配置文件，格式为JSON
					 * -->猥琐的JSON工具类，不能直接解析成布尔型
					 * JSON工具需要重构
					 */ 
					if(obj&&obj.success&&obj.success=="true"){
						try{
							var confData:Object=obj.data;
							for(var p:* in obj.data){
								var url:String=confData[p];
								urls.put(p,confData[p]);
							}
							if(conf.success&&conf.success is Function){
								conf.success();
							}
						}catch(e:Error){
							if(failureFn){failureFn("解析配置文件失败，请检查文件格式");}
						}
					}else if(obj&&obj.msg){
						if(failureFn){failureFn(obj.msg);}
					}else{
						if(failureFn){failureFn();}
					}
				},
				failure:function(msg:String):void{
					if(failureFn){failureFn(msg);}
				}
			});
		}
		
		/**
		 * 外部代码使用此函数根据名称获取请求的url地址
		 * 此方法将会自动拼接上baseUrl，返回一个完整的http路径
		 * 如果希望返回相对路径，请将此方法的第二个参数short指定为true
		 * @param	name
		 * @param	short
		 * @return
		 */
		public static function getUrl(name:String,short:Boolean=false):String{
			if(!name){
				return null;
			}
			if(short){
				return urls.get(name);
			}
			return SysConfMgr.baseUrl+urls.get(name);
		}
	}
}