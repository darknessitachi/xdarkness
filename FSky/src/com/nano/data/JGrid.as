package com.nano.data{
	import flash.net.URLLoaderDataFormat;
	/**
	 * 二维网格结构，
	 * 可以根据格子的x,y坐标插入、删除对象，
	 * 该类一般用于网格碰转检测
	 * @version	1.3 2010-05-18
	 * @author	章小飞 zhangxf5@asiainfo-linkage.com
	 * @since  	V1.0
	 */
	public class JGrid {
		private var content:Array = [];
		private var rows:int;
		private var columns:int;
		
		/**
		 * 构造方法
		 * @param	rows
		 * @param	columns
		 */
		public function JGrid(rows:int = 0,columns:int=0 ) {
			this.rows=rows;
			this.columns=columns;
			this.makeGrid(rows,columns);
		}
		
		/**
		 * 构造一个网格数据结构
		 * @param	r
		 * @param	c
		 */
		private function makeGrid(r:int,c:int):void {
			for (var i:int = 0; i < r;i++ ) {
				this.content[i] = [];
				for (var j:int = 0; j < c;j++ ) {
					this.content[i][j] = [];
				}
			}
		}
		
		/**
		 * 把对象添加到网格中
		 * @param	r
		 * @param	c
		 * @param	obj
		 */
		public function addToGrid(r:int,c:int,obj:*):void {
			//超出格子边界的对象直接无视
			if (r>=this.rows||c>=this.columns) {
				//throw("超出格子边界");
				return;
			}
			this.content[r][c].push(obj);
		}
		
		/**
		 * 从格子中删除一个对象
		 * @param	r
		 * @param	c
		 * @param	obj
		 */
		public function rmFromGrid(r:int,c:int,obj:*):void {
			//超出格子边界的对象直接无视
			if (r>=this.rows||c>=this.columns) {
				//throw("超出格子边界");
				return;
			}
			var arr:Array = this.content[r][c];
			for (var i:int = 0; i < arr.length;i++ ) {
				if (arr[i]==obj) {
					arr[i] = null;
					return;
				}
			}
		}
		
		/**
		 * 清空格子
		 */
		public function clearGrid():void {
			this.makeGrid(this.rows,this.columns);
		}
		
		/**
		 * 获得一个单元格
		 * @param	r
		 * @param	c
		 */
		public function getCell(r:int,c:int):Array {
			return this.content[r][c] as Array;
		}
		
		public function getRows():int {
			return this.rows;
		}
		
		public function getColumns():int {
			return this.columns;
		}
	}
}