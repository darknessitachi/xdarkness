package com.abigdreamer.java.net.orm;

import java.util.ArrayList;
import java.util.List;


public class OperateType {
	public static final OperateType INSERT = new OperateType(1);
	public static final OperateType UPDATE = new OperateType(2);
	public static final OperateType DELETE = new OperateType(3);
	public static final OperateType BACKUP = new OperateType(4);
	public static final OperateType DELETE_AND_BACKUP = new OperateType(5);
	public static final OperateType DELETE_AND_INSERT = new OperateType(6);
	public static final OperateType SQL = new OperateType(7);
	
	private int type;
	
	private OperateType(int type){
		this.type = type;
	}
	
	@Override
	public String toString() {
		return "" + type;
	}
	
	public static OperateType getType(int type) {
		OperateType operateType = null;
		for (int i = 0; i < types.size(); i++) {
			operateType = types.get(i);
			if (operateType.type == type) {
				return operateType;
			}
		}
		
		operateType =  new OperateType(type);
		types.add(operateType);
		return operateType;
	}
	
	private static List<OperateType> types = new ArrayList<OperateType>();
	static {
		types.add(INSERT);
		types.add(UPDATE);
		types.add(DELETE);
		types.add(BACKUP);
		types.add(DELETE_AND_BACKUP);
		types.add(DELETE_AND_INSERT);
		types.add(SQL);
	}
}
