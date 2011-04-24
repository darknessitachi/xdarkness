package com.xdarkness.framework.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Counts instances of a type family.
 * @author Darkness 
 * create on 2010 2010-12-8 上午10:05:18
 * @version 1.0
 * @since JDP 1.0
 */
public class TypeCounter extends HashMap<Class<?>, Integer> {
	
	private static final long serialVersionUID = -6495894002244395103L;
	
	private Class<?> baseType;

	public TypeCounter(Class<?> baseType) {
		this.baseType = baseType;
	}

	public void count(Object obj) {
		Class<?> type = obj.getClass();
		if (!baseType.isAssignableFrom(type))
			throw new RuntimeException(obj + " incorrect type: " + type
					+ ", should be type or subtype of " + baseType);
		countClass(type);
	}

	private void countClass(Class<?> type) {
		Integer quantity = get(type);
		put(type, quantity == null ? 1 : quantity + 1);
		Class<?> superClass = type.getSuperclass();
		if (superClass != null && baseType.isAssignableFrom(superClass))
			countClass(superClass);
	}

	public String toString() {
		StringBuilder result = new StringBuilder("{");
		for (Map.Entry<Class<?>, Integer> pair : entrySet()) {
			result.append(pair.getKey().getSimpleName());
			result.append("=");
			result.append(pair.getValue());
			result.append(", ");
		}
		result.delete(result.length() - 2, result.length());
		result.append("}");
		return result.toString();
	}
}
