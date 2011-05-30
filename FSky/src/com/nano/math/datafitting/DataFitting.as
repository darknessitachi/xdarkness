package com.nano.math.datafitting{
	import com.nano.data.JHashMap;
	
	import mx.collections.ArrayCollection;

	/**
	 * 数据拟合基类
	 * @version	1.0 2010-09-10
	 * @author	章小飞 zhangxf5@asiainfo-linkage.com
	 * @since  	V1.0
	 */
	public class DataFitting{
		/**
		 * 历史数据，对象格式{x:0,y:0}
		 */ 
		protected var hisData:JHashMap;
		/**
		 * 预测数据集合
		 */
		protected var resultData:JHashMap=new JHashMap();
		/**
		 * 未来点数
		 */ 
		protected var futureNum:int;
		/**
		 * x增加步长
		 */
		protected var deltaX:Number=1;
		/**
		 * 数据精度
		 */
		public var dataPrecesion:int=3;
		/**
		 * 数学公式
		 */
		protected var formula:String="";
		/**
		 * 斜率
		 */ 
		protected var k:Number=0;
		/**
		 * 截距
		 */ 
		protected var b:Number=0;
		
		/**
		 * 构造方法
		 */ 
		public function DataFitting(hisData:JHashMap=null,futureNum:int=12,deltaX:int=1){
			this.hisData=hisData;
			this.futureNum=futureNum;
			this.deltaX=deltaX;
		}
		
		/**
		 * 运算，拟合
		 */ 
		public function calc():JHashMap{
			return this.resultData;
		}
		
		/**
		 * 预测，所有子类都必须覆盖此方法提供特定的实现
		 * 此方法默认什么事情都不做
		 */ 
		public function fore():JHashMap{
			return this.resultData;
		}
		
		public function setHisData(hisData:JHashMap):void{
			if(hisData){
				this.hisData=hisData;
			}
		}
		
		public function setFutureNum(fNum:int):void{
			if(fNum>0){
				this.futureNum=fNum;
			}
		}
		
		public function setDeltaX(deltaX:Number):void{
			if(deltaX>0){
				this.deltaX=deltaX;
			}
		}
		
		public function getK():Number{
			return this.k;
		}
		
		public function getB():Number{
			return this.b;
		}
		
		public function getFomula():String{
			return this.formula;
		}
	}
}