package com.nano.flcomp {
	import com.nano.dd.JDragSource;
	/**
	 * 主机
	 */
	public class JHost extends JIcon implements JDragSource {
		/**
		 * 构造方法
		 */
		public function JHost(xPos:int = 0, yPos:int = 0){
			super({
				fl:new Host(),
				x:xPos,
				y:yPos
			});
		}
	}
}