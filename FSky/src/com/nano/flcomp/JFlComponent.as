package com.nano.flcomp {
	import com.greensock.TweenMax;
	import com.nano.core.JComponent;
	import com.nano.core.JEditAble;
	import com.nano.core.SysClassMgr;
	import com.nano.core.event.SelectionEvent;
	import com.nano.layout.JBorderLayout;
	import com.nano.layout.JLayoutManager;
	import com.nano.layout.JVBoxLayout;
	import com.nano.util.CollectionUtil;
	import com.nano.widgets.link.JLinkAble;
	
	import flash.display.DisplayObject;
	import flash.events.Event;
	import flash.text.TextField;
	import flash.text.TextFormat;
	import flash.text.TextFormatAlign;
	
	/**
	 * 该类可以包装一个由flash元件作为自己的UI组件。
	 * 被包装的flash对象是DisplayObject(一般是MovieClip)的子类。
	 * 通过包装一个flash制作的元件，从而可以方便地在Flex项目中使用Flash制作的组件。
	 * @version	1.3 2010-05-18
	 * @author	章小飞 zhangxf5@asiainfo-linkage.com
	 * @since  	V1.0
	 */
	public class JFlComponent extends JComponent implements JLinkAble,JEditAble{
		include "../core/event/__event.as";
		include "../widgets/link/__linkable.as";
		/**
		 * 模块的名称
		 */ 
		public var moduleName:String;
		/**
		 * 显示名称或者ID的TextField
		 */ 
		protected var nameArea:TextField;
		/**
		 * 内部包装的flash元件
		 */
		public var content:DisplayObject;
		/**
		 * 默认布局管理器:JVBoxLayout，从上到下垂直布局
		 */
		protected var layout:JLayoutManager;
		
		public var autoLayout:Boolean=true;
		
		/**
		 * 构造方法
		 * @param	xPos		x轴坐标
		 * @param	yPos		y轴坐标
		 */
		public function JFlComponent(xPos:Number=0,yPos:Number=0) {
			this.x=xPos;
			this.y=yPos;
			
			layout=new JVBoxLayout(1,2);
			layout.setOwner(this);
			
			//禁止内部子组件接受鼠标事件
			this.mouseChildren=false;
			this.useHandCursor=false;
			this.buttonMode=false;
			
			this.on("drawcomplete",_drawComplete);
		}
		
		/**
		 * 对象绘制完成之后的操作
		 * @param	e
		 */
		protected function _drawComplete(e:Event):void {
			TweenMax.to(this, 0.25, {blurFilter:{blurX:5, blurY:5, remove:true}});
		}
		
		/**
		 * 删除对象
		 */
		override protected function onRemove(...args):void{
			super.onRemove(args);
			/**
			 * 重要：由于JFlComponent被设计为“可连接”的对象，因此在删除自身之前必须删除所有连接到自己的“连接线”(所有“出线”和“入线”)
			 * 这里向连接发送删除事件
			 * 连接线在删除自身的过程中会首先从linkFrom、linkTo对象的集合中删除自己
			 * 为了避免在同一时刻操作同一个集合，这里必须首先进行一次数组“浅拷贝”操作
			 * 然后在拷贝出来的数组上进行遍历，依次发送remove事件
			 */ 
			var temp1:Array=[];
			CollectionUtil.arrCpy(temp1,this.linkFrom);
			CollectionUtil.each(temp1,function(item:*):void{
				SysEventMgr.sendMsg(new SelectionEvent(SelectionEvent.REMOVE),item);
			});
			
			var temp2:Array=[];
			CollectionUtil.arrCpy(temp2,this.linkTo);
			CollectionUtil.each(temp2,function(item:*):void{
				SysEventMgr.sendMsg(new SelectionEvent(SelectionEvent.REMOVE),item);
			});
			
			temp1=null;
			temp2=null;
			
			this.linkFrom.removeAll();
			this.linkTo.removeAll();
		}
		
		/**
		 * 获取组件名称
		 * @return
		 */
		public function getModuleName():String{
			return this.nameArea.text;
		}
		
		/**
		 * 设置组件名称
		 * @param	n
		 */
		public function setModuleName(n:String):void{
			if(this.nameArea){
				this.nameArea.text=n;
				this.moduleName=n;
			}
		}
		
		/**
		 * 设置组件的UI对象，此对象是DisplayObject的任意子类
		 * @param	obj
		 */
		override public function setCompUI(obj:DisplayObject):void{
			super.setCompUI(obj);
			this.content=obj;
			this.doLayout();
		}
		
		/**
		 * 为组件添加一个显示文本的文本框
		 * @param	h			文本区域高度
		 */
		public function addNameArea(h:Number=20):void{
			if(!this.nameArea){
				this.nameArea=new TextField();
				this.nameArea.wordWrap=true;
				//设置文本格式
				var myFormat:TextFormat = new TextFormat();
				myFormat.align = TextFormatAlign.CENTER;
				this.nameArea.defaultTextFormat=myFormat;
				nameArea.text=this.id;
				nameArea.selectable=false;
			}
			this.nameArea.y=this.getChildrenY();
			nameArea.width=this.width;
			nameArea.height=h;
			this.append(nameArea,JBorderLayout.SOUTH);
		}
		
		/**
		 * 设置布局管理器
		 * @param	layout
		 */
		public function setLayout(layout:JLayoutManager):void{
			layout.setOwner(this);
			this.layout=layout;
		}
		
		/**
		 * 强制进行布局操作，注意：在动态添加或者删除子对象之后必须调用doLayout方法进行布局，所有通过append方法添加进来的
		 * 子对象才有效，直接通过原生的addChild方法添加的组件不受布局管理器管理
		 */
		public function doLayout():void{
			this.layout.layoutContainer();
		}
		
		/**
		 * 添加子对象
		 * @param	comp
		 * @param	constrains
		 */
		public function append(comp:DisplayObject,constrains:*=null):void{
			this.layout.addComp(comp,constrains);
			if(this.autoLayout){
				this.doLayout();
			}
		}
		
		/**
		 * 从布局管理器中删除对象
		 * @param	comp
		 * @param	constrains
		 */
		public function removeComp(comp:DisplayObject,constrains:*=null):void{
			this.layout.removeComp(comp,constrains);
		}
		
		/**
		 * 覆盖：克隆操作
		 * @param	...args
		 * @return
		 */
		override public function clone(...args):JComponent{
			var ClassType:Class=SysClassMgr.getClasFromObj(this);
			var newObj:JFlComponent=new ClassType(this.x,this.y);
			newObj.transformMatrix=this.transform.matrix;
			return newObj;
		}
		
		public function getNameArea():TextField{
			return this.nameArea;
		}
		
		override public function set width(w:Number):void{
			super.width=w;
		}
		
		override public function set height(h:Number):void{
			super.height=h;
		}
	}
}