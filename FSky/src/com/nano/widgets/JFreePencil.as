package com.nano.widgets {
	import com.nano.core.JComponent;
	import com.nano.core.SysEventMgr;
	import com.nano.core.event.SelectionEvent;
	import com.nano.serialization.json.JSON;
	import com.nano.core.SysClassMgr;
	
	import flash.events.Event;
	import flash.events.MouseEvent;
	import flash.geom.Point;

	/**
	 * 可填充的任意形状
	 * 该类被设计用来在页面上使用鼠标“绘制”任意形状
	 * @version	1.3 2010-05-18
	 * @author	章小飞 zhangxf5@asiainfo-linkage.com
	 * @since  	V1.0
	 */
	public class JFreePencil extends JWidget{
		/**
		 * 记录路径，
		 * <b>
		 * <pre>
		 * 	注意： 1、请不要修改此属性名称，因为在保存和加载对象时需要根据此名称来解析值；
		 *        2、此数组中必须存储无类型的对象，格式为:{x:100,y:100}，存储其它类型的对象都有可能引起解析错误
		 * </pre>
		 * </b>
		 */
		public var linePath:Array = new Array();
		/**
		 * 是否使用填充
		 */
		public var useFill:Boolean=false;
		
		/**
		 * 构造方法
		 * @param	x
		 * @param	y
		 * @param	isFill			是否绘制填充
		 * @param	strokeW
		 * @param	strokeColor
		 * @param	strokeTrans
		 */
		public function JFreePencil(x:Number = 0, y:Number = 0, useFill:Boolean = true,strokeW:Number = 1, strokeColor:Number = 0x000000, strokeTrans:Number = 1) {
			this.x=x;
			this.y=y;
			this.strokeW = strokeW;
			this.strokeColor = strokeColor;
			this.strokeTrans = strokeTrans;
			this.useFill=useFill;
			
			SysEventMgr.on(this,SelectionEvent.REMOVE, _onDeleteHandler);
		}
		
		/**
		 * 添加到舞台结束后
		 * @param	e
		 */
		override protected function _addedToRootComplete(e:Event):void{
			super._addedToRootComplete(e);
			this._addPoint(0,0);
			this._moveTo(0,0);
		}
				
		/**
		 * 处理绘制对象时鼠标移动事件
		 * @param	e
		 */
		override protected function _drawMouseMove(e:MouseEvent):void {
			var p:Point = new Point(e.stageX, e.stageY);
			p = this.globalToLocal(p);
			this._addPoint(p.x,p.y);
			this._lineTo(p.x,p.y);
		}
		
		/**
		 * 鼠标抬起:绘制完成
		 * @param	e
		 */
		override protected function _drawMouseUp(e:MouseEvent):void {
			if(this.useFill){
				this.graphics.endFill();
			}
			super._drawMouseUp(e);
		}
		
		/**
		 * 对象被删除事件处理函数
		 * @param	e
		 */
		protected function _onDeleteHandler(e:SelectionEvent):void{
			if (!this.parent){//对象已经从显示列表中删除，直接返回
				return;
			}
			this.fireEvent("b4remove");
			this.parent.removeChild(this);
		}
		
		/**
		 * 向路径中添加一个点
		 * @param	newX
		 * @param	newY
		 */
		private function _addPoint(newX:Number,newY:Number):void{
			this.linePath.push({x:newX,y:newY});
		}
		
		/**
		 * 移动到指定的点
		 * @param	newX
		 * @param	newY
		 */
		private function _moveTo(newX:Number,newY:Number):void{
			this.graphics.lineStyle(this.strokeW,this.strokeColor,this.strokeTrans);
			if(this.useFill){
				this.graphics.beginFill(this.fillColor,this.fillTrans);
			}
			this.graphics.moveTo(newX,newY);
		}
		
		/**
		 * 连线到指定的点
		 * @param	newX
		 * @param	newY
		 */
		private function _lineTo(newX:Number,newY:Number):void{
			this.graphics.lineStyle(this.strokeW,this.strokeColor,this.strokeTrans);
			this.graphics.lineTo(newX,newY);
		}
		
		/**
		 * 获取曲线的坐标路径
		 * @return
		 */
		public function getlinePath():Array {
			return this.linePath;
		}
		
		/**
		 * 工具方法：根据记录的曲线坐标路径绘制曲线，
		 * 注意，此方法只用于“重绘”操作
		 */
		override public function drawWidget():void {
			this.graphics.clear();
			if(this.linePath.length==0){
				return;
			}
			this._moveTo(linePath[0].x,linePath[0].y);
			for(var i:int=1;i<linePath.length;i++){
				this._lineTo(linePath[i].x,linePath[i].y);
			}
			super.drawWidget();
		}
		
		override public function clone(...args):JComponent{
			var ClassType:Class=SysClassMgr.getClasFromObj(this);
			var newObj:JFreePencil=new ClassType(this.x,this.y);
			newObj.strokeStyle(this.strokeW,this.strokeColor,this.strokeTrans);
			newObj.fillStyle(this.fillColor,this.fillTrans);
			newObj.useFill=this.useFill;
			newObj.linePath=JSON.decode(JSON.encode(this.linePath));
			newObj.drawWidget();
			newObj.transform.matrix=this.transform.matrix;
			return newObj;
		}
	}
}