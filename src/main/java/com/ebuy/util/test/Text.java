package com.ebuy.util.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * $
 *
 * @author Lian
 * @date 2017/2/13
 * @since 1.0
 */
public class Text {

	public static void main(String[] args) {
		
//		Buffer

//		List<String> list1 = new ArrayList<String>();
//		List<String> list2 = new ArrayList<String>();
//
//		for (int i=0; i<strArr1.length; i++) {
//			list1.add(strArr1[i]);
//		}
//
//		for (int i=0; i<strArr2.length; i++) {
//			list2.add(strArr2[i]);
//		}
//
//		list1.removeAll(list2);
//
//		for (String str : list1) {
//			System.out.println(str);
//		}

//		HashSet<String> hashSet = new HashSet<String>();
//		hashSet.add("A");
//		hashSet.add("AB");
//		hashSet.add("AC");
//		hashSet.add("AD");
//
//		System.out.println(hashSet);
//		System.out.println(hashSet.toString());

		HashMap<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("brand1", "shop1");
		hashMap.put("brand2", "shop2");
		hashMap.put("brand3", "shop3");
		hashMap.put("brand4", "shop4");
		System.out.println(hashMap);
		System.out.println(hashMap.toString());
	}
}
