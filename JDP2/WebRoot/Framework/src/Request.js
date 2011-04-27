
//()()
function(){

    /**
     * @Class Request 定义外部接口
     * 	Request开放接口给外部，提供两个接口：getParameter和getParameterValues
     *  这样外部的JavaScript文件就可以通过调用Request.getParameter()来执行相应的动作
     */
    Request = {
        /**
         * @method getParameter 得到URL后的参数
         * 	例如URL：http://abc?x=1&y=2， getParameter("x") 得到1
         */
        getParameter: getParameter,
		
		/**
		 * @method getParameterValues 如果有多个重复的paraName的情况下，下面这个方法返回一个包含了所有值的数组
		 * 例如http://abc?x=1&x=2&x=3，getParameterValues("x")得到[1,2,3]
		 */
        getParameterValues: getParameterValues
    };
    
    function getParameter(paraName, wnd){
    	var values = getParameterValues(paraName, wnd);
		return values.length > 0 ? values[0] : null;
    }
    
    function getParameterValues(paraName, wnd){
        
		var paraStr = getSearchStr(wnd);
		//根据“&”符号分割字符串
        var paraList = paraStr.split(/\&/g);
        
        var values = new Array();
        for (var i = 0; i < paraList.length; i++) {
            ///用正则表达式判断字符串是否是“paraName=value”的格式
            var pattern = new RegExp("^" + paraName + "[?=\\=]", "g");
            if (pattern.test(paraList[i])) {
                //将所有满足paramName=value的结果的value(解码后的value的内容)都放入一个数组中
                values.push(decodeURIComponent(paraList[i].split(/\=/g)[1]));
            }
        }
        //返回结果数组
        return values;
    }
	
	/**
	 * 得到地址栏中的get参数字符串
	 * @param {Object} wnd
	 */
	function getSearchStr(wnd) {
		//如果不提供wnd参数，则默认为当前窗口
        if (wnd == null) 
            wnd = self;
        
        //得到地址栏上“?”后边的字符串
        return wnd.location.search.slice(1);
	}
};
