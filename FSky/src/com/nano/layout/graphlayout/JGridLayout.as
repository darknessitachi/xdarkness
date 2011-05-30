package com.nano.layout.graphlayout{
	import com.greensock.TweenLite;
	
	import flash.display.DisplayObject;
	import flash.geom.Point;
	
	/**
	 * 网格布局
	 * @version 1.0
	 * @author  章小飞 zhangxf5@asiainfo-linkage.com
	 * @since   v1.3 2010-06-04
	 */
	public class JGridLayout extends JDiagramLayout {
		/**
		 * 网格宽度
		 */
		public var GRID_WIDTH:Number = 50;
		/**
		 * 网格高度
		 */
		public var GRID_HEIGHT:Number = 50;
		/**
		 * 格子内部水平方向上的对齐方式，默认居中
		 * 0：left、1：center、2：right
		 */
		public var halign:int=1;
		/**
		 * 格子内部垂直方向上的对齐方式，默认居中
		 * 0：top、1：middle、2：bottom
		 */
		public var valign:int=1;
		/**
		 * 添加次序
		 * 0、从左到右；1：从上到下
		 * 默认为0
		 */ 
		public var addRule:int=0;
		/**
		 * 列数
		 */
		private var columns:int=0
		/**
		 * 行数
		 */
		private var rows:int=0;
		
		/**
		 * 网格布局
		 * @param	hgap
		 * @param	vgap
		 */
		public function JGridLayout(config:Object=null) {
			super(config.startX,config.startY,config.layoutWidth,config.layoutHeight);
			if(config.GRID_WIDTH){
				this.GRID_WIDTH=Number(config.GRID_WIDTH);
			}
			if(config.GRID_HEIGHT){
				this.GRID_HEIGHT=Number(config.GRID_HEIGHT);
			}
		}
		
		/**
		 * 对容器进行布局
		 * @param	owner
		 */
		override public function doLayout(config:*=null):void{
			this._cal();
			this.assignObjsToGrid(config?config.animation:false);
		}
		
		/**
		 * 计算行数和列数
		 */
		private function _cal():void {
			this.rows=Math.floor(this.layoutHeight/this.GRID_HEIGHT);
			this.columns=Math.floor(this.layoutWidth/this.GRID_WIDTH);
		}
		
		/**
		 * 按次序把对象分配到格子中
		 */
		private function assignObjsToGrid(animation:Boolean=false):void {
			for (var i:int = 0; i <this.nodes.length; i++){
				var p:Point=getPos(i);
				var mem:DisplayObject=this.nodes[i] as DisplayObject;
				if(animation){
					TweenLite.to(mem,0.5,{x:p.x,y:p.y});
				}else{
					mem.x=p.x;
					mem.y=p.y;
				}
			}
		}
		
		/**
		 * 根据索引获取对象在网格中的位置
		 * @param	index
		 * @return
		 */
		private function getPos(index:int):Point{
			var _row:int=0
			var _col:int=0
			if(this.addRule==0){//从左到右
				_row=Math.floor(index/this.columns);
				_col=index%this.columns;
			}else if(this.addRule==1){//从上到下
				_row=index%this.rows;
				_col=Math.floor(index/this.rows);
			}
			var _x:Number=_col*(this.GRID_WIDTH);
			var _y:Number=_row*(this.GRID_HEIGHT);
			if(this.halign==1){
				_x+=this.GRID_WIDTH/2;
			}
			if(this.halign==2){
				_x+=this.GRID_WIDTH;
			}
			if(this.valign==1){
				_y+=this.GRID_HEIGHT/2;
			}
			if(this.valign==2){
				_y+=this.GRID_HEIGHT;
			}
			_x+=this.startX;
			_y+=this.startY;
			return new Point(_x,_y);
		}
	}
}