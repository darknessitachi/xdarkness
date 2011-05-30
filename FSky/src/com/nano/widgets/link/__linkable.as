/**
 * 所有实现JLinkAble接口的类都必须include此文件，以支持连接线的连接操作
 * 此文件提供了JLinkAble接口中所规定方法的默认实现
 * <b>
 * 如果需要特殊的实现，可以不include此文件而提供自己override的实现
 * </b>
 * @version	1.0
 * @author	章小飞 zhangxf5@asiainfo-linkage.com
 * @since  	2010-05-18
 */
import com.nano.core.SysDepthManager;
import com.nano.widgets.link.JLink;

import flash.display.Sprite;
import flash.geom.Point;
import flash.geom.Rectangle;

import mx.collections.ArrayCollection;


/**
 * 以当前对象为起点的连接
 */
public var linkFrom:ArrayCollection = new ArrayCollection();
/**
 * 以当前对象为终点的连接
 */
public var linkTo:ArrayCollection = new ArrayCollection();

/**
 * 添加一个以当前对象为起始点的连接
 */
public function addLinkStart(linkTemp:JLink):JLink {
	var link:JLink = linkTemp;
	link.setEditAble(false);
	link.moveTo(this.x + this.width / 2, this.y + this.height / 2);
	this.linkFrom.addItem(link);
	link.setLinkFrom(this);
	this.parent.addChildAt(link, 0);
	return link;
}

/**
 * 添加一个以当前对象为终止点的连接
 */
public function addLinkEnd(link:JLink, linkDepth:Number = 0):void {
	this.linkTo.addItem(link);
	link.setLinkTo(this); //link.setLinkTo()必须在link.setEndPoint()方法之前调用，setEndPoint()方法会使用到
	var _bound:Rectangle=this.getBounds(this);
	var p:Point = new Point(_bound.x + _bound.width/2,_bound.y+ _bound.height/2);
	p=this.localToGlobal(p);
	p=link.globalToLocal(p);
	link.setEndPoint(p.x, p.y);
	
	if (linkDepth == 0){
		//将连接线添加到两端对象中叠放次序较低的之下
		SysDepthManager.addToLower(this, Sprite(link.getLinkFrom()), link);
	} else if (linkDepth == 1){
		SysDepthManager.addToHigher(this, Sprite(link.getLinkFrom()), link);
	}

//	if (link.getLinkFrom() is JModuleContainer){
//		(link.getLinkFrom() as JModuleContainer).updateZIndex();
//	}
}

/**
 * 删除与当前对象有关的所有连接线
 */
public function rmAllLink():void {
	for each (var link1:JLink in this.linkFrom){
		this.parent.removeChild(link1);
	}
	for each (var link2:JLink in this.linkTo){
		this.parent.removeChild(link2);
	}
	this.linkFrom.removeAll();
	this.linkTo.removeAll();
}

/**
 * 删除一个以当前对象为起始节点的连接
 * @param	obj
 */
public function rmLinkStart(obj:JLink):void {
	if (this.linkFrom.contains(obj)){
		this.linkFrom.removeItemAt(this.linkFrom.getItemIndex(obj));
		this.removeListenerByScope(obj);
	}
}

/**
 * 删除一个以当前对象为终止节点的连接
 * @param	obj
 */
public function rmLinkEnd(obj:JLink):void {
	if (this.linkTo.contains(obj)){
		this.linkTo.removeItemAt(this.linkTo.getItemIndex(obj));
		this.removeListenerByScope(obj);
	}
}

public function getLinkStart():ArrayCollection {
	return this.linkFrom;
}

public function getLinkEnd():ArrayCollection {
	return this.linkTo;
}