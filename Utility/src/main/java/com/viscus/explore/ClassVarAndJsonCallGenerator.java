package com.viscus.explore;

public class ClassVarAndJsonCallGenerator {
	private static final String strClassName = "FacebookProfile";
	private static final String strJSONResponse = "\"id\":String,\"name\":String,\"first_name\":String,\"last_name\":String,\"link\":String,\"birthday\":String,\"location\":String,\"bio\":String,\"quotes\":String,\"work\":String,\"education\":String,\"gender\":String,\"email\":String,\"timezone\":String,\"locale\":String,\"verified\":boolean,\"updated_time\":String,\"username\":String";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ClassVarAndJsonCallGenerator generator = new ClassVarAndJsonCallGenerator();

		String[] splitText = strJSONResponse.split(",");
		if (null != splitText && splitText.length > 0) {
			generator.generateVariableDeclaration(splitText);
			generator.generateJSONMethodCall(splitText);
		}
	}

	private void generateVariableDeclaration(String[] splitText) {
		String dataType = "";

		String constText = "public " + strClassName + "(JSONObject jsonObject){";
		constText += "\nif (null != jsonObject) {";

		String varText = "";
		for (int i = 0; i < splitText.length; i++) {
			String[] temp = splitText[i].split(":");
			if (null != temp && temp.length == 2) {
				varText += "public ";
				if (temp[1].toLowerCase().contains("str")) {
					dataType = "String";
					varText += "String";
				} else if (temp[1].toLowerCase().contains("int")) {
					dataType = "Int";
					varText += "int";
				} else if (temp[1].toLowerCase().contains("bool")) {
					dataType = "Boolean";
					varText += "boolean";
				} else if (temp[1].toLowerCase().contains("flo")) {
					dataType = "Float";
					varText += "float";
				} else if (temp[1].toLowerCase().contains("dou")) {
					dataType = "Double";
					varText += "double";
				} else {
					dataType = "Unknown";
					varText += "Unknown";
				}
				String varName = formatVar(temp[0].replace("\"", ""));
				constText += "\n this." + varName + " = jsonObject.opt" + dataType + "(\"" + temp[0].replace("\"", "") + "\");";
				varText += " " + varName + ";\n";
			}
		}

		System.out.println(varText);
		constText += "\n}\n}";
		System.out.println("\n" + constText);
	}

	private void generateJSONMethodCall(String[] splitText) {
		String dataType = "";
		String outText = "//" + strClassName.substring(0, 1).toLowerCase() + strClassName.substring(1, strClassName.length()) + " = new "
				+ strClassName + "(";
		for (int i = 0; i < splitText.length; i++) {
			String[] temp = splitText[i].split(":");
			if (null != temp && temp.length == 2) {
				if (temp[1].toLowerCase().contains("str")) {
					dataType = "String";
				} else if (temp[1].toLowerCase().contains("int")) {
					dataType = "Int";
				} else if (temp[1].toLowerCase().contains("bool")) {
					dataType = "Boolean";
				} else if (temp[1].toLowerCase().contains("flo")) {
					dataType = "Float";
				} else if (temp[1].toLowerCase().contains("dou")) {
					dataType = "Double";
				} else {
					dataType = "Unknown";
				}
				if (i != splitText.length - 1) {
					outText += "jsonObject.opt" + dataType + "(\"" + temp[0].replace("\"", "") + "\"), ";
				} else {
					outText += "jsonObject.opt" + dataType + "(\"" + temp[0].replace("\"", "") + "\"));";
				}

			}
		}

		System.out.println(outText);
	}

	private String formatVar(String unformatted) {
		if (null != unformatted) {
			unformatted = unformatted.trim();
			unformatted = unformatted.replaceAll("\\ ", "");
			if (unformatted.contains("_")) {
				String[] unfArr = unformatted.split("\\_");
				String ret = lowerCase(unfArr[0], 0);
				for (int i = 1; i < unfArr.length; i++) {
					ret += upperCase(unfArr[i], 0);
				}
				return ret;
			} else {
				return lowerCase(unformatted, 0);
			}
		}
		return "";
	}

	private String upperCase(String text, int pos) {
		if (null != text && pos >= 0 && pos < text.length()) {
			text = text.trim();
			if (text.length() == 1) {
				return text.toUpperCase();
			} else {
				String ret = null;
				if (pos == 0) {
					ret = ("" + text.charAt(0)).toUpperCase() + text.substring(1);
				} else if (pos == text.length() - 1) {
					ret = text.substring(0, text.length() - 1) + ("" + text.charAt(text.length() - 1)).toUpperCase();
				} else {
					ret = text.substring(0, pos) + ("" + text.charAt(pos)).toUpperCase() + text.substring(pos + 1);
				}
				return ret;
			}
		}
		return text;
	}

	private String lowerCase(String text, int pos) {
		if (null != text && pos >= 0 && pos < text.length()) {
			text = text.trim();
			if (text.length() == 1) {
				return text.toLowerCase();
			} else {
				String ret = null;
				if (pos == 0) {
					ret = ("" + text.charAt(0)).toLowerCase() + text.substring(1);
				} else if (pos == text.length() - 1) {
					ret = text.substring(0, text.length() - 1) + ("" + text.charAt(text.length() - 1)).toLowerCase();
				} else {
					ret = text.substring(0, pos) + ("" + text.charAt(pos)).toLowerCase() + text.substring(pos + 1);
				}
				return ret;
			}
		}
		return text;
	}

}
