package com.nano.flcomp.chart {
	import com.nano.core.SysConfMgr;
	
	/**
	 * 仪表盘
	 * @version	1.0 2010-05-19
	 * @author	章小飞 zhangxf5@asiainfo-linkage.com
	 * @since  	V1.0
	 */
	public class JPan extends JChart {
		public function JPan(xPos:int=0,yPos:int=0){
			super(xPos,yPos);
			this.content=new SPan();
			(this.content as SPan).startLoadData(1000,SysConfMgr.getUrl('chartDataURL'));
			this.setCompUI(this.content);
		}
	}
}