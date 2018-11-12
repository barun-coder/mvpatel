package com.viscus.explore;

import java.util.HashSet;
import java.util.Set;

public class SetIterate {
	private Set<String> tags;
	public static void main(String[] args){
		SetIterate si = new SetIterate();
		si.tags = new HashSet<>();
		si.tags.add("ram");
		si.tags.add("rama");
		si.tags.add("ramaa");
		si.tags.add("ramaaa");
		System.out.println(si.getTags());
	}
	
	public String getTags() {
		StringBuilder sb = new StringBuilder();
		for (String s : tags) {
			sb.append(s).append(", ");
		}
		String tag = sb.toString().trim();
		if (tag.endsWith(",")) {
			tag = tag.substring(0, tag.length() - 1);
		}
		return "[" + tag + "]";
	}
}
