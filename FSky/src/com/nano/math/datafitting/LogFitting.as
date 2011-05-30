package com.nano.math.datafitting{
	import com.nano.data.JHashMap;
	/**
	 *对数型拟合 
	 */ 
	public class LogFitting extends DataFitting{
		private var lf:LinerFitting=new LinerFitting();
		
		/**
		 * 构造函数
		 */ 
		public function LogFitting(){
			super(hisData,futureNum,deltaX);
		}
		
		/**
		 * 开始计算
		 */ 
		override public function calc():JHashMap{
			this._log();
			this.lf.setHisData(this.hisData);
			this.lf.setFutureNum(this.futureNum);
			this.lf.setDeltaX(this.deltaX);
			lf.calc();
			
			//根据参数计算数据 
			var k:Number=lf.getK();
			var b:Number=lf.getB();
			
			//根据参数计算数据
			var keys:Array=this.hisData.keys();
			var t:Number=0;
			for(var i:int=0;i<keys.length;i++){
				var key:*=keys[i];
				var value:Number=0;//
				if(i>0){
					value=this.hisData.get(key+"");//k*Number(key)+b;
				}else{
					value=k*Math.log(i)+b;
				}
				this.resultData.put(key+"",value.toFixed(2))
			}
//			var startX:Number=keys[keys.length-1];
//			for(var j:int=1;j<=this.futureNum;j++){
//				var tempX:Number=startX+j*this.deltaX;
//				var tempY:Number=k*Math.log(Number(tempX))+b;
//				this.futureData.put(tempX+"",tempY.toFixed(2));
//			}
			return this.resultData;
		}
		
		/**
		 * 对x取对数
		 */ 
		private function _log():void{
			var keys:Array=this.hisData.keys();
			for(var i:int=0;i<keys.length;i++){
				var key:Number=Number(keys[i]);
				if(key>0){
					key=Math.log(key);
				}
				var value:Number=Number(this.hisData.get(key));
				this.hisData.put(key+"",value+"");
			}
		}
	}
}