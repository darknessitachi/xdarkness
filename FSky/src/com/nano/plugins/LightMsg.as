package  com.nano.plugins{
	import com.greensock.TimelineLite;
	import com.greensock.TweenLite;
	import com.nano.core.NanoSystem;
	import com.nano.widgets.JFilledShape;
	import com.nano.widgets.JRoundRect;
	
	import flash.display.DisplayObjectContainer;
	import flash.display.Sprite;
	import flash.display.Stage;
	import flash.text.TextField;
	
	import mx.core.FlexGlobals;
	import mx.managers.ISystemManager;

	/**
	 * 消息框
	 * 模拟Ext中的一个MsgBox
	 * title区域和body区域都可以接受AS3支持的HTML标签
	 * @author	章小飞 zhangxf5@asiainfo-linkage.com
	 * @since  	2010-05-18
	 */
	public class LightMsg extends JRoundRect {
		private static var msgNum:int = 0;
		//所属的容器
		private var ownCt:Stage;
		private var postion:String;
		private var title:TextField;
		private var content:TextField;
		
		//四边边距，像素数
		private var bMargin:Number=25;
		private var rMargin:Number=5;
		private var tMargin:Number=5;
		private var lMargin:Number=5;
		
		//显示位置常量 
		public static const POSITION:Object={T:'top',R:'right',B:'bottom',L:'left',
							LT:'lefttop',RT:'righttop',RB:'rightbottom',LB:'leftbottom'};
		
		/**
		 * 构造函数
		 */		
		public function LightMsg(config:Object=null){
			if(config){
				if(config is Stage){
					this.ownCt =config as Stage;
					this.endX=200;
					this.endY=80;
					this.postion=POSITION.T;
				}else{
					this.ownCt =config.ct||config.container||config.stage||config.target;
					this.endX=config.w||config.width||200;
					this.endY=config.h||config.height||80;
					this.postion=config.position||config.p||POSITION.T;
				}
				
			}
			
			/**
			 * 如果没有找到显示消息的容器，默认为全局舞台对象。
			 */
//			if(!this.ownCt){
//				this.ownCt=NanoSystem.getStage();
//			}
			
			//默认的消息框样式
			this.strokeW=1;
			this.strokeColor=0xe8e8e8;
			this.strokeTrans=0.5;
			this.fillType=JFilledShape.FILL_TYPE.GRADIENT;
			this.gradientFillVars.colors=[0xffffff,0xEBEBEB];
			this.gradientFillVars.alphas=[0.8,0.8];
			this.ellipseHeight=10;
			this.ellipseWidth=10;
			
			//此组件不允许进行鼠标交互
			this.mouseEnabled=false;
			this.mouseChildren=false;
			//msgNum++;
			
			this.drawWidget();
		}
		
		/**
		 * 消息显示结束之后从所在的容器中删除自身
		 * @param	...args
		 */
		private function _onDelete(...args):void{
			this.ownCt.removeChild(this);
			msgNum--;
		}
		
		/**
		 * 覆盖：绘制组件
		 */
		override public function drawWidget():void {
			super.drawWidget();
			
			this.title = new TextField();
			title.selectable = false;
			title.x=5;
			title.y=5;
			title.width=this.endX-10;
			title.height=20;
			this.addChild(title);
			
			this.content=new TextField();
			content.selectable=false;
			content.wordWrap=true;
			content.x=title.x;
			content.y=title.y+title.height+3;
			content.width=this.endX-10;
			content.height=this.endY-content.y-3;
			this.addChild(content);
			
			//设置初始位置 
			var tempx:Number=0;
			var tempy:Number=0;
			switch(this.postion){
				case 'top':
					tempx=(this.ownCt.stageWidth-this.endX)/2;
					tempy=-this.endY;
					break;
				case 'rightbottom':
					tempx=this.ownCt.stageWidth-this.endX-this.rMargin;
					tempy=this.ownCt.stageHeight+this.endY-this.bMargin;
					break;
				default:
					break;
			}
			this.x=tempx;
			this.y=tempy;
			this.visible=false;
			this.ownCt.addChild(this);
		}
		
		/**
		 * 显示消息框
		 * config必须包含title和content(或者msg)两个属性，用来表示消息的标题和内容
		 * 可选一个delay参数，表示消息显示持续的时间 
		 * @param	config
		 */
		public function show(config:Object):void {
			var tempTitle:String=config?(config.title?config.title:""):"";
			var tempContent:String = config?(config.msg || config.content || ""):"";
			this.title.htmlText="<b>"+tempTitle+"</b>";
			this.content.htmlText=tempContent;
			this.visible=true;
			
			//动画效果
			var timeline:TimelineLite = new TimelineLite();
			var sx:Number=0;
			var sy:Number=0;
			var ex:Number=0;
			var ey:Number=0;
			switch(this.postion){
				case 'top':
					sx=this.x;
					sy=(this.height+5)*msgNum;
					ex=this.x;
					ey=-this.height;
					break;
				case 'rightbottom':
					sx=this.x;
					sy=this.ownCt.stageHeight-(this.height+5)*msgNum-this.bMargin;
					ex=this.x;
					ey=this.ownCt.stageHeight+this.endY;
					break;
				default:
					break;
			}
			msgNum++;
			timeline.append( new TweenLite(this,1,{x:sx,y:sy}));
			timeline.append( new TweenLite(this,0.5, {x:ex,y:ey,delay:(config?(config.delay?config.delay:null):null)||1.5,onComplete:_onDelete}));
			timeline.play();
		}
		
		/**
		 * 静态方法：显示消息框
		 * 使用此方法显示消息时无需new对象
		 * 
		 * @param	config
		 */
		public static function light(config:Object):void{
			//保证只弹出一个消息框
			if(config.single&&LightMsg.msgNum){
				return;
			}
			var msg:LightMsg=new LightMsg(config.ct||NanoSystem.getStage());
			msg.show(config);
		}
		
		/**
		 * 与light方法等效，只是为了传递参数的习惯而设置
		 * @param	title
		 * @param	msg
		 * @param	delay
		 * @param	parent
		 */
		public static function alert(title:String="",msg:String="",delay:int=3,parent:DisplayObjectContainer=null):void{
			var msgBox:LightMsg=new LightMsg((parent is Stage)?parent:NanoSystem.getStage());
			msgBox.show({title:title,msg:msg,delay:delay});
		}
	}
}