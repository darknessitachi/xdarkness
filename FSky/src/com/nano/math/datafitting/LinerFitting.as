package com.nano.math.datafitting{
	import com.nano.data.JHashMap;
	import com.linkage.cmgrx.util.CollectionUtil;
	
	import mx.collections.ArrayCollection;
	/**
	 * 线性拟合：最小二乘法
	 */ 
	public class LinerFitting extends DataFitting{
		/**
		 * x平均值
		 */ 
		private var xavg:Number;
		/**
		 * y平均值
		 */ 
		private var yavg:Number;
		
		/**
		 * 构造方法
		 */ 
		public function LinerFitting(hisData:JHashMap=null,futureNum:int=12,deltaX:Number=1){
			super(hisData,futureNum,deltaX);
		}
		
		/**
		 * 开始计算
		 */ 
		override public function calc():JHashMap{
			this.resultData.clear();
			//计算参数
			_calc();
			//根据参数线性化历史数据
			var keys:Array=this.hisData.keys();
			for(var i:int=0;i<keys.length;i++){
				var key:*=keys[i];
				var value:*=this.k*key+this.b;
				this.resultData.put(key+"",value+"");
			}
			return this.resultData;
		}
		
		/**
		 * 计算未来数据
		 */ 
		override public function fore():JHashMap{
			this.resultData.clear();
			this.calc();
			var keys:Array=this.hisData.keys();
			var startX:Number=keys[keys.length-1];
			for(var j:int=1;j<=this.futureNum;j++){
				var tempX:Number=startX+j*this.deltaX;
				var tempY:Number=this.k*tempX+this.b;
				this.resultData.put(tempX+"",tempY+"");
			}
			return this.resultData;
		}
		
		/**
		 * 根据最小二乘法计算斜率和截距
		 */
		private function _calc():void{
			var xData:Array=this.hisData.keys();
			convertToNumber(xData);//强转成数字值
			var yData:Array=this.hisData.values();
			convertToNumber(yData);//强转成数字
			this.xavg=avg(xData);
			this.yavg=avg(yData);
			
			//计算斜率
			var temp1:Number=0;
			var temp2:Number=0;
			for(var i:int=0;i<xData.length;i++){
				var xi:Number=xData[i];
				var yi:Number=yData[i];
				temp1+=(xi-this.xavg)*(yi-this.yavg);
				temp2+=(xi-this.xavg)*(xi-this.xavg);
			}
			if(temp2){
				this.k=temp1/temp2;
			}
			
			//计算截距
			this.b=this.yavg-this.k *this.xavg;
		}
		
		/**
		 * 求数组平均值
		 */ 
		private function avg(src:Array):Number{
			var result:Number=0;
			for each(var p:Number in src){
				result+=p;
			}
			result/=src.length;
			return result;
		}
		
		/**
		 * 将数组元素格式化成数字
		 */ 
		private function convertToNumber(arr:Array):void{
			for(var i:int=0;i<arr.length;i++){
				arr[i]=Number(arr[i]);
			}
		}
		
		override public function getFomula():String{
			this.formula="y="+this.k+"x+"+this.b;
			return this.formula;
		}
	}
}