package com.nano.flexcomp
{
	import com.nano.core.NanoSystem;
	
	import flash.display.Sprite;
	
	import mx.containers.HBox;
	import mx.core.FlexGlobals;
	import mx.managers.ISystemManager;
	import mx.managers.PopUpManager;
	
	import spark.components.Label;
	
	/**
	 *
	 *
	 *  @see mx.managers.PopUpManager
	 *  
	 *  @langversion 3.0
	 *  @playerversion Flash 10
	 *  @productversion Flex 4
	 */ 
	public class JLoading extends HBox
	{
		
		private var message:String = "";
		private var msg:Label = new Label();
		private var leaf:LeafLoading = new LeafLoading();
		
		public function JLoading(message:String){
			super();
			this.message = message;
			this.setStyle("verticalAlign","middle");
			this.setStyle("horizontalAlign","center");
			this.verticalScrollPolicy="off";
			this.horizontalScrollPolicy="off";
		}
		
		public static function show(config:*):JLoading{
			var message:String = "";
			if(config.msg){
				message=config.msg as String;
			}
			
			var jloading:JLoading = new JLoading(message);
			var modal:Boolean = true;
			if(config.modal){
				modal=config.modal as Boolean;
			}
			if(config.width){
				jloading.width=config.width as Number;
			}
			if(config.height){
				jloading.height=config.height as Number;
			}
			var p:Sprite=config.parent;
			if(!p){
				var sm:ISystemManager = ISystemManager(FlexGlobals.topLevelApplication.systemManager);
				var mp:Object = sm.getImplementation("mx.managers.IMarshallPlanSystemManager");
				if (mp && mp.useSWFBridge())
					p = Sprite(sm.getSandboxRoot());
				else
					p = Sprite(FlexGlobals.topLevelApplication);
			}
			PopUpManager.addPopUp(jloading, p, modal);
			return jloading;
		}
		
		public static function loadingMask(msg:String = "",
										   parent:Sprite = null):JLoading{
			return show({
				msg:msg,
				parent:parent,
				width:200,
				height:72
			});
		}
		
		override protected function createChildren():void{
			super.createChildren();
			
			leaf.width = 32;
			leaf.height = 32;
			NanoSystem.addToFlex(leaf,this);
			
			msg.text = message;
			this.addElement(msg);
		}
	}
}