package com.viscus.explore;

public class UniqueId {
	public static void main(String[] args) {
		System.out.println(getUniqueId());
	}

	public static String getUniqueId() {
//		return Long.toBinaryString(System.currentTimeMillis());
//		return Long.toHexString(System.currentTimeMillis());
//		return Long.toOctalString(System.currentTimeMillis());
//		return Long.toString(System.currentTimeMillis());
		return Long.toString(System.currentTimeMillis(), 26);
	}
}
