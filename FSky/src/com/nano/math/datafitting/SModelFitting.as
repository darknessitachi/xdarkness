package com.nano.math.datafitting{
	import com.nano.data.JHashMap;
	/**
	 * S型拟合
	 */ 
	public class SModelFitting extends DataFitting{
		//S型曲线常量
		public static const L:Number=200000;
		
		private var lf:LinerFitting=new LinerFitting();
		
		public function SModelFitting(hisData:JHashMap=null,futureNum:int=12,deltaX:Number=1){
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
			var k2:Number=Math.pow(Math.E,b);
			
			//根据参数计算数据
			var keys:Array=this.hisData.keys();
			var t:Number=0;
			for(var i:int=0;i<keys.length;i++){
				var key:*=keys[i];
				t=1+k2*Math.pow(Math.E,k*Number(key));
				var value:Number=SModelFitting.L/t;
				this.resultData.put(key+"",value.toFixed(2));
			}
			var startX:Number=keys[keys.length-1];
			for(var j:int=1;j<=this.futureNum;j++){
				var tempX:Number=startX+j*this.deltaX;
				t=1+k2*Math.pow(Math.E,k*Number(tempX));
				var tempY:Number=SModelFitting.L/t;
				this.resultData.put(tempX+"",tempY.toFixed(2));
			}
			return this.resultData;
		}
		
		/**
		 * 对y取对数
		 */ 
		private function _log():void{
			var keys:Array=this.hisData.keys();
			for(var i:int=0;i<keys.length;i++){
				var key:*=keys[i];
				var temp:Number=Number(this.hisData.get(key));
				var value:Number=0;//Math.log(Number(this.hisData.get(key)));
				if(temp>0){
					value=Math.log(temp);
				}
				this.hisData.put(key+"",value+"");
			}
		}
	}
}