package com.nano.math.datafitting{
	import com.nano.data.JHashMap;
	/**
	 * 指数型拟合
	 */ 
	public class PowFitting extends DataFitting{
		private var lf:LinerFitting=new LinerFitting();
		
		public function PowFitting(hisData:JHashMap=null,futureNum:int=12,deltaX:Number=1){
			super(hisData,futureNum,deltaX);
		}
		
		/**
		 * 开始计算
		 */
		private var k2:Number=0;
		
		override public function calc():JHashMap{
			this._log();
			this.lf.setHisData(this.hisData);
			this.lf.setFutureNum(this.futureNum);
			this.lf.setDeltaX(this.deltaX);
			lf.calc();
			
			//根据参数计算数据 
			k=lf.getK();
			b=lf.getB();
			k2=Math.pow(Math.E,b);
			
			//根据参数计算数据
			var keys:Array=this.hisData.keys();
			var t:Number=0;
			for(var i:int=0;i<keys.length;i++){
				var key:*=keys[i];
				t=k*Number(key);
				var value:Number=k2*Math.pow(Math.E,t);
				this.resultData.put(key+"",value.toFixed(this.dataPrecesion));
			}
			return this.resultData;
		}
		
		/**
		 * 计算未来数据
		 */ 
		override public function fore():JHashMap{
			this.calc();
			var keys:Array=this.hisData.keys();
			var startX:Number=keys[keys.length-1];
			var t:Number=0;
			for(var j:int=1;j<=this.futureNum;j++){
				var tempX:Number=startX+j*this.deltaX;
				t=k*Number(tempX);
				var tempY:Number=k2*Math.pow(Math.E,t);
				this.resultData.put(tempX+"",tempY.toFixed(this.dataPrecesion));
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
		
		override public function getFomula():String{
			this.formula="y="+this.k+"x+"+this.b;
			return this.formula;
		}
	}
}