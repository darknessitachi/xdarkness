package com.xdarkness.cms.webservice;

import com.xdarkness.cms.api.UserAPI;
import com.xdarkness.framework.util.Errorx;
import com.xdarkness.framework.util.LogUtil;
import com.xdarkness.framework.util.Mapx;

public class UserOperatorImpl implements UserOperator {
	public UserOperationResponse doUserOperation(UserOperationRequest request) {
		String operationType = request.getOperationType();
		String userCode = request.getUserCode();
		String userName = request.getUserName();
		String orgCode = request.getOrgCode();
		String orgName = request.getOrgName();
		UserOperationResponse response = new UserOperationResponse();
		int errorFlag = 0;
		int errorCode = 0;
		String errorMsg = "";
		if ("create".equals(operationType)) {
			Mapx params = new Mapx();
			params.put("Username", userCode);
			params.put("RealName", userName);
			params.put("Password", "123456");
			params.put("Email", "");
			params.put("BranchCode", orgCode);
			if (XString.isEmpty(orgCode)) {
				params.put("BranchCode", "0001");
			}
			params.put("Status", "N");
			UserAPI u = new UserAPI();
			u.setParams(params);
			if (u.insert() != -1L) {
				LogUtil.info("新建用户成功：" + userCode);
			} else {
				errorMsg = "新建用户失败，"
						+ Errorx.getMessages()[0];
				LogUtil.info(errorMsg);
				errorFlag = -1;
				errorCode = 1;
			}
		} else if ("update".equals(operationType)) {
			Mapx params = new Mapx();
			params.put("Username", userCode);
			params.put("RealName", userName);
			params.put("BranchCode", orgCode);
			UserAPI u = new UserAPI();
			u.setParams(params);

			if (u.update()) {
				LogUtil.info("成功修改用户：" + userCode);
			} else {
				errorMsg = "修改用户失败：" + userCode;
				LogUtil.info(errorMsg);
				errorFlag = -1;
				errorCode = 2;
			}
		} else if ("suspend".equals(operationType)) {
			Mapx params = new Mapx();
			params.put("Username", userCode);
			params.put("Status", "S");
			params.put("OperationType", "suspend");
			UserAPI u = new UserAPI();
			u.setParams(params);

			if (u.update()) {
				LogUtil.info("成功暂停用户：" + userCode);
			} else {
				errorMsg = "暂停失败，"
						+ Errorx.getMessages()[0];
				LogUtil.info(errorMsg);
				errorFlag = -1;
				errorCode = 3;
			}
		} else if ("restore".equals(operationType)) {
			Mapx params = new Mapx();
			params.put("Username", userCode);
			params.put("Status", "N");
			params.put("OperationType", "restore");
			UserAPI u = new UserAPI();
			u.setParams(params);

			if (u.update()) {
				LogUtil.info("成功恢复用户：" + userCode);
			} else {
				errorMsg = "恢复用户失败，"
						+ Errorx.getMessages()[0];
				LogUtil.info(errorMsg);
				errorFlag = -1;
				errorCode = 4;
			}
		} else {
			errorFlag = -1;
			errorCode = 5;
			errorMsg = "不支持的操作类型:" + operationType;
		}
		response.setErrorCode(errorCode);
		response.setErrorFlag(errorFlag);
		response.setErrorMessage(errorMsg);
		return response;
	}
}

/*
 * com.xdarkness.cms.webservice.UserOperatorImpl JD-Core Version: 0.6.0
 */