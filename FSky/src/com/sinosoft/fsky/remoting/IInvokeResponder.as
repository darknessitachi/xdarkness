package com.sinosoft.fsky.remoting
{
	public interface IInvokeResponder
	{
		function addResultListener(handler:Function,...params):IInvokeResponder;
		
		function addFaultListener(handler:Function,...params):IInvokeResponder;
	}
}