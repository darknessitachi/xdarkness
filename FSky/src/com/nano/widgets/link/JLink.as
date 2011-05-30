package com.nano.widgets.link {
	import com.nano.core.JComponent;
	import com.nano.core.SysDepthManager;
	import com.nano.core.event.SelectionEvent;
	import com.nano.widgets.JLine;
	
	import flash.display.DisplayObject;
	import flash.geom.Point;
	
	/**
	 * 连接线基类，用来连接两个对象，
	 * 该类是所有连接线的基类，
	 * 该类是被设计用来继承的，一般情况下不要创建该类的实例
	 * @version	1.1
	 * @author	章小飞 zhangxf5@asiainfo-linkage.com
	 * @since  	2009-10-05
	 */
	public class JLink extends JLine {
		/**
		 * 连接的起始节点
		 */
		protected var linkFrom:JComponent;
		/**
		 * 连接的终止节点
		 */
		protected var linkTo:JComponent;
		/**
		 * 连接起始点的id
		 */ 
		public var fromId:String;
		/**
		 * 连接结束点的id
		 */ 
		public var toId:String;
		/**
		 * 连接线被选中时显示的颜色
		 */
		protected var selColor:Number = 0xffffff;
		/**
		 * 连接线被选中时外框宽度
		 */
		protected var selWidth:Number=6;
		/**
		 * 连接线被选中时显示的“影子对象”
		 */
		protected var selGhost:JLine=null;
		
		/**
		 * 构造方法
		 * @param	x1
		 * @param	y1
		 * @param	strokeW
		 * @param	strokeColor
		 * @param	strokeTrans
		 */
		public function JLink(x1:Number=0,y1:Number=0,strokeW:Number=1,strokeColor:Number=0x000000,strokeTrans:Number=1) {
			super(x1, y1, strokeW, strokeColor, strokeTrans);
		}
		
		/**
		 * 删除连接线
		 * @param	e
		 */
		override protected function onRemove(...args):void{
			super.onRemove(args);
			if(this.linkFrom){
				(this.linkFrom as JLinkAble).rmLinkStart(this);
			}
			if(this.linkTo){
				(this.linkTo as JLinkAble).rmLinkEnd(this);
			}
			this.linkFrom=null;
			this.linkTo=null;
			this.fromId=null;
			this.toId=null;
		}
		
		/**
		 * 连接起始节点被删除事件监听器
		 * @param	...args
		 */
		protected function _linkFromRemoveHandler(...args):void{
			(this.linkTo as JLinkAble).rmLinkEnd(this);
			this.linkFrom=null;
			this.parent.removeChild(this);
		}
		
		/**
		 * 连接终止节点被删除事件监听器
		 * @param	...args
		 */
		protected function _linkEndRemoveHandler(...args):void{
			(this.linkFrom as JLinkAble).rmLinkStart(this);
			this.linkTo=null;
			this.parent.removeChild(this);
		}
		
		/**
		 * 连接起始节点移动事件处理函数监听器
		 * @param	...args
		 */
		protected function _linkFromMoveHandler(...args):void{
			var tempLinkFrom:DisplayObject=this.linkFrom as DisplayObject;
			var tempLinkTo:DisplayObject=this.linkTo as DisplayObject;
			this.x=tempLinkFrom.x+tempLinkFrom.width/2;
			this.y=tempLinkFrom.y+tempLinkFrom.height/2;
			var p:Point=new Point(tempLinkTo.width/2/tempLinkTo.scaleX,tempLinkTo.height/2/tempLinkTo.scaleY);
			p=tempLinkTo.localToGlobal(p);
			p=this.globalToLocal(p);
			this.setEndPoint(p.x,p.y);
			SysDepthManager.addToLower(tempLinkFrom,tempLinkTo,this);
		}
		
		/**
		 * 连接终止节点移动事件监听器
		 * @param	...args
		 */
		protected function _linkEndMoveHandler(...args):void{
			var tempLinkFrom:DisplayObject=this.linkFrom as DisplayObject;
			var tempLinkTo:DisplayObject=this.linkTo as DisplayObject;
			var p:Point=new Point(tempLinkTo.width/2/tempLinkTo.scaleX,tempLinkTo.height/2/tempLinkTo.scaleY);
			p=tempLinkTo.localToGlobal(p);
			p=this.globalToLocal(p);
			this.setEndPoint(p.x,p.y);
			SysDepthManager.addToLower(tempLinkFrom,tempLinkTo,this);
		}
		
		/**
		 * 设置连接的起始节点
		 * @param	s
		 */
		public function setLinkFrom(s:JLinkAble):void{
			this.linkFrom=s as JComponent;
			this.fromId=(s as JComponent).id;
		}
		
		/**
		 * 设置连接的终止节点
		 * 连接线终点设置成功之后将监听起点对象、终点的各3个事件
		 * @param	e
		 */
		public function setLinkTo(end:JLinkAble):void{
			this.linkTo=end as JComponent;
			this.toId=(end as JComponent).id;
			
			this.linkFrom.addListener("moving",_linkFromMoveHandler,this);
			this.linkFrom.addListener("resize",_linkFromMoveHandler,this);
			
			this.linkTo.addListener("moving",_linkEndMoveHandler,this);
			this.linkTo.addListener("resize",_linkEndMoveHandler,this);
		}
		
		/**
		 * 连接线被选中事件处理
		 * @param	e
		 */
		override public function onSelect(e:SelectionEvent):void{
			this.isSelected=true;
			SysDepthManager.bringToTop(this);
			this.fireEvent("select",this);
			
			if(!this.selGhost){
				this.selGhost=new JLine();
			}
			
			this.selGhost.setStrokeW(this.getStrokeW()+this.selWidth);
			this.selGhost.setStrokeColor(this.selColor);
			
			var _w:Number=this.linkFrom.width/2;
			var p1:Point=Point.polar(_w,this.getSlopAngle());
			var tempX:Number=p1.x;
			var tempY:Number=p1.y;
			p1.offset(this.x,this.y);
			this.selGhost.setStartPoint(p1.x,p1.y);
			
			_w=this.linkTo.width/2;
			var p2:Point=Point.polar(_w,this.getSlopAngle()+Math.PI);
			p2.offset(this.endX,this.endY);
			p2.offset(-tempX,-tempY);
			this.selGhost.setEndPoint(p2.x,p2.y);
			
			SysDepthManager.addToBlow(this,this.selGhost);
		}
		
		/**
		 * 获取连接的起始节点
		 * @return
		 */
		public function getLinkFrom():JLinkAble{
			return this.linkFrom as JLinkAble;
		}
		
		/**
		 * 获得连接的终止节点
		 * @return
		 */
		public function getLinkTo():JLinkAble{
			return this.linkTo as JLinkAble;
		}
		
		/**
		 * 获取连接起始节点的id
		 * @return
		 */
		public function getFromId():String{
			return this.fromId;
		}
		
		/**
		 * 获得连接结束点的id
		 * @return
		 */
		public function getToId():String{
			return this.toId;
		}
		
		/**
		 * 设置连接起始节点的id，该方法一般在使用xml生成JLink对象时在内部调用，外部代码无需调用该方法
		 * @param	f
		 */
		public function setFromId(f:String):void{
			this.fromId=f;
		}
		
		/**
		 * 设置连接终止节点的id，该方法一般在使用xml生成JLink对象时在内部调用，外部代码无需调用该方法
		 * @param	t
		 */
		public function setToId(t:String):void{
			this.toId=t;
		}
		
		override public function clone(...args):JComponent{
			var comp:JComponent=super.clone();
			(comp as JLink).setLinkFrom(this.getLinkFrom());
			(comp as JLink).setLinkTo(this.getLinkTo());
			return comp;
		}
	}
}