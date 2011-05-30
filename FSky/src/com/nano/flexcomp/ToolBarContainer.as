package com.nano.flexcomp
{
	import flash.display.DisplayObject;
	import flash.events.MouseEvent;
	
	import mx.containers.Canvas;
	import mx.containers.HBox;
	import mx.controls.Button;
	import mx.core.UIComponent;
	import mx.effects.Zoom;
	
	public class ToolBarContainer extends Canvas{
		
		private var isDrag:Boolean;
		private var quote:Number=1;
		private var fitQuote:Number=1;
		private var zoomAll:Zoom;
		private var zoomChanged:Boolean;
		
		private var obj:UIComponent;
		
		public function ToolBarContainer()
		{
			//TODO: implement function
			super();
			
			addToolBtn();
			
			this.addEventListener(MouseEvent.MOUSE_DOWN,mouseDownHandler);
			this.addEventListener(MouseEvent.MOUSE_MOVE,mouseMoveHandler);
			this.addEventListener(MouseEvent.MOUSE_UP,mouseUpHandler);
			this.addEventListener(MouseEvent.MOUSE_WHEEL,mouseWheelHandler);
		}
		
		override public function addChild(child:DisplayObject):DisplayObject{
			var displayObject:DisplayObject=super.addChild(child);
			
			if(this.numChildren>1){
				obj=this.getChildAt(1) as UIComponent;
				
				this.setChildIndex(obj,0);
				zoomAll=new Zoom();
				zoomAll.startDelay=50;
				zoomAll.duration=200;
				zoomAll.target=obj;
				
			}
			return displayObject;
		}
		
		//初始化添加工具栏
		private function addToolBtn():void{
			
			var hBox:HBox=new HBox();
			hBox.x = 10;
			hBox.y = 10;
			hBox.setStyle("fontSize",12);
			var zoomInBtn:Button=new Button();
			zoomInBtn.width=60;
			zoomInBtn.height=20;
			zoomInBtn.label="放大";
			zoomInBtn.addEventListener(MouseEvent.CLICK,zoomIn);
			hBox.addChild(zoomInBtn);
			
			var zoomOutBtn:Button=new Button();
			zoomOutBtn.width=60;
			zoomOutBtn.height=20;
			zoomOutBtn.label="缩小";
			zoomOutBtn.addEventListener(MouseEvent.CLICK,zoomOut);
			hBox.addChild(zoomOutBtn);
			
			var zoomFitBtn:Button=new Button();
			zoomFitBtn.width=60;
			zoomFitBtn.height=20;
			zoomFitBtn.label="适合";
			zoomFitBtn.addEventListener(MouseEvent.CLICK,fitToContent);
			hBox.addChild(zoomFitBtn);
			
			this.addChild(hBox);
		}
		
		//放大
		private function zoomIn(e:MouseEvent):void{
			this.zoomInObj();
		}
		
		private function zoomInObj():void{
			if(quote<2){//控制放大率
				if(zoomAll.isPlaying){
				}else{
					zoomChanged=true;
					
					zoomAll.zoomHeightFrom=quote;
					zoomAll.zoomHeightTo=quote+0.22;
					zoomAll.zoomWidthFrom=quote;
					zoomAll.zoomWidthTo=quote+0.22;
					
					quote=quote+0.22;
					zoomAll.play();
				}
			}
		}
		
		//缩小
		private function zoomOut(e:MouseEvent):void{
			this.zoomOutObj();
		}
		
		private function zoomOutObj():void{
			
			if(quote>0.09){//控制缩小率
				if(zoomAll.isPlaying){
				}else{
					zoomChanged=true;
					
					zoomAll.zoomHeightFrom=quote;
					zoomAll.zoomHeightTo=quote-0.22;
					zoomAll.zoomWidthFrom=quote;
					zoomAll.zoomWidthTo=quote-0.22;
					
					quote=quote-0.22;
					zoomAll.play();
				}
			}
		}
		
		//适合
		private function fitToContent(e:MouseEvent):void{
			if(zoomAll.isPlaying){}else{
				zoomAll.zoomHeightFrom=quote;
				zoomAll.zoomHeightTo=fitQuote;
				zoomAll.zoomWidthFrom=quote;
				zoomAll.zoomWidthTo=fitQuote;
				
				quote=fitQuote;
				zoomAll.play();
			}
		}
		
		
		
		private function mouseDownHandler(event:MouseEvent):void{
			if(event.target is Button){
				isDrag=false;
			}else{
				isDrag=true;
			}
		}
		
		private function mouseMoveHandler(event:MouseEvent):void{
			if(isDrag){
				//鼠标放在容器内;
				this.setChildIndex(obj,0);
				obj.startDrag();
			}
		}
		
		private function mouseUpHandler(event:MouseEvent):void{
			isDrag=false;
			obj.stopDrag();
		}
		//鼠标滚轮操作；
		private function mouseWheelHandler(event:MouseEvent):void{
			if(event.delta>0){
				this.zoomIn(event);
			}else{
				this.zoomOut(event);
			}
		}
		
		
	}
}
