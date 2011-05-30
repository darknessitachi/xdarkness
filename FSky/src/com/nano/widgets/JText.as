package com.nano.widgets {
	import com.nano.core.JComponent;
	import com.nano.core.SysEventMgr;
	import com.nano.core.event.SelectionEvent;
	import com.nano.core.SysClassMgr;
	
	import flash.events.Event;
	import flash.events.MouseEvent;
	import flash.text.TextField;
	import flash.text.TextFieldType;
	/**
	 * 该类被设计用来包装原生的TextField对象，以提供动态插入文本和更改文本框尺寸的能力
	 * @version	1.0
	 * @author	章小飞 zhangxf5@asiainfo-linkage.com
	 * @since  	v1.3 2010-05-27
	 */
	public class JText extends JRect{
		private var content:TextField=new TextField();
		/**
		 * 文本框允许的最小宽度
		 */ 
		public var minWidth:Number=200;
		/**
		 * 文本框允许的最小高度
		 */ 
		public var minHeight:Number=40;
		/**
		 * 文本内容
		 */ 
		public var text:String="切换到'选中'模式，然后双击修改内容";
		
		public function JText(x:Number = 0, y:Number = 0){
			super(x,y);
			this.mouseChildren=false;
			this.content.type=TextFieldType.INPUT;
			this.content.wordWrap=true;
			this.content.multiline=true;
			this.content.selectable=false;
			this.content.scrollV=10;
			this.addChild(this.content);
			
			this.content.addEventListener(Event.CHANGE,function(e:Event):void{
				text=content.text||"";
			});
			this.addListener("__dbclick",__tdbclick);
		}
		
		override protected function _drawMouseUp(e:MouseEvent):void {
			super._drawMouseUp(e);
			if(!this.endX||this.endX<this.minWidth){
				this.endX=this.minWidth;
			}
			if(!this.endY||this.endY<this.minHeight){
				this.endY=this.minHeight
			}
			this.setEndPoint(this.endX,this.endY);
			this.setStrokeStyle(2,0x0000ff,0);
			this.setFillStyle(0xffffff,0);
			SysEventMgr.on(this,MouseEvent.MOUSE_OVER,_onMouseOver);
			SysEventMgr.on(this,MouseEvent.MOUSE_OUT,_onMouseOut);	
		}
		
		private function __tdbclick(...args):void{
			this.mouseChildren=true;
			this.content.selectable=true;
			this.content.mouseEnabled=true;
		}
		
		private function _onMouseOver(...args):void{
			this._drawSelRect();
		}
		
		private function _onMouseOut(...args):void{
			this.drawWidget();
		}
		
		override public function onDeSelect(e:SelectionEvent):void{
			this.mouseChildren=false;
			this.content.selectable=false;
			this.content.mouseEnabled=false;
			super.onDeSelect(e)
		}
		
		override public function drawWidget():void {
			super.drawWidget();
			this.content.width=this.endX-1;
			this.content.height=this.endY-1;
			this.content.text=text||"";
		}
		
		override public function clone(...args):JComponent{
			var ClassType:Class=SysClassMgr.getClasFromObj(this);
			var newObj:*=new ClassType(this.x,this.y);
			newObj.strokeStyle(this.strokeW,this.strokeColor,this.strokeTrans);
			newObj.fillStyle(this.fillColor,this.fillTrans);
			newObj.useFill=this.useFill;
			newObj.fillType=this.fillType;
			newObj.endX=this.endX;
			newObj.endY=this.endY;
			newObj.drawWidget();
			newObj.transformMatrix=this.transform.matrix;
			return newObj;
		}
	}
}