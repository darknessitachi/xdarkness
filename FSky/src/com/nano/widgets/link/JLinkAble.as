package com.nano.widgets.link {
	import com.nano.widgets.link.JLink;
	import mx.collections.ArrayCollection;
	/**
	 * 所有可以连接起来的对象都必须实现此接口，__linkable.as提供了对此接口所规定方法的默认实现，在需要提供连接能力的对象中可以
	 * 使用include编译指令包含__linkable.as，或者提供自定义的实现
	 * @version	1.0
	 * @author	章小飞 zhangxf5@asiainfo-linkage.com
	 * @since  	2010-05-18
	 */
	public interface JLinkAble {
		function addLinkStart(linkTemp:JLink):JLink;
		function addLinkEnd(link:JLink,linkDepth:Number=0):void;
		function rmAllLink():void;
		function rmLinkStart(obj:JLink):void;
		function rmLinkEnd(obj:JLink):void;
		function getLinkStart():ArrayCollection;
		function getLinkEnd():ArrayCollection;
	}
}