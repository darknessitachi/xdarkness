package com.nano.plugins{
	import com.nano.widgets.JCircle;
	
	import flash.display.DisplayObjectContainer;
	import flash.events.TimerEvent;
	import flash.utils.Timer;
	
	/**
	 * 光圈扩散效果
	 * @version 1.0 2010-05-21
	 * @author  章小飞 zhangxf5@asiainfo-linkage.com
	 * @since   v1.0
	 */
	public class JLightCircle extends JCircle{
		/**
		 * 内部定时器
		 */
		private var timer:Timer;
		/**
		 * 默认半径
		 */
		private var radius:Number=10;
		
		/**
		 * 扩散效果起始半径，像素数
		 */
		public var fromRadius:Number = 10;
		/**
		 * 扩散效果结束半径，像素数
		 */
		public var toRadius:Number=60;
		
		/**
		 * 扩散效果持续的时间
		 */
		public var spreadTime:Number = 500;
		/**
		 * 扩散效果重复的次数，默认重复3次
		 */
		public var repeatCount:int=3;
		
		/**
		 * 内部使用，每个定时器周期中半径的增量
		 */
		private var _radiusPerTimerCount:Number = (60 - 10) / (500 / 10);
		/**
		 * 内部使用，扩散动作计数器
		 */
		private var _counter:int=0;
		
		/**
		 * 构造方法
		 * @param	x		x轴坐标
		 * @param	y		y轴坐标
		 */
		public function JLightCircle(x:Number=0,y:Number=0) {
			super(x,y);
			this.strokeW=3;
			this.strokeColor=0xff0000;
			this.strokeTrans=0.8;
			this.fillColor=0xffff00;
			this.fillTrans=0.5;	
			
			this.addEvents(["b4action","acting","actioncomplete","terminated"]);
		}
		
		/**
		 * 静态方法，直接在任意的显示对象上添加效果，而不需手工创建该类的实例
		 * @param	tar			目标显示对象
		 * @param	vars		可包含11个参数
		 * <pre>
		 * 		例如:{x:0,y:0,fromRadius:20,toRadius:60,duration:1000,repeatCount:3}
		 * 			  x,y:坐标(0,0)
		 * 			  strokeW:边框粗度
		 * 			  strokeColor:边框颜色
		 *            strokeTrans:边框透明度
		 *            fillColor:填充颜色
		 *            fillTrans:填充透明度
		 * 			  fromRadius:扩散起始半径20像素
		 * 			  toRadius:扩散结束半径60像素
		 * 			  duration:每次扩散动作的持续时间1000毫秒
		 *            repeatCount:动作重复次数3次
		 * </pre>
		 */
		public static function animateTo(tar:DisplayObjectContainer,vars:*=null):JLightCircle{
			if(!tar){
				throw new Error("目标对象不能为空");
			}
			var ls:JLightCircle=new JLightCircle();
			ls.x=tar.x+tar.width/2;
			ls.y=tar.y+tar.height/2;
			if(vars){
				if(vars.x){
					ls.x=vars.x;
				}
				if(vars.y){
					ls.y=vars.y;
				}
				if(vars.strokeW){
					ls.strokeW=vars.strokeW;
				}
				if(vars.strokeColor){
					ls.strokeColor=vars.strokeColor;
				}
				if(vars.strokeTrans){
					ls.strokeTrans=vars.strokeTrans;
				}
				if(vars.fillColor){
					ls.fillColor=vars.fillColor;
				}
				if(vars.fillTrans){
					ls.fillTrans=vars.fillTrans;
				}
				if(vars.fromRadius){
					ls.fromRadius=vars.fromRadius;
					ls.radius=ls.fromRadius;
				}
				if(vars.toRadius){
					ls.toRadius=vars.toRadius;
				}
				if(vars.duration){
					ls.spreadTime=vars.duration;
				}
				if(vars.repeatCount){
					ls.repeatCount=vars.repeatCount;
				}
			}
			var p:DisplayObjectContainer=tar.parent;
			p.addChild(ls);
			p.addChild(tar);
			ls.startAction();
			return ls;
		}
		
		
		/**
		 * 开始动作
		 */
		public function startAction():void{
			this.fireEvent("b4action");
			this.timer=new Timer(10,this.spreadTime/10);
			this._radiusPerTimerCount=(this.toRadius-this.fromRadius)/(this.spreadTime/10);
			this.timer.addEventListener(TimerEvent.TIMER,_timerHandler);
			this.timer.addEventListener(TimerEvent.TIMER_COMPLETE,_timerComplete);
			this.timer.start();
		}
		
		/**
		 * 停止动作
		 */
		public function stopAction():void{
			if(this.timer&&this.timer.running){
				this.timer.stop();
			}
			this.fireEvent("terminated");
		}
		
		/**
		 * 内部定时器周期结束时的事件处理函数
		 * @param	...args
		 */
		private function _timerComplete(...args):void{
			//到达指定的重复次数之后停止定时器
			if(this._counter>=this.repeatCount-1){
				this.timer.stop();
				this._counter=0;
				this.graphics.clear();
				this.fireEvent("actioncomplete");
				this.parent.removeChild(this);
				delete this;
				return;
			}
			this.fireEvent("acting");
			this.radius=this.fromRadius;
			this.timer.reset();
			this.timer.start();
			this._counter++;
		}
		
		/**
		 * 内部定时器事件处理函数
		 * @param	...args
		 */
		private function _timerHandler(...args):void{
			this.radius+=_radiusPerTimerCount;
			this.drawWidget();
		}
		
		/**
		 * 更新对象
		 */
		override public function drawWidget():void {
			this.graphics.clear();
			this.graphics.lineStyle(this.strokeW, this.strokeColor, this.strokeTrans);
			this.graphics.beginFill(this.fillColor,this.fillTrans);
			this.graphics.drawCircle(0,0,this.radius);
			this.graphics.endFill();
		}
	}
}