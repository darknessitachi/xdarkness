package com.nano.widgets {
	import flash.display.Loader;
	import flash.net.URLRequest;
	import flash.utils.ByteArray;
	/**
	 * 该类被设计用来包装原生的Image对象，以提供快捷的换图功能
	 * <p>
	 * <b>注意：该功能尚处于设计阶段，不可基于此代码进行开发。</b>
	 * </p>
	 * @version	1.1
	 * @author	章小飞 zhangxf5@asiainfo-linkage.com
	 * @since  	2009-09-22
	 */
	public class JImg extends Loader{
		private var imgUrl:String = null;
		private var data:ByteArray = null;
		private var isEditAble:Boolean = true;
		
		/**
		 * 构造方法
		 */ 
		public function JImg(imgUrl:String,data:ByteArray) {
			if (imgUrl!=null) {
				this.imgUrl = imgUrl;
				this.load(new URLRequest(this.imgUrl));
			}else if (data!=null) {
				this.data = data;
				this.loadBytes(this.data);
			}
		}
		
		public function canEdit():Boolean {
			return this.isEditAble;
		}
		
		public function setPosition(newX:Number,newY:Number):void {
			this.x = newX;
			this.y = newY;
		}
	}
}