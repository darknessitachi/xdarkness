package com.nano.dd{
	import com.nano.core.event.DDEvent;

	/**
	 * 可落下区域接口
	 * @version	1.3 2010-05-18
	 * @author	章小飞 zhangxf5@asiainfo-linkage.com
	 * @since  	V1.0
	 */
	public interface JDropZone{
		function getBeginX():Number;
		function getEndX():Number;
		function getBeginY():Number;
		function getEndY():Number;
		function dropOver(e:DDEvent):void;		//拖拽经过
		function drop(e:DDEvent):void;			//落下
	}
}