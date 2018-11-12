package com.viscus.explore;

public class Devise {
	public static void main(String[] args) {
		int num = 1;
		for (int j = 0; j < 10; j++) {
			char[] nums = Integer.toString(num).toCharArray();
			String ret = "";
			int count = 0;
			for (int i = 0; i < nums.length; i++) {
				count++;
				ret = nums[nums.length - 1 - i] + ret;
				if (count == 3 && i != nums.length - 1) {
					ret = "," + ret;
					count = 0;
				}
				// System.out.println(ret);
			}
			System.out.println("ret=" + ret);
			num *= 10;
		}
	}
}
