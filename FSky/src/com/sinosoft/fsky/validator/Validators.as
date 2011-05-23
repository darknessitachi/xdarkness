package com.sinosoft.fsky.validator
{
	import flash.events.Event;
	import flash.events.EventDispatcher;
	import flash.events.IEventDispatcher;
	
	import mx.core.IMXMLObject;
	import mx.events.FlexEvent;
	import mx.events.ValidationResultEvent;
	import mx.validators.ValidationResult;
	import mx.validators.Validator;
	
	
	/**
	 * @author Darkness
	 * @description 验证器框架信息对象
	 * 				用于对多个验证器进行综合统一验证，并返回验证结果
	 * @create_date 2011-4-27
	 * @modify_date 2011-4-27
	 */
	[DefaultProperty("validatorArray")]
	public class Validators extends EventDispatcher implements IMXMLObject
	{
		
		public static const VALID_CHANGEED_EVENT:String = "validChanged";
		
		/**
		 * 验证器框架对象编号
		 */
		public var id:String;
		
		/**
		 * 当前所属的页面文档信息
		 */
		public var document:Object;
		
		/**
		 * 强制验证的验证器数目
		 * 用于保存 required 值为真的验证器的数目
		 */
		private var _requiredNum:int;
		public function get requiredNum():int{
			_requiredNum = 0;
			if ( validatorArray ){
				for each( var v:Validator in validatorArray ){
					if ( v.required ){
						_requiredNum++;
					}
				}
			}
			
			return _requiredNum;
		}
		
		/**
		 * 收到的 required 值为真的验证器数目。
		 */
		private var _requiredValidatedNum:int;
		public function get requiredValidatedNum():int{
			_requiredValidatedNum = 0;
			if ( results ){
				for each( var event:ValidationResultEvent in results ){
					if ( event.currentTarget.required && event.type == ValidationResultEvent.VALID ){
						_requiredValidatedNum++;
					}
				}
			}
			
			return _requiredValidatedNum;
		}
		
		
		/**
		 * 强制验证标识
		 * 根据自身管理的验证器来决定自己的required属性值。
		 * 只要有一个验证器是强制验证的，该required值就是真，否则相反。
		 */
		private var _required:Boolean;
		public function get required():Boolean{
			return _required;
		}
		
		/**
		 * 验证结果标识
		 * true - 成功，false - 失败
		 */
		private var _valid:Boolean;
		[Bindable("validChanged")]
		public function get valid():Boolean{
			return _valid;
		}
		
		/**
		 * 验证结果数组
		 * 存储每个验证器的验证信息
		 */
		[ArrayType("mx.events.ValidationResultEvent")]
		public var results:Array;
		
		/**
		 * 验证器数组
		 */
		[ArrayType("mx.validators.Validator")]
		private var _validatorArray:Array;
		[Bindable]
		public function get validatorArray():Array{
			return _validatorArray;
		}
		public function set validatorArray(array:Array):void{
			if ( _validatorArray != null ){
				for each( var v:Validator in _validatorArray ){
					v.removeEventListener(ValidationResultEvent.INVALID, resultHandler);
					v.removeEventListener(ValidationResultEvent.VALID, resultHandler);
				}
			}
			
			_validatorArray = array;
			for each( var validator:Validator in _validatorArray ){
				validator.addEventListener(ValidationResultEvent.INVALID, resultHandler);
				validator.addEventListener(ValidationResultEvent.VALID, resultHandler);
				if ( this.trigger ) validator.trigger = this.trigger;
				if ( this.triggerEvent ) validator.triggerEvent = this.triggerEvent;

				if ( requiredNum == 0 ){
					_required = false;
					_valid = true;
				}
			}
		}
		
		/**
		 * 验证触发器
		 */
		private var _trigger:IEventDispatcher;
		public function get trigger():IEventDispatcher{
			return _trigger;
		}
		public function set trigger(value:IEventDispatcher):void{
			_trigger = value;
			if ( validatorArray ){
				for each( var validator:Validator in validatorArray ){
					validator.trigger = _trigger;
				}
			}
		}
		
		/**
		 * 验证触发事件类型
		 */
		private var _triggerEvent:String = FlexEvent.VALUE_COMMIT;
		public function get triggerEvent():String{
			return _triggerEvent;
		}
		public function set triggerEvent(value:String):void{
			_triggerEvent = value;
			if ( validatorArray ){
				for each( var validator:Validator in validatorArray ){
					validator.triggerEvent = _triggerEvent;
				}
			}
		}
		
		/**
		 * 构造函数
		 * @param target
		 * @return
		 */
		public function Validators(target:IEventDispatcher=null)
		{
			super(target);
		}
		
		/**
		 * 实现IMXMLObject
		 * @param document
		 * @param id
		 * @return
		 */
		public function initialized(document:Object, id:String):void
		{
			this.id = id;
			this.document = document;
		}
		
		/**
		 * 验证器验证结果事件侦听处理函数
		 * @param event 验证结果事件信息
		 * @return
		 */
		protected function resultHandler(event:ValidationResultEvent):void{
			//更新框架验证结果数组
			var alreadyIn:Boolean;
			if ( this.results == null ) {
				results = new Array();
			}
			
			for ( var i:int = 0; i < results.length; i++ ){
				if ( results[i].currentTarget == event.currentTarget ){
					results[i] = event;
					alreadyIn = true;
				}
			}
			
			if ( !alreadyIn ){
				results.push(event);
			}
			
			//强制验证的验证器都验证过之后可以计算总体结果
			if ( requiredValidatedNum == requiredNum ){
				var wholeValid:Boolean = true;
				for ( var j:int = 0; j < results.length; j++ ){
					var isValid:Boolean = results[j].type == ValidationResultEvent.VALID;
					wholeValid = wholeValid && isValid;
				}
				if ( valid != wholeValid ){
					this._valid = wholeValid;
					dispatchEvent(new Event(VALID_CHANGEED_EVENT));
				}
				if ( valid ){
					dispatchEvent(new ValidationResultEvent(ValidationResultEvent.VALID));
				} else {
					dispatchEvent(new ValidationResultEvent(ValidationResultEvent.INVALID));
				}
			} else {
				if ( valid ){
					_valid = !valid;
					dispatchEvent(new Event(VALID_CHANGEED_EVENT));
				}
			}
		}
		
		/**
		 * 清空错误信息
		 * @return
		 */
		public function clearErrorMessages():void{
			if ( results ){
				for each( var vre:ValidationResultEvent in results ){
					if ( vre.type == ValidationResultEvent.INVALID ){
						if ( vre.currentTarget is Validator ){
							vre.results = null;
							if ( vre.currentTarget.listener ){
								vre.currentTarget.listener.errorString = null
							} else if ( vre.currentTarget.source ){
								vre.currentTarget.source.errorString = null;
							}
						} else if ( vre.currentTarget is Validators ){
							(vre.currentTarget as Validators).clearErrorMessages();
						}
					}
				}
			}
		}
		
		/**
		 * 校验所有的验证器
		 * @return
		 */
		public function validate():void{
			if ( validatorArray ){
				for each( var validator:Validator in validatorArray ){
					validator.validate();
				}
			}
		}
	}
}