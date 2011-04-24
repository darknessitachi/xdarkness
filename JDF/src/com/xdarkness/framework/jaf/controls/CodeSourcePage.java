package com.xdarkness.framework.jaf.controls;

import com.xdarkness.framework.jaf.Ajax;
import com.xdarkness.framework.jaf.Current;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.util.XString;

public class CodeSourcePage extends Ajax {
	public void getData() {
		String codeType = $V("CodeType");
		if (XString.isEmpty($V("ConditionField"))) {
			this.request.put("ConditionField", "1");
			this.request.put("ConditionValue", "1");
		}
		DataTable dt = null;
		String method = $V("Method");
		if ((XString.isEmpty(method)) && (codeType.startsWith("#"))) {
			method = codeType.substring(1);
		}
		if (XString.isNotEmpty(method)) {
			String className = method.substring(0, method.lastIndexOf("."));
			String methodName = method.substring(method.lastIndexOf(".") + 1);
			try {
				Object o = Current.invokeMethod(method,
						new Object[] { this.request });
				dt = (DataTable) o;
			} catch (Exception e) {
				throw new RuntimeException("确认类" + className + "的方法"
						+ methodName + "返回值是DataTable类型!");
			}
		} else {
			CodeSource cs = SelectTag.getCodeSourceInstance();
			dt = cs.getCodeData(codeType, this.request);
		}
		$S("DT", dt);
	}
}
