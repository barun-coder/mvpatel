package com.viscus.explore;

public class WordUtil {
	public static void main(String[] arguments) {
		System.out.println(toUCFirstWord("ramanand singh is working as android   developer"));
		System.out.println(toUCFirstSentence("ramanand singh is working as android   developer"));
	}

	public static String toUCFirstWord(String mixed) {
		if (null != mixed && mixed.length() > 0) {
			return Character.toUpperCase(mixed.charAt(0)) + mixed.substring(1);
		}
		return mixed;
	}

	public static String toUCFirstSentence(String sentence) {
		if (null == sentence || sentence.length() < 1) {
			return sentence;
		}

		final StringBuilder result = new StringBuilder(sentence.length());
		String[] words = sentence.split("\\s");
		for (int i = 0, l = words.length; i < l; ++i) {
			result.append(toUCFirstWord(words[i])).append(" ");
		}
		return result.toString().trim();
	}
}
