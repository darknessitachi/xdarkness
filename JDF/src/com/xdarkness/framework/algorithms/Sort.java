package com.xdarkness.framework.algorithms;

import java.util.Arrays;



/**
 * @author Darkness 
 * create on 2010-12-16 上午10:19:20
 * @version 1.0
 * @since JDF 1.0
 */
public class Sort {
	
	/**
	 * 插入排序，从小到大排列
	 * @param args
	 * @return
	 */
	public static <T> T[] insertionSort(T... args){
		for (int i = 1; i < args.length; i++) {
			T arg = args[i];
			int j = i-1;
			while(j>=0 && (args[j].toString().compareTo(arg.toString()) > 0)) {
				args[j+1] = args[j];
				j--;
			}
			args[j+1] = arg;
		}
		return args;
	}
	
	public static void main(String[] args) {
		Integer[] array = insertionSort(9,7,5,6,3,4,2,10);
		System.out.println(Arrays.toString(array));
	}
}
