package com.viscus.explore;

public class StatusTagTokenizer {
	private static final Character TOK_BEGIN_CHAR = '@';
	private static final Character TOK_END_CHAR = '\f';

	public static void main(String[] args) {
		// String text = "Here we go with @Ramanand" + TOK_END_CHAR +
		// " with the latest techs on android developed by @Naveen Sharma"
		// + TOK_END_CHAR + ". A wonderful @android" + TOK_END_CHAR +
		// " developer";
		String text = "" + TOK_END_CHAR;
		System.out.println(text);

		int i = 0, beginIndex = 0, endIndex = 0;
		while (i < text.length()) {
			if (text.charAt(i) == '@') {
				beginIndex = i;
				if (endIndex > 0) {
					System.out.println("untag=" + text.substring(endIndex + 1, i));
				} else {
					System.out.println("untag=" + text.substring(endIndex, i));
				}
			} else if (text.charAt(i) == TOK_END_CHAR) {
				endIndex = i;
				if (0 != beginIndex) {
					System.out.println("tag=" + text.substring(beginIndex + 1, endIndex));
					beginIndex = 0;
				}
			}
			i++;
		}
	}
}
