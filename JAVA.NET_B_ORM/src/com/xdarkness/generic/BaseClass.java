package com.xdarkness.generic;

import java.lang.reflect.ParameterizedType;


/**
 * @author Darkness 
 * create on 2010-12-3 下午04:53:36
 * @version 1.0
 * @since JDP 1.0
 */
public class BaseClass<T> {

	private Class<T> genericClass;
	
	public void test(){
		
		
		genericClass = (Class<T>)((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		System.out.println(genericClass);
		System.out.println(genericClass.getName());
		
		System.out.println(genericClass.getSimpleName());
	}
}
