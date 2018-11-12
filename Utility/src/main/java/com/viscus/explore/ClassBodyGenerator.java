package com.viscus.explore;


public class ClassBodyGenerator {
	private static final String strClassName = "GoogleLocation";
	private static final String strJSONResponse = "\"address_components\":String,\"formatted_address\":String,\"geometry\":String,\"types\":String";
	
   	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ClassBodyGenerator classBodyGenerator = new ClassBodyGenerator();

		String[] splitText = strJSONResponse.split(",");
		if (null != splitText && splitText.length > 0) {
			classBodyGenerator.generateClassBody(splitText);
			classBodyGenerator.generateJSONMethodCall(splitText);
		}
	}
	
	private void generateClassBody(String[] splitText){
		generateVariableDeclaration(splitText);
		generateConstrutor(splitText);
		generateGetters(splitText);
	}

	private void generateVariableDeclaration(String[] splitText) {
		String varPreFix = "";
		String outText = "";
		for (int i = 0; i < splitText.length; i++) {
			String[] temp = splitText[i].split(":");
			if (null != temp && temp.length == 2) {
				if (temp[1].toLowerCase().contains("str")) {
					outText += "String";
					varPreFix = "str";
				} else if (temp[1].toLowerCase().contains("int")) {
					outText += "int";
					varPreFix = "int";
				} else if (temp[1].toLowerCase().contains("bool")) {
					outText += "boolean";
					varPreFix = "bool";
				} else if (temp[1].toLowerCase().contains("flo")) {
					outText += "float";
					varPreFix = "flt";
				} else if (temp[1].toLowerCase().contains("dou")) {
					outText += "double";
					varPreFix = "dbl";
				} else {
					outText += "Unknown";
					varPreFix = "ukn";
				}
				outText += " " + varPreFix + temp[0].replace("\"", "") + ";\n";
			}
		}

		System.out.println(outText);
	}

	private void generateConstrutor(String[] splitText) {
		String varPreFix = "";
		String outText = "public " + strClassName + "(";
		String outAssignements = " {\n";
		for (int i = 0; i < splitText.length; i++) {
			String[] temp = splitText[i].split(":");
			if (null != temp && temp.length == 2) {
				if (temp[1].toLowerCase().contains("str")) {
					outText += "String";
					varPreFix = "str";
				} else if (temp[1].toLowerCase().contains("int")) {
					outText += "int";
					varPreFix = "int";
				} else if (temp[1].toLowerCase().contains("bool")) {
					outText += "boolean";
					varPreFix = "bool";
				} else if (temp[1].toLowerCase().contains("flo")) {
					outText += "float";
					varPreFix = "flt";
				} else if (temp[1].toLowerCase().contains("dou")) {
					outText += "double";
					varPreFix = "dbl";
				} else {
					outText += "Unknown";
					varPreFix = "ukn";
				}
				outText += " " + varPreFix + temp[0].replace("\"", "");
				if (i != splitText.length - 1) {
					outText += ", ";
				} else {
					outText += ")";
				}
				outAssignements += "this." + varPreFix + temp[0].replace("\"", "") + " = " + varPreFix + temp[0].replace("\"", "") + ";\n";
			}
		}
		outAssignements += "}";

		System.out.println(outText + outAssignements);
	}

	private void generateGetters(String[] splitText) {
		String varPreFix = "";
		String outText = "";
		for (int i = 0; i < splitText.length; i++) {
			String[] temp = splitText[i].split(":");
			if (null != temp && temp.length == 2) {
				outText += "\npublic ";
				if (temp[1].toLowerCase().contains("str")) {
					outText += "String";
					varPreFix = "str";
				} else if (temp[1].toLowerCase().contains("int")) {
					outText += "int";
					varPreFix = "int";
				} else if (temp[1].toLowerCase().contains("bool")) {
					outText += "boolean";
					varPreFix = "bool";
				} else if (temp[1].toLowerCase().contains("flo")) {
					outText += "float";
					varPreFix = "flt";
				} else if (temp[1].toLowerCase().contains("dou")) {
					outText += "double";
					varPreFix = "dbl";
				} else {
					outText += "Unknown";
					varPreFix = "ukn";
				}
				outText += " get" + temp[0].replace("\"", "") + "() {\nreturn " + varPreFix + temp[0].replace("\"", "") + ";\n}\n";
			}
		}

		System.out.println(outText);
	}

	private void generateJSONMethodCall(String[] splitText) {
		String dataType ="";
		String varPreFix = "";
		String outText = strClassName.substring(0, 1).toLowerCase()+strClassName.substring(1, strClassName.length()) + " = new "+strClassName+"(";
		for (int i = 0; i < splitText.length; i++) {
			String[] temp = splitText[i].split(":");
			if (null != temp && temp.length == 2) {
				if (temp[1].toLowerCase().contains("str")) {
					dataType = "String";
					varPreFix = "str";
				} else if (temp[1].toLowerCase().contains("int")) {
					dataType = "Int";
					varPreFix = "int";
				} else if (temp[1].toLowerCase().contains("bool")) {
					dataType = "Boolean";
					varPreFix = "bool";
				} else if (temp[1].toLowerCase().contains("flo")) {
					dataType = "Float";
					varPreFix = "flt";
				} else if (temp[1].toLowerCase().contains("dou")) {
					dataType = "Double";
					varPreFix = "dbl";
				} else {
					dataType = "Unknown";
					varPreFix = "ukn";
				}
				if(i != splitText.length-1){
					outText += "jsonObjectTemp.get" + dataType + "(\""+temp[0].replace("\"", "")+"\"), ";
				}else{
					outText += "jsonObjectTemp.get" +dataType + "(\""+temp[0].replace("\"", "")+"\"));";
				}
				
			}
		}

		System.out.println(outText);
	}

}
