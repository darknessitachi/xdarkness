package com.nano.plugins {
	import com.greensock.TweenMax;
	import com.nano.widgets.JRoundRect;
	
	import flash.display.DisplayObjectContainer;
	import flash.events.Event;
	import flash.events.FocusEvent;
	import flash.events.KeyboardEvent;
	import flash.text.TextField;
	import flash.text.TextFieldType;
	
	/**
	 * 动态搜索框
	 * @version	1.3 2010-05-18
	 * @author	章小飞 zhangxf5@asiainfo-linkage.com
	 * @since  	V1.0
	 */
	public class SearchBar extends JRoundRect {
		private static var instanceNum:int = 0;
		private var ownCt:DisplayObjectContainer;
		
		/**
		 * 构造函数
		 * @param	container
		 * @param	w
		 * @param	h
		 */
		public function SearchBar(container:DisplayObjectContainer, w:Number = 500, h:Number = 80){
			this.ownCt = container;
			//默认的消息框样式
			this.endX=w;
			this.endY=h;
			this.strokeW=1;
			this.strokeColor=0xe8e8e8;
			this.strokeTrans=0.5;
			this.fillColor=0xF8F8F8;
			this.fillTrans=0.8;
			this.ellipseHeight=10;
			this.ellipseWidth=10;
			this.addEvents("dosearch");//在搜索框按下Enter键时将触发此事件，外部对象必须监控此事件进行响应操作
			instanceNum++;
		}
		
		/**
		 * 隐藏搜索栏目
		 * @param	...args
		 */
		public function hide(...args):void{
			this.ownCt.removeChild(this);
			instanceNum--;
		}
		
		/**
		 * 显示搜索框
		 * @param	xPos
		 * @param	yPos
		 */
		public function show(xPos:Number=-1,yPos:Number=-1):void {
			//绘制消息框自身
			this.drawWidget();
			//添加显示标题和消息内容的文本域
			var title:TextField = new TextField();
			title.selectable = false;
			
			title.htmlText="<b>搜索对象</b>";
			title.x=10;
			title.y=10;
			title.width=this.endX-30;
			title.height=20;
			this.addChild(title);
			
			var content:TextField=new TextField();
			content.maxChars=50;
			content.border=true;
			content.borderColor=0xbbbbbb;
			content.background=true;
			content.backgroundColor=0xffffff;
			content.multiline=false;
			content.type=TextFieldType.INPUT;
			content.htmlText="测试搜索工具条";
			content.x=title.x;
			content.y=title.y+title.height+3;
			content.width=this.endX-30;
			content.height=this.endY-content.y-25;
			this.addChild(content);
			
			content.addEventListener(FocusEvent.FOCUS_IN,function(e:Event):void{content.borderColor=0x6666ff;});
			content.addEventListener(FocusEvent.FOCUS_OUT,function(e:Event):void{content.borderColor=0xbbbbbb;});
			content.addEventListener(KeyboardEvent.KEY_DOWN,function(e:KeyboardEvent):void{
				//监控Enter键，触发事件
				if(e.keyCode==13){
					fireEvent("dosearch");
				}
			});
			
			TweenMax.to(this, 0.1, { glowFilter: { color:0x000000, alpha:0.5, blurX:15, blurY:15 }} );
			//设置起始位置
			var tempX:Number = 0;
			if (xPos!=-1) {
				tempX = xPos;
			}else {
				tempX=(this.ownCt.width-this.endX)/2;
			}
			this.x = -this.endX;
			if (yPos!=-1) {
				this.y = yPos;
			}else {
				this.y = (ownCt.height - this.endY) / 2;
			}
			this.ownCt.addChild(this);
			
			TweenMax.to(this,0.5,{x:tempX,y:this.y});
		}
	}
}