package com.nano.flcomp.chart {
	import com.nano.core.JComponent;
	import com.nano.flcomp.JFlComponent;
	import com.nano.core.SysClassMgr;
	/**
	 * 图表基类，所有自定义图表对象均可继承此类以提供嵌入到JPage的能力
	 * @version	1.0 2010-05-19
	 * @author	章小飞 zhangxf5@asiainfo-linkage.com
	 * @since  	V1.0
	 */
	public class JChart extends JFlComponent {		
		/**
		 * 构造方法
		 * @param	xPos
		 * @param	yPos
		 */
		public function JChart(xPos:int=0,yPos:int=0){
			this.x = xPos;
			this.y = yPos;
		}
		
		override public function clone(...args):JComponent{
			var ClassType:Class=SysClassMgr.getClasFromObj(this);
			var newObj:JComponent=new ClassType(this.x,this.y);
			newObj.transform.matrix=this.transform.matrix;
			return newObj;
		}
	}
}