package com.nano.serialization.xml {
	import com.nano.core.JComponent;
	import com.nano.flcomp.JFlComponent;
	import com.nano.core.SysClassMgr;
	import com.nano.data.JHashMap;
	import com.nano.serialization.json.JSON;
	import com.nano.widgets.link.JFluxLink;
	import com.nano.widgets.link.JLink;
	
	import flash.utils.describeType;
	
	import mx.collections.ArrayCollection;
	import mx.controls.Alert;

	/**
	 * 对象的xml解析和生成器，此解析器专门用来解析和生成JWidget和JModule型的数据。
	 * 如果需要其它特定类型的解析器，请覆盖相关的方法。
	 * 该类为静态工具类，尝试实例化该类将获得一个运行时异常。
	 * @version	1.3 2010-05-18
	 * @author	章小飞 zhangxf5@asiainfo-linkage.com
	 * @since  	V1.0
	 */
	public class CompXmlParser{
		public function CompXmlParser() {
			throw new Error("尝试实例化XML解析工具类ModuleXmlParser。");
		}
		
		/**
		 * 将一个组件格式化成xml
		 * 只将基本类型的属性格式化成xml
		 * 数组、集合等复杂数据类型忽略
		 * @param	comp
		 * @return
		 */
		public static function xmlEncode(comp:JComponent):XML {
			//[重要]：在开始解析之前设置组件的叠放次序
			if(comp.parent){
				comp._zIndex=comp.parent.getChildIndex(comp);
			}
			
			//xml描述预处理
			var instanceInfo:XML=flash.utils.describeType(comp);
			delete instanceInfo.metadata;
			delete instanceInfo.extendsClass;
			delete instanceInfo.implementsInterface;
			delete instanceInfo.constructor;
			delete instanceInfo.method;
			
			//开始格式化对象的xml描述
			var temp:XMLList=parseObj(instanceInfo,comp);
			var result:XML=new XML("<instance type='"+instanceInfo.@name+"' base='"+instanceInfo.@base+"'></instance>");
			result.appendChild(temp);
			return result;
		}
		
		/**
		 * 从xml生成组件
		 * @return
		 */
		public static function xmlDecode(xmlList:XMLList):ArrayCollection {
			var result:ArrayCollection = new ArrayCollection();
			try {
				//解析XML数据
				var tempObj:*=null;
				for each(var v:XML in xmlList) {
					tempObj={};
					
					var name:String=null;
					var value:*=null;
					var type:*=null;
					for each(var xml1:XML in v.*){
						name=String(xml1.name);
						value=String(xml1.value);
						type=String(xml1.type);
						switch(type){
							case 'Number':
								value=Number(value);
								break;
							case 'int':
								value=int(value);
								break;
							case 'Boolean':
								value=(value=='true'?true:false);
								break;
							case 'String':
								value=(value==''||value=='null'||value=='undefined')?null:value;
								break;
							case 'Array':
								if(name=='linePath'||name=="colors"||name=="alphas"){//对数组特殊处理
									value=JSON.decode(value);
								}
								break;
							case '*':
								value=JSON.decode(value);
								break;
							case 'Object':
								value=JSON.decode(value);
								break;
							default:
								break;
						}
						tempObj[name]=value;
					}
					
					/**
					 * 创建实例，对关键属性赋值
					 * 注意：由于保存的XML数据中任然存在大量不需要的垃圾数据，这里使用逐个属性赋值的方式进行类型反射
					 * 避免创建出来的对象出现异常状态。
					 */ 
					var className:String=v.@type;
					var ClassType:Class =SysClassMgr.getClassFromName(className);					
					var instance:Object = new ClassType();
					instance.id=tempObj.id;
					instance.x=tempObj.x;
					instance.y=tempObj.y;
					instance._zIndex=tempObj._zIndex;
					instance.isDragAble=tempObj.isDragAble;
					instance.isSelected=tempObj.isSelected;
					instance.cdata=tempObj.cdata;
					if(instance is JFlComponent){
						if(tempObj.moduleName){
							(instance as JFlComponent).setModuleName(tempObj.moduleName);
						}else{
							(instance as JFlComponent).setModuleName(tempObj.id);
						}
					}else if(instance is JFluxLink){
						var fLink:JFluxLink=instance as JFluxLink;
						fLink.setFromId(tempObj.fromId);
						fLink.setToId(tempObj.toId);
						fLink.setStrokeW(tempObj.strokeW as Number);
						fLink.setStrokeColor(tempObj.strokeColor as Number);
						fLink.setStrokeTrans(tempObj.strokeTrans as Number);
						fLink.setFillColor(tempObj.fillColor as Number);
						fLink.setFillTrans(tempObj.fillTrans as Number);
						fLink.setFluxColor(tempObj.colors);
						fLink.setBorderColor(tempObj.borderColor);
						fLink.setBorderW(tempObj.borderW);
						fLink.setBorderTrans(tempObj.borderTrans);
					}else if(instance is JLink){
						var tempLink:JLink=instance as JLink;
						tempLink.setFromId(tempObj.fromId);
						tempLink.setToId(tempObj.toId);
						tempLink.setStrokeW(tempObj.strokeW as Number);
						tempLink.setStrokeColor(tempObj.strokeColor as Number);
						tempLink.setStrokeTrans(tempObj.strokeTrans as Number);
						tempLink.setFillColor(tempObj.fillColor as Number);
						tempLink.setFillTrans(tempObj.fillTrans as Number);
					}
					result.addItem(instance);
				}
			}catch (err:Error) {
				trace(err);
				Alert.show("无法识别的xml格式","xml解析错误");
			}
			return result;
		}
		
		/**
		 * 将XML解析成显示对象
		 * 此方法保证了连接对象在其它对象之后被解析
		 * 返回的HashMap包含2种类型的对象，modules为非连接线对象，links为连接线对象
		 * 由于连线对象必须提供两端的端点，因此，在调用JPen.addToView()向JPage中添加对象时，必须首先添加非连接线对象
		 */
		public static function decodeJcompFromXml(str:String):JHashMap{
			var result:JHashMap=new JHashMap();
			var xmlList:XMLList=new XMLList(str);
			var linksXmlList:XMLList=new XMLList();
			var compXmlList:XMLList=new XMLList();
			for each(var v:XML in xmlList){
				var t:String=String(v.@type);
				if(t.indexOf("Link")!=-1){
					linksXmlList+=v;
				}else{
					compXmlList+=v;
				}
			}
			
			var modules:ArrayCollection=CompXmlParser.xmlDecode(compXmlList);
			var links:ArrayCollection=CompXmlParser.xmlDecode(linksXmlList);
			result.put("modules",modules);
			result.put("links",links);
			return result;
		}
		
		/**
		 * 格式化对象的xml描述，去掉冗余的描述信息
		 * @param	instanceInfo		对象的xml描述
		 * @param	obj					对象
		 * @param	result				结果
		 * @return
		 */
		private static function parseObj(instanceInfo:XML,obj:*,result:XMLList=null):XMLList{
			if(!result){
				result=new XMLList;
			}
			if(obj&&obj!=null&&obj!='null'&&obj!='undefined'){
				var name:String="";
				var type:String="";
				var v:XML=null;
				for each(v in instanceInfo..variable){
					delete v.metadata;//删除元数据---Flex4中新xml描述含有此项目
					name=v.@name;
					type=v.@type;
					if(name&&name!=null&&name!='null'&&name!='undefined'){
						if(type=="Number"||type=="int"||type=="String"||type=="Boolean"){
							v['name']=name;
							v['value']=obj[name];
							v['type']=type;
							delete v.@name;
							delete v.@type;
							result+=v;
						}else if(type=="*"||type=="Object"){
							v['name']=name;
							v['value']=JSON.encode(obj[name]);
							v['type']=type;
							delete v.@name;
							delete v.@type;
							result+=v;
						}
						
						if(type=="Array"&&(name=="linePath"||name=="colors"||name=="alphas")){//对数组特殊处理
							v['name']=name;
							v['value']=JSON.encode(obj[name]);
							v['type']=type;
							delete v.@name;
							delete v.@type;
							result+=v;
						}
					}
				}
				
				for each(v in instanceInfo..accessor){
					name=v.@name;
					type=v.@type;
					if(name&&name!=null&&name!='null'&&name!='undefined'){
						if(v.@access&&(v.@access!='readwrite')){
							continue;
						}
						if(type=="Number"||type=="int"||type=="String"||type=="Boolean"){
							v['name']=name;
							v['value']=obj[name];
							v['type']=type;
							delete v.@name;
							delete v.@type;
							result+=v;
						}
					}
				}
			}
			return result;
		}
		
		public static function clone(comp:JComponent):JComponent{
			if(!comp){
				return null;
			}
			var xml:XML=xmlEncode(comp);
			var xmlList:XMLList=new XMLList(xml);
			var result:JComponent=xmlDecode(xmlList).getItemAt(0) as JComponent;
			return result;
		}
	}
}