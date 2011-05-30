package com.nano.widgets {
	import com.nano.core.JComponent;
	import com.nano.core.SysEventMgr;
	import com.nano.core.event.SelectionEvent;
	import com.nano.math.geom.JLineUtil;
	import com.nano.core.SysClassMgr;
	
	import flash.events.MouseEvent;
	import flash.geom.Point;
	
	/**
	 * 线条
	 * 与可以填充组件JFilledShape相反，线条组件JLine的特征是不可填充。
	 * 因此，JLine极其子类只具有线条颜色、线条宽度、线条透明度三个典型特征
	 * @version	1.1
	 * @author	章小飞 zhangxf5@asiainfo-linkage.com
	 * @since  	2009-09-22
	 */
	public class JLine extends JWidget {		
		/**
		 * 构造方法
		 * @param	strokeW
		 * @param	color
		 * @param	trans
		 */
		public function JLine(spriteX:Number=0,spriteY:Number=0,strokeW:Number=1,strokeColor:Number=0x000000,strokeTrans:Number=1) {
			this.x = spriteX;
			this.y = spriteY;
			this.strokeW = strokeW;
			this.strokeColor = strokeColor;
			this.strokeTrans = strokeTrans;
			this.graphics.lineStyle(this.strokeW,this.strokeColor,this.strokeTrans);
		}
		
		
		/**
		 * 处理绘制对象时鼠标移动事件
		 * 如果子类需要特定的实现，必须覆盖此方法
		 * @param	e
		 */
		override protected function _drawMouseMove(e:MouseEvent):void {
			var p:Point = new Point(e.stageX, e.stageY);
			p = this.globalToLocal(p);
			this.lineTo(p.x, p.y);
		}
		
		/**
		 * 设置起始点
		 * 如果在构造方法中没有给起始点坐标
		 * 必须调用此方法来设置线段的起始点，否则无法绘制
		 * @param	x
		 * @param	y
		 */
		public function moveTo(x:Number, y:Number):void {
			this.x = x;
			this.y = y;
		}
		
		/**
		 * 设置终止点
		 * @param	x
		 * @param	y
		 */
		public function lineTo(x:Number,y:Number):void {
			this.endX = x;
			this.endY = y;
			drawWidget();
		}
		
		/**
		 * 重新设置直线宽度
		 * @param	strokeW
		 */
		public function setWidth(strokeW:Number):void {
			this.strokeW = strokeW;
			drawWidget();
		}
		
		/**
		 * 对象绘制完成后重新设置起始点
		 * @param	newX
		 * @param	newY
		 */
		public function setStartPoint(newX:Number,newY:Number):void {
			this.x = newX;
			this.y = newY;
			drawWidget();
		}
		
		/**
		 * 方法覆盖：绘制组件
		 * 为了增加鼠标点击的感应范围，先在在线的外层绘制一层完全透明的线
		 */
		override public function drawWidget():void {
			this.graphics.clear();
			
			this.graphics.lineStyle(this.strokeW+10,0xff0000,0);
			this.graphics.moveTo(0,0);
			this.graphics.lineTo(this.endX, this.endY);
			
			this.graphics.lineStyle(this.strokeW, this.strokeColor, this.strokeTrans);
			this.graphics.moveTo(0,0);
			this.graphics.lineTo(this.endX, this.endY);
			
			super.drawWidget();
		}
		
		/**
		 * 线类被设计成没有填充颜色的组件
		 * 覆盖父类的填充样式方法，置为一个空方法
		 */ 
		override public function fillStyle(fillColor:Number,fillTrans:Number):void{}
		
		/**
		 * 获得当前线段的长度
		 * @return
		 */
		public function getLength():Number {
			return JLineUtil.getDistance(this.x, this.y, this.endX, this.endY);
		}
				
		/**
		 * 获取当前直线的斜率
		 * @return
		 */
		public function getSlope():Number{
			return JLineUtil.getSlope(0,0,this.endX,this.endY);
		}
		
		/**
		 * 获取当前斜率的弧度角
		 * @return
		 */
		public function getSlopAngle():Number{
			return JLineUtil.getSlopeAngle(0,0,this.endX,this.endY);
		}
		
		/**
		 * 获取线段长度
		 * @return
		 */
		public function getDistance():Number {
			return JLineUtil.getDistance(0,0,this.endX,this.endY);
		}
		
		/**
		 * 根据相对百分比，获取线段上点的坐标[global坐标，而不是本地坐标]
		 */ 
		public function getPointByPercent(per:Number):Point{
			var _x:Number=this.endX*per;
			var _y:Number=this.endY*per;
			var p:Point=new Point(_x,_y);
			p=this.localToGlobal(p);
			return p;
		}
		
		override public function getEndX():Number{
			return this.endX;
		}
		
		override public function getEndY():Number{
			return this.endY;
		}
				
		public function getBeginPoint():Point{
			return new Point(this.x,this.y);
		}
		
		public function getEndPoint():Point{
			return new Point(this.endX,this.endY);
		}
		
		override public function clone(...args):JComponent{
			var ClassType:Class=SysClassMgr.getClasFromObj(this);
			var newObj:JLine=new ClassType();
			newObj.setStrokeColor(this.strokeColor);
			newObj.setStrokeW(this.strokeW);
			newObj.setStrokeTrans(this.strokeTrans);
			newObj.endX=this.endX;
			newObj.endY=this.endY;
			newObj.drawWidget();
			newObj.transformMatrix=this.transform.matrix;
			return newObj;
		}
	}	
}