package com.nano.widgets.link {
	import com.nano.core.SysEventMgr;
	import com.nano.core.event.SelectionEvent;
	
	import flash.display.CapsStyle;
	import flash.display.DisplayObject;
	import flash.display.GradientType;
	import flash.display.LineScaleMode;
	import flash.display.SpreadMethod;
	import flash.events.MouseEvent;
	import flash.events.TimerEvent;
	import flash.geom.Matrix;
	import flash.utils.Timer;

	/**
	 * 流动的连接线
	 * @version	1.0 2010-03-05
	 * @author	章小飞 zhangxf5@asiainfo-linkage.com
	 * @since  	V1.0
	 */
	public class JFluxLink extends JLink{
		private var timer:Timer;
		private var fluxDirection:Number=FLUX_DIRECTION.START_TO_END;			//流动方向
		public var fluxSpeed:Number=0;											//流动速度10px
		public var autoFlux:Boolean=true;										//创建之后是否自动流动
		public var colorWidth:Number = 10;										//每种颜色的宽度，像素值
		public var borderColor:Number=0x666666;									//包裹的边框颜色
		public var borderW:Number=2;											//包裹的边框的宽度
		public var borderTrans:Number=1;										//包裹的边框的透明度
		/**
		 * 以下为流通部分填充渐变参数定义
		 * 默认为黑白相间的流动线
		 */
		private var gradientBoxMatrix:Matrix = new Matrix();					
		public var colors:Array=[0xffffff,0xffffff,0x333333,0x333333];
		public var alphas:Array=[1,1,1,1];
		private var ratios:Array=[0,128,128,255];
		/**
		 * 流动方向常量
		 */
		public static const FLUX_DIRECTION:Object={START_TO_END:1,END_TO_START:0};	//流动方向
		
		/**
		 * 构造方法
		 * @param	obj
		 */
		public function JFluxLink(obj:*=null){
			super(0,0,3,0xCCCCCC,1);
			if(obj){
				if(obj.x1&&obj.y1){
					this.setPosition(obj.x1,obj.y1);
				}
				if(obj.strokeW&&obj.srokeColor&&obj.strokeTrans){
					this.setStrokeStyle(obj.strokeW,obj.strokeColor,obj.strokeTrans);
				}
				
				if(obj.autoFlux){
					this.autoFlux=obj.autoFlux;
				}
			}
			this.isEditAble=false;
			
			gradientBoxMatrix.createGradientBox(this.colors.length*this.colorWidth,80,0, 0, 0); 
			
			this.timer = new Timer(100);
			this.timer.addEventListener(TimerEvent.TIMER,function(e:TimerEvent):void{
				if(endX==0){
					return;
				}
				//根据流动方向、流动速度计算tx值
				var txTemp:Number=(timer.currentCount*fluxSpeed)%(colors.length*colorWidth);
				if(fluxDirection==FLUX_DIRECTION.END_TO_START){
					txTemp=-txTemp;
				}
				//计算倾斜角度
				var slopeRadian:Number=Math.atan(endY/endX);
				gradientBoxMatrix.createGradientBox(colors.length*colorWidth,80,slopeRadian,txTemp, 0); 
				drawWidget();
			});
			if(this.autoFlux){
				this.startFlux();
			}
			
		}
		
		/**
		 * 开始流动
		 */
		public function startFlux():void{
			timer.start();
		}
		
		/**
		 * 停止流动
		 */
		public function stopFlux():void{
			this.timer.stop();
		}
		
		/**
		 * 设置流动方向
		 * @param	direct
		 */
		public function setFluxDirection(direct:Number):void {
			this.fluxDirection = direct;
		}
		
		override public function onSelect(e:SelectionEvent):void{
			
		}
		
		/**
		 * 重新设置流动速度
		 * @param	time
		 */
		public function setFluxSpeed(s:Number):void{
			this.fluxSpeed = s;
			this.timer = new Timer(100);
			this.timer.addEventListener(TimerEvent.TIMER,function(e:TimerEvent):void{
				//根据流动方向、流动速度计算tx值
				var txTemp:Number=(timer.currentCount*fluxSpeed)%(colors.length*colorWidth);
				if(fluxDirection==FLUX_DIRECTION.START_TO_END){
					txTemp=endX>0?txTemp:(-txTemp);
				}else if(fluxDirection==FLUX_DIRECTION.END_TO_START){
					txTemp=endX>0?(-txTemp):txTemp;
				}
				//计算倾斜角度
				var slopeRadian:Number=Math.atan(endY/endX);
				gradientBoxMatrix.createGradientBox(colors.length*colorWidth,80,slopeRadian,txTemp, 0); 
				drawWidget();
			});
			this.timer.start();
		}
		
		/**
		 * 设置流动线条的颜色
		 * @param	arr
		 */
		public function setFluxColor(arr:Array):void{
			if (arr.length!=4||arr[0]!=arr[1]||arr[2]!=arr[3]) {
				throw new Error("非法的颜色数组");
				return;
			}
			for each(var p:* in arr) {
				if (!p is Number) {
					throw new Error("非法的颜色数据类型");
					return;
				}
			}
			this.colors = arr;
		}
		
		public function setBorderColor(c:Number):void{
			this.borderColor=c;
		}
		
		public function setBorderW(w:Number):void{
			this.borderW=w;
		}
		
		public function setBorderTrans(t:Number):void{
			this.borderTrans=t;
		}
		
		public function getBorderColor():Number{
			return this.borderColor;
		}
		
		public function getBorderW():Number{
			return this.borderW;
		}
		
		public function getWidth():Number{
			return this.colorWidth+this.borderW;
		}
		
		public function getFluxSpeed():Number{
			return this.fluxSpeed;
		}
		
		public function getFluxColor():Array{
			return this.colors;
		}
		
		public function getFluxWidth():Number{
			return this.colorWidth;
		}
		
		override public function getStrokeColor():Number{
			return this.borderColor;
		}
		
		override public function getStrokeW():Number{
			return this.borderW;
		}
		
		override public function getStrokeTrans():Number{
			return this.borderTrans;
		}
		
		/**
		 * 处理绘制对象时鼠标弹起事件
		 * @param	e
		 */
		override protected function _drawMouseUp(e:MouseEvent):void {
			var temp:DisplayObject=this as DisplayObject;
			SysEventMgr.un(temp.stage,MouseEvent.MOUSE_MOVE,_drawMouseMove);
			SysEventMgr.un(temp.stage,MouseEvent.MOUSE_UP,_drawMouseUp);
		}
		
		/**
		 * 绘制组件
		 */
		override public function drawWidget():void {
			if(!this.colors||this.colors.length!=4){
				return;
			}
			this.graphics.clear();
			//绘制边框
			this.graphics.lineStyle(this.strokeW+this.borderW*2, this.borderColor, this.borderTrans,true,LineScaleMode.NORMAL,CapsStyle.NONE);
			this.graphics.moveTo(0,0);
			this.graphics.lineTo(this.endX, this.endY);
			//绘制渐变
			this.graphics.lineStyle(this.strokeW, this.strokeColor, this.strokeTrans,true,LineScaleMode.NORMAL,CapsStyle.NONE);
			this.graphics.lineGradientStyle(GradientType.LINEAR,this.colors,this.alphas,this.ratios,gradientBoxMatrix,SpreadMethod.REPEAT);
			this.graphics.moveTo(0,0);
			this.graphics.lineTo(this.endX, this.endY);
		}
	}
}