package com.viscus.explore.gen;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
	public static float MIN_LATITUDE = Float.valueOf("-90.0000");

	/**
	 * The maximum allowed latitude
	 */
	public static float MAX_LATITUDE = Float.valueOf("90.0000");

	/**
	 * The minimum allowed longitude
	 */
	public static float MIN_LONGITUDE = Float.valueOf("-180.0000");

	/**
	 * The maximum allowed longitude
	 */
	public static float MAX_LONGITUDE = Float.valueOf("180.0000");
	public static  String mWayPointLat=".5789";
	// zip code pattern Regular expression for US, CAnada, INdia |(^([0-9]{6})$)
	public static boolean isValidLatitude(String username) {
		if (isValidLat(username)) {
			try {
				float value = Float.parseFloat(username);
				if (value >= MIN_LATITUDE && value <= MAX_LATITUDE) {
					return true;
				} else {
					return false;
				}
			} catch (NumberFormatException e) {
				return false;
			}
		} else {
			return false;
		}
	}

	public static boolean isValidLongitude(String username) {
		if (isValid(username)) {
			try {
				float value = Float.parseFloat(username);
				if (value >= MIN_LONGITUDE && value <= MAX_LONGITUDE) {
					return true;
				} else {
					return false;
				}
			} catch (NumberFormatException e) {
				return false;
			}
		} else {
			return false;
		}
	}

	public static boolean isValid(String latlng) {
		String[] splistr = latlng.split("\\.");
		if (splistr.length > 1 && splistr[0].length() >0) {
			if (splistr[0].replace("-", "").length() <= 4 && splistr[0].length() >0) {
				if (splistr[0].replace("-", "").length() <= 3 && splistr[0].replace("-", "").length() >0) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	
	public static boolean isValidLat(String latlng) {
		String[] splistr = latlng.split("\\.");
		if (splistr.length > 1 && splistr[0].length() >0) {
			if (splistr[0].length() <= 3 && splistr[0].length() >0) {
				if (splistr[0].replace("-", "").length() <= 3 && splistr[0].replace("-", "").length() >0) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	private static final String USERNAME_PATTERN = "^[0-9.]$";

	public static boolean isValidUsername(String username) {
		Pattern pattern = Pattern.compile(USERNAME_PATTERN);
		Matcher matcher = pattern.matcher(username);

		return matcher.matches();
	}

	public static void main(String[] args) {
//		String date = "2015-03-21T00:00:00";
//		System.out.println("1"+isValidLatitude(".12.34.56"));
//		System.out.println("2"+isValidLatitude("-.123456"));
//		System.out.println("3"+isValidLatitude("-111.123456"));
//		System.out.println("4"+isValidLatitude("111.123456"));
//		System.out.println("5"+isValidLatitude("--.123456"));
//		System.out.println("6"+isValidLatitude("-.---"));
//		System.out.println("7"+isValidLatitude("25.123--456"));
//		System.out.println("9"+isValidLatitude("..25.123--456"));
//		System.out.println("8"+isValidLongitude("-97.874564387568756857123456"));
//	
//		boolean value = (mWayPointLat == null && mWayPointLat.length() <= 0 && !(isValidLatitude(mWayPointLat)));
//		System.out.println(mWayPointLat == null);
//		System.out.println(mWayPointLat.length() <= 0);
//		System.out.println(isValidLatitude(mWayPointLat));
		
		DecimalFormat df = new DecimalFormat("##0.00");
		System.out.println(df.format(7748476.67986789679686));
//
	}
//	public static void main(String[] args) {
//		String date = "2015-03-21T00:00:00";
//		System.out.println("1"+isValidLatitude(".12.34.56"));
//		System.out.println("2"+isValidLatitude("-.123456"));
//		System.out.println("3"+isValidLatitude("-111.123456"));
//		System.out.println("4"+isValidLatitude("111.123456"));
//		System.out.println("5"+isValidLatitude("--.123456"));
//		System.out.println("6"+isValidLatitude("-.---"));
//		System.out.println("7"+isValidLatitude("25.123--456"));
//		System.out.println("9"+isValidLatitude("..25.123--456"));
//		System.out.println("8"+isValidLongitude("-97.874564387568756857123456"));
//	
////		boolean value=(mWayPointLat == null && mWayPointLat.length() <= 0
////				&& !(isValidLatitude(mWayPointLat)));
////		System.out.println(mWayPointLat == null);
////		System.out.println(mWayPointLat.length() <= 0);
////		System.out.println(isValidLatitude(mWayPointLat));
//
//	}
}
