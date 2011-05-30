package com.nano.widgets.link{
	import com.nano.core.event.SelectionEvent;
	import com.nano.math.geom.JLineUtil;
	import com.nano.widgets.JArrowLine;
	
	import flash.geom.Point;
	
	/**
	 * 有方向的连接线
	 * @version	1.1 2010-05-21
	 * @author	章小飞 zhangxf5@asiainfo-linkage.com
	 * @since  	v1.0
	 */
	public class JDirectionLink extends JSimpleLink {
		/**
		 * 箭头类型，默认为双端箭头
		 */
		public var arrowType:Number = JArrowLine.ARROW_TYPE.BOTH_ARROW;
		/**
		 * 箭头三角型侧边长度
		 */
		public var arrowLen:Number = 10;
		/**
		 * 箭头一边与直线之间的夹角
		 */
		public var arrowAngle:Number = Math.PI / 8;
		protected var exAngle:Number = Math.PI+arrowAngle;
		protected var isDrawFinished:Boolean=false;
		
		/**
		 * 构造方法
		 * @param	x1				起点x坐标
		 * @param	y1				起点y坐标
		 * @param	strokeW			笔触宽度
		 * @param	strokeColor		笔触颜色
		 * @param	strokeTrans		笔触透明度
		 */
		public function JDirectionLink(x1:Number = 0, y1:Number = 0, strokeW:Number = 2, strokeColor:Number = 0x000000, strokeTrans:Number = 0.8){
			super(x1, y1, strokeW, strokeColor, strokeTrans);
		}
		
		/**
		 * 连线反选事件处理
		 * @param	e
		 */
		override public function onDeSelect(e:SelectionEvent):void{
			super.onDeSelect(e);
			if(this.selGhost&&this.parent.contains(this.selGhost)){
				this.parent.removeChild(this.selGhost);
			}
		}
		
		/**
		 * 覆盖：绘制组件
		 */
		override public function drawWidget():void{
			var _w:Number=this.linkFrom.width/2;
			var p1:Point=Point.polar(_w,this.getSlopAngle());
			this.graphics.clear();
			
			//为了增加鼠标点击的感应范围，先在在线的外层绘制一层完全透明的线
			this.graphics.lineStyle(this.strokeW+10,0xff0000,0);
			this.graphics.moveTo(0,0);
			this.graphics.lineTo(this.endX, this.endY);
			
			this.graphics.lineStyle(this.strokeW, this.strokeColor, this.strokeTrans);
			this.graphics.moveTo(p1.x,p1.y);
			
			var angle:Number=this.getSlopAngle();
			
			//绘制时：鼠标是否已脱离连接起点对象区域
			var _tempP:Point=new Point(this.endX,this.endY);
			_tempP=this.localToGlobal(_tempP);
			var _isIn:Boolean=linkFrom.hitTestPoint(_tempP.x,_tempP.y);
			
			//移动时：终止节点是否与起始节点碰撞
			var _isHit:Boolean=false;
			if(linkTo){
				var _dist:Number=JLineUtil.getDistance(this.linkFrom.x,this.linkFrom.y,this.linkTo.x,this.linkTo.y);
				if(_dist<=(this.linkFrom.width+this.linkTo.width)/2){
					_isHit=true;
				}
			}
			
			//如果鼠标任然处于起始节点内部，则不绘制
			if(!_isIn&&(!_isHit)){
				if(this.arrowType==1||this.arrowType==2){
					var temp2:Point = Point.polar(this.arrowLen,angle + arrowAngle);
					var temp3:Point = Point.polar(this.arrowLen,angle - arrowAngle);
					temp2.offset(p1.x,p1.y);
					temp3.offset(p1.x,p1.y);
					this.graphics.beginFill(this.strokeColor,this.strokeTrans);
					this.graphics.moveTo(p1.x,p1.y);
					this.graphics.lineTo(temp2.x,temp2.y);
					this.graphics.lineTo(temp3.x,temp3.y);
					this.graphics.lineTo(p1.x,p1.y);
					this.graphics.endFill();
				}
				
				var _endX:Number=0;
				var _endY:Number=0;
				
				if(this.isDrawFinished){//是否已完成绘制
					_w=this.linkTo.width/2;
					var p2:Point=Point.polar(_w,this.getSlopAngle()+Math.PI);
					p2.offset(this.endX,this.endY);
					this.graphics.lineTo(p2.x,p2.y);
					_endX=p2.x;
					_endY=p2.y;
				}else{
					this.graphics.lineTo(this.endX, this.endY);
					_endX=this.endX;
					_endY=this.endY;
				}
				
				//绘制尾部箭头
				if(this.arrowType==0||this.arrowType==2){
					var p4:Point = Point.polar(this.arrowLen,angle-exAngle);
					var p5:Point = Point.polar(this.arrowLen,angle+exAngle);
					p4.offset(_endX,_endY);
					p5.offset(_endX,_endY);
					this.graphics.beginFill(this.strokeColor,this.strokeTrans);
					this.graphics.moveTo(_endX,_endY);
					this.graphics.lineTo(p4.x,p4.y);
					this.graphics.lineTo(p5.x,p5.y);
					this.graphics.lineTo(_endX,_endY);
					this.graphics.endFill();
				}
			}
		}
		
		/**
		 * 重写：设置连接的终止点
		 */
		override public function setLinkTo(end:JLinkAble):void{
			super.setLinkTo(end);
			this.isDrawFinished=true;
		}
	}
}