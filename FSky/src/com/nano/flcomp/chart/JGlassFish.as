package com.nano.flcomp.chart {
	import com.nano.core.SysConfMgr;
	
	/**
	 * 鱼缸
	 * @version	1.0 2010-05-19
	 * @author	章小飞 zhangxf5@asiainfo-linkage.com
	 * @since  	V1.0
	 */
	public class JGlassFish extends JChart {
		public function JGlassFish(xPos:int=0,yPos:int=0){
			super(xPos,yPos);
			this.content=new GlassFish();
			this.setCompUI(content);
			(this.content as GlassFish).startLoadData(1000,SysConfMgr.getUrl('chartDataURL'));
		}
	}
}