
function Money(){
	var ipts = document.getElementsByTagName("input");
	if(ipts.length == 0)
		return null;
	
	this.moneyInputs = [];

	for(var i=0,j=ipts.length; i<j; i++){
		var _ipt = ipts[i];
		if(_ipt.xtype == "money")
			this.moneyInputs.push(_ipt);
	}
	return this;
}

Money.prototype = {
	init: function(){
		var moneyInputs = this.moneyInputs;
		if(moneyInputs) {
			for(var i=0,j=moneyInputs.length;i<j;i++){
				addEvent(moneyInputs[i],"blur",function(){
					if(/(^\d+$)|(^\d+\.\d+$)/.test(this.value)){
						this.value = Money.formatNum(Math.getPrecision(this.value,2));
					}
				});
				addEvent(moneyInputs[i],"focus",function(){
					//if(/(^\d+$)|(^\d+\.\d+$)/.test(this.value)){
						this.value = Money.filterCommaNumber(this.value);
					//}
				});
			}
		}
	}
}

//domReady(function(){
//	new Money().init();
//});

/**
 * 根据新输入的数字或小数点格式化num的格式
 * @param num
 */
Money.formatNum = function (num){
    var numStr = new String(num);
    if (numStr == null || numStr.length == 0) return "";
    var parCount = 0;
    var result = "";

    var dotIndex = numStr.indexOf(".");
    var integer = dotIndex >= 0 ? (dotIndex == 0 ? "0" : Money.filterCommaNumber(numStr.substring(0, dotIndex))) : Money.filterCommaNumber(numStr);
    var fraction = dotIndex >= 0 ? Money.filterCommaNumber(numStr.substring(dotIndex).replace(".", "")).replace(".", "") : "";//考虑集中情况
    for (var i = integer.length - 1; i >= 0; i--){
        var figure = integer.charAt(i);
        var code = integer.charCodeAt(i);
        if (code >= 48 && code <= 57){
            if (parCount == 3){
                result = figure + "," + result;
                parCount = 0;
            }
            else result = figure + result;
            parCount++;
        }
    }

    if (fraction.length == 0 && numStr.charAt(numStr.length - 1) == ".") 
    	return result + ".";
    else 
    	return result + (fraction.length > 0 ? "." + fraction : "");
}
	
/**
 * 过滤“123,234,34”这样的数值中的“,”
 *@author <a href="zhr_sh@skytech.com">zhr_sh</a>
 *@param fnumStr 待过滤的字符串
 *@return 如果"123,234,34"，处理后，则返回“12323434”
 */
Money.filterCommaNumber = function (fnumStr) {
    if (fnumStr.charAt(fnumStr.length - 1) == ".") fnumStr = fnumStr.substring(0, fnumStr.length - 1);
    var normalNum = fnumStr;
    var firstcomma = normalNum.indexOf(",");
    while (firstcomma > 0) {
        normalNum = normalNum.substring(0, firstcomma) + normalNum.substring(firstcomma + 1);
        firstcomma = normalNum.indexOf(",");
    }
    return normalNum;
}

/**
 * 根据参数将原值小数点后转化为指定长度
 *@author <a href="zhr_sh@skytech.com">zhr_sh</a>
 *@param normailNumber  str  需要处理的数值
 *@param precision  int     小数点位数
 *
 *@return      str
 */
Math.getPrecision = function(normalNumber, precision) {
	var baseNumber = Math.pow(10, precision);
	normalNumber = Math.round(normalNumber * baseNumber);
	return normalNumber / baseNumber;
}

/**
 *获得指定格式(小数位数)的double的String类型
 *@author <a href="zhr_sh@skytech.com">zhr_sh</a>
 *@param str str 待处理的数值字符串
 *@param  fractionLength int 小数位数
 *@return   返回处理后的数值字符串
 */
function getFormatedDoubleStr(str, fractionLength){
    var numstrs = str.split(".");
    var fraction = "";
    if (numstrs.length == 2) {
        fraction = numstrs[1];   //小数部分
        if (fractionLength > 0){   //保留fractionLength位小数
            if (fraction.length <= fractionLength){
                var range = fractionLength - fraction.length;
                for (var i = 0; i < range; i ++){
                    fraction = fraction + "0";
                }
                return numstrs[0] + "." + fraction; //补足少位返回 end
            }else{//小数部分超过需要长度，从末尾开始四舍五入
                var tail = fraction.substring(fractionLength);  //超出部分 （位数大于0）
                var remain = fraction.substring(0, fractionLength);  //保留部分 (位数大于0)
                var toadd = getRoundNum(tail);   //对tail四舍五入的结果
                var originLength = remain.length;
                remain = new Number(remain) + toadd + "";
                var currLength = remain.length;
                if (currLength < originLength){
                    for (var m = 0; m < originLength - currLength; m ++)
                        remain = "0" + remain;
                }else if (currLength > originLength){
                    numstrs[0] = new Number(numstrs[0]) + 1 + "";
                    remain = remain.substring(1);
                }
                return numstrs[0] + "." + remain;
            }
        }else{  //取零位小数
            return new Number(numstrs[0]) + getRoundNum(numstrs[1]) + "";
        }
    }else{         //小数位为fractionLength个０
        if (fractionLength > 0) {
            for (var n = 0; n < fractionLength; n ++)
                fraction += "0";
            return numstrs[0] + "." + fraction;
        }
        else return numstrs[0];
    }
}

/**
 * 对目标字符串判断四舍五入返回0还是1
 *@author <a href="zhr_sh@skytech.com">zhr_sh</a>
 *@param str 待处理的字符串
 *
 *@return str
 */
function getRoundNum(str){
    var firstdigit = str.charAt(0);
    if (firstdigit > 4) return 1;
    else if (firstdigit < 4) return 0;
    else{
        for (var i = 1; i < str.length; i ++){
            var current = str.charAt(i);
            if (current < 4) return 0;
            else if (current > 4) return 1;
        }
        return 0;
    }
}