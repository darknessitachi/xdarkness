package com.nano.flexcomp {
	import com.nano.core.SysDataMgr;
	import com.nano.plugins.LightMsg;
	import com.nano.serialization.json.JSON;
	
	import flash.display.DisplayObject;
	import flash.events.Event;
	import flash.events.FocusEvent;
	import flash.events.MouseEvent;
	
	import mx.collections.ArrayCollection;
	import mx.collections.IList;
	import mx.controls.TextInput;
	
	import spark.components.ComboBox;
	
	/**
	 * 提供value双向绑定，通过options或者model来配置数据源<br>
	 * 	<flexcomp:JComboBox id="show_level" width="200"<br>
	 *		value="@{selectedServiceType.show_level}"
	 *		valueField="param_id" labelField="param_value"
	 *		model="TC_M_SVC_TYPE.SHOW_LEVEL"/>
	 * <p>
	 * sqlType:通过自定义sql来获取数据源<br>
	 * eg:sqlType="SQL_SERVER_GROUP_WITH_ALL"
	 * 
	 * @includeExample mxml/examples/tJComboBox.mxml (需将143行的shortUrl改为url)
	 */
	public class JComboBox extends ComboBox {
		/**
		 * 根据options属性提供下拉列表的内容<br>
		 * eg.: options="1:普通用户,2:维护人员,3:高级管理员"
		 */ 
		public var options:String = null;
		/**
		 * 通过表tc_m_cmpparam获取数据源<br>
		 * eg.: model="TC_M_SVC_TYPE.SHOW_LEVEL"
		 */
		public var model:String = null;
		
		/**
		 * 是否显示全部选项
		 */
		public var isFull:Boolean = false;
		
		/**
		 * 从表tableName中取出记录生成valueColumn为value，labelColumn为label的下拉列表
		 */
		public var tableName:String = null;
		public var valueColumn:String = null;
		public var labelColumn:String = null;
		public var extendColumns:String = null;
		
		public var valueField:String = "data";
		/**
		 * 通过自定义sql来获取数据源<br>
		 * eg: sqlType="SQL_SERVER_GROUP_WITH_ALL"
		 * 暂时将sql语句存放到java类：com.linkage.cmp.dao.BaseDao.SQLCollection<br>
		 * sql语句的列名(别名)必须为value和label<br>
		 * 以后会将sql语句放到数据库里。
		 */
		public var sqlType:String = null;
		
		private var _superItem:DisplayObject = null;
		private var _superValue:String = null;
		private var _editable:Boolean = true;
		private var _value:String = "";
		private var _valueLater:String = null;
//		private var _dataProvider:IList = null;
		private var _filterField:String = null;
//		private var _param:Object = null;
		
		private static var cache:Object = {};
		
		private static const DATAPROVIDER_URL:String = "getComboBoxProvider";
		private static const CMPPARAM_VALUEFIELD:String = "param_id";
		private static const CMPPARAM_LABELFIELD:String = "param_value";
		private static const SQLTYPE_VALUEFIELD:String = "value";
		private static const SQLTYPE_LABELFIELD:String = "label";
		private static const ALLOFLIST_VALUE:String = "%";
		private static const ALLOFLIST_LABEL:String = "全部";

		public function JComboBox() {
			super();
		}
		
		/**
		 * 当组件editable为false时，增加光标进入和单击时展开下拉列表
		 */
		public function disEditOpenDrop(evt:Event):void {
			if (!_editable) {
				if (!isDropDownOpen) {
					openDropDown();
				}
			}
		}
		
		override protected function createChildren():void {
			super.createChildren();
			
			if (textInput != null)
				textInput.editable = _editable;
			
			if (dataProvider == null) {
				
				if (options != null) {
					var provider:IList = new ArrayCollection();
					var arr:Array = options.split(',');
					for each (var option:String in arr) {
						var dataLabelArr:Array = option.split(':');
						if (dataLabelArr.length == 2) {
							var o:Object = new Object();
							o.data = dataLabelArr[0];
							o.label = dataLabelArr[1];
							provider.addItem(o);
							
						} else {
							LightMsg.alert("警告", "JComboBox（" + id + "）配置不正确"); 
						}
					}
					this.dataProvider = provider;
				} else if (model != null) {
					requestForDropList(
						{
							MODEL: model,
							GET_MODE: 0
						}, 
						this.valueField, this.labelField, model,
						CMPPARAM_VALUEFIELD, CMPPARAM_LABELFIELD
					);
				} else if (tableName != null) {
					if (valueColumn == null || labelColumn == null)
						throw new Error("JComboBox（" + id + "）配置错误：" +
										"valueColumn和labelColumn必须配置!");
					if (extendColumns == null)
						extendColumns = "";
					
					requestForDropList(
						{
							TABLE_NAME: tableName,
							VALUE_COLUMN: valueColumn,
							LABEL_COLUMN: labelColumn,
							EXTEND_COLUMNS: extendColumns,
							GET_MODE: 1
						}, 
						valueColumn.toLowerCase(), labelColumn.toLowerCase(), 
						tableName
					);
				} else if(sqlType != null){
					requestForDropList(
						{
							SQLTYPE: sqlType,
							GET_MODE: 2
						},
						valueField.toLowerCase(), labelField.toLowerCase(), null,
						SQLTYPE_VALUEFIELD, SQLTYPE_LABELFIELD
					);
				}
			}
		}
		
		
		private function requestForDropList(requestParam:Object, 
					valueFld:String = null, labelFld:String = null, 
					cacheName:String = null, 
					defaultValueFld:String = null, defaultLabelFld:String = null):void {
			
			if (cacheName != null && hasCache(cacheName)) {
				var config:Object = cache[cacheName];
				this.dataProvider = config.dataProvider;
				this.valueField = config.valueField;
				this.labelField = config.labelField;
				
			} else {
				SysDataMgr.req({
					shortUrl: DATAPROVIDER_URL,
					param: requestParam,
					success: {
						scope:this, 
						fn: function(resStr:String = null):void {
							var datas:Object = JSON.decode(resStr);
							
							if (resStr.indexOf(valueFld) < 0 && 
								resStr.indexOf(defaultValueFld) > 0) {
								valueFld = defaultValueFld;
							}
							if (resStr.indexOf(labelFld) < 0 && 
								resStr.indexOf(defaultLabelFld) > 0) {
								labelFld = defaultLabelFld;
							}
							
							var provider:IList = null;
							if (datas is Array) {
								provider = new ArrayCollection(datas as Array);
							} else {
								if (provider == null)
									provider = new ArrayCollection();
								
								provider.addItem(datas);
							}
							if (isFull) {
								var fullObj:Object = {};
								fullObj[valueFld] = ALLOFLIST_VALUE;
								fullObj[labelFld] = ALLOFLIST_LABEL;
								provider.addItemAt(fullObj, 0);
							}
							this.valueField = valueFld;
							this.labelField = labelFld;
							this.dataProvider = provider;
							
							if (cacheName != null) {
								var config:Object = {};
								config.dataProvider = this.dataProvider;
								config.valueField = this.valueField;
								config.labelField = this.labelField;
								cache[cacheName] = config;
							}							
						}
					},
					failure: {
						scope:this, 
						fn:function(data:String = null):void {
							LightMsg.alert("警告", "JComboBox" + id + "获取数据模板时发生错误" + data);
						}
					}
				});
				
			}
		} 

		/**
		 * 判断是否有缓存
		 */
		private function hasCache(cacheName:String):Boolean {
			return (cache[cacheName] != null)
		}
		
		public function set value(v:String):void {
			if (_value == v)
				return;
			
			var index:int = -1;
			// dataProvider 还没有对其赋值,无法选择出对应的选项
			// 记录下当前的value,在后续set dataProvider的时候重新执行次set value操作
			if (dataProvider == null) {
				_valueLater = v;
				return;
			}
			
			for (var i:int = 0; i < dataProvider.length; i++) {
				var o:Object = dataProvider.getItemAt(i);
				if (o[valueField] == v) {
					index = i;
					break;
				}
			}
			selectedIndex = index;
			index >= 0 ? _value = v : _value = "";
		}
		
		//使用[Bindable]元数据定义Get方法事件绑定
		[Bindable(event="change")]
		public function get value():String {
			if (selectedIndex >= 0) {
				_value = selectedItem[valueField];
				return _value;
			}
			else
				return "";
		}
		
		public function set editable(editable:Boolean):void {
			if (_editable == editable)
				return;
			
			_editable = editable;
			if (textInput != null) {
				textInput.editable = _editable;
			}
			if (_editable) {
				this.removeEventListener(FocusEvent.FOCUS_IN, disEditOpenDrop);
				this.removeEventListener(MouseEvent.CLICK, disEditOpenDrop);
				this.removeEventListener(MouseEvent.DOUBLE_CLICK, disEditOpenDrop);
			} else {
				this.addEventListener(FocusEvent.FOCUS_IN, disEditOpenDrop);
				this.addEventListener(MouseEvent.CLICK, disEditOpenDrop);
				this.addEventListener(MouseEvent.DOUBLE_CLICK, disEditOpenDrop);
			}
		}
		
		public function get editable():Boolean {
			return _editable;
		}
		
		public function set superItem(item:DisplayObject):void {
			this._superItem = item;
			item.addEventListener(Event.CHANGE, superItem_changeHandler);
		}
		
		private function superItem_changeHandler(evt:Event):void {
			_superValue = null;
			if (_superItem is JComboBox) {
				_superValue = (_superItem as JComboBox).value;
			} else if (_superItem is TextInput) {
				_superValue = (_superItem as TextInput).text;
			}
			if (_superValue == null)
				return;
			
			if (_filterField) {
				var localValue:String = _value;
				_value = null;
				var dp:ArrayCollection = this.dataProvider as ArrayCollection;
				dp.filterFunction = filterFunc;
				dp.refresh();
				this.value = localValue;
//				_dataProvider = bkProvider;
			}
//			this.value = _value;
		}
		
		private function filterFunc(item:Object):Boolean {
			return _superValue == ALLOFLIST_VALUE || (
					item.hasOwnProperty(_filterField) && 
					item[_filterField].toString() == _superValue);
		}
		
		public function set filterField(v:String):void {
			_filterField = v;
		}
		override public function set dataProvider(value:IList):void {
			if (dataProvider === value)
				return;
			
			super.dataProvider = value;
			
			// 备份数据源
//			if (_superItem != null) {
//				_dataProvider = value;
//			}
			
			if (_valueLater) {
				this.value = _valueLater;
				_valueLater = null;
			}
		}
		
	}
}