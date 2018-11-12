package com.viscus.explore;

public class CommaSep {
	public static void main(String[] argments) {
		long num = 10000000;
		String numString = Long.toString(num);
		String sepString = "";
		int firstComma = numString.length() % 3;
		for (int i = 0; i < numString.length(); i++) {
			if (i == firstComma || (i - firstComma) % 3 == 0) {
				sepString += "," + numString.charAt(i);
			} else {
				sepString += numString.charAt(i);
			}
		}
		System.out.println(sepString);
	}
}
