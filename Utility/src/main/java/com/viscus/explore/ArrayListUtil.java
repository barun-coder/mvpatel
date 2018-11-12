package com.viscus.explore;

import java.util.ArrayList;

public class ArrayListUtil {
	public static void main(String[] args) {
		ArrayListUtil arrayListUtil = new ArrayListUtil();
		ArrayList<String> list1 = new ArrayList<String>() {
			{
				add("A");
				add("B");
				add("C");
			}
		};
		
		ArrayList<String> list2 = new ArrayList<String>() {
			{
				add("X");
				add("Y");
				add("Z");
			}
		};
		arrayListUtil.pirntArrayList(list1);
		arrayListUtil.pirntArrayList(list2);
		list1.addAll(list2);
		arrayListUtil.pirntArrayList(list1);
		System.out.println(list1.containsAll(list2));
		arrayListUtil.pirntArrayList(list1);
	}

	private void pirntArrayList(ArrayList<String> list) {
		if (null != list) {
			System.out.println("\n-------------- Printing Array List --------------");
			for (int i = 0; i < list.size(); i++) {
				System.out.print(list.get(i) + ", ");
			}
		}
	}
}
