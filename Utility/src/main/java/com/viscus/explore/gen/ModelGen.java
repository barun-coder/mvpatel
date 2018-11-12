package com.viscus.explore.gen;

import java.util.ArrayList;
import java.util.List;

public class ModelGen {
	private static final String CLASS_NAME = "Discover";
	private static final String JSON_RESPONSE = "\"LoginSessionKey\":String,\"ExperianceTitle\":String,\"CategoryItems\":String,\"HashtagList\":String,\"ViewCount\":int,\"PageIndex\":int,\"PageSize\":int,\"OrderType\":String,\"Orderby\":String,\"ExperienceGuid\":String,\"City\":String,\"State\":String,\"Country\":String,\"MediaTypeId\":int,\"Itempicture\":String,\"MediaType\":String,\"ResolutionID\":int,\"PhotoCount\":int,\"Latitude\":double,\"Longitude\":double,\"ExperienceStatusID\":int,\"ExperienceStatus\":String";

	public List<String> varNames = new ArrayList<>();
	public List<String> jsonNames = new ArrayList<>();
	public List<String> varClasses = new ArrayList<>();
	private List<String> varTypes = new ArrayList<>();

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ModelGen generator = new ModelGen();

		if (generator.probeDataClasses()) {
			generator.printVarDeclaration();
			generator.printJSONConstructor();
			generator.printJSONConverter();
			generator.printListFromJSONConverter();
			generator.printPersister();
			generator.printFromContext();
			generator.printClearer();
		}
	}

	private boolean probeDataClasses() {
		varNames.clear();
		jsonNames.clear();
		varClasses.clear();
		varTypes.clear();
		if (null != JSON_RESPONSE) {
			String[] splitText = JSON_RESPONSE.split(",");
			if (null != splitText && splitText.length > 0) {
				for (int i = 0; i < splitText.length; i++) {
					String[] temp = splitText[i].split(":");
					if (null != temp && temp.length == 2) {
						String dataType = null;
						String varText = null;
						if (temp[1].toLowerCase().contains("str")) {
							dataType = "String";
							varText = "String";
						} else if (temp[1].toLowerCase().contains("int")) {
							dataType = "Int";
							varText = "int";
						} else if (temp[1].toLowerCase().contains("bool")) {
							dataType = "Boolean";
							varText = "boolean";
						} else if (temp[1].toLowerCase().contains("flo")) {
							dataType = "Float";
							varText = "float";
						} else if (temp[1].toLowerCase().contains("dou")) {
							dataType = "Double";
							varText = "double";
						} else {
							dataType = "Unknown";
							varText = "Unknown";
						}
						String varName = formatVar(temp[0].replace("\"", ""));
						if (null != varName) {
							varNames.add(varName);
							jsonNames.add(temp[0]);
						}
						if (null != dataType) {
							varClasses.add(dataType);
						}
						if (null != varText) {
							varTypes.add(varText);
						}
					}
				}
				if (varClasses.size() > 0 && varClasses.size() == varTypes.size() && varNames.size() == varClasses.size()) {
					return true;
				}
			}
		}
		return false;
	}

	private void printVarDeclaration() {
		String varText = "";
		for (int i = 0; i < varClasses.size(); i++) {
			varText += "public " + varTypes.get(i) + " " + varNames.get(i) + ";\n";
		}
		System.out.println(varText);
	}

	private void printJSONConstructor() {
		String constText = "public " + CLASS_NAME + "(JSONObject jsonObject){";
		constText += "\nif (null != jsonObject) {";
		for (int i = 0; i < varClasses.size(); i++) {
			constText += "\n this." + varNames.get(i) + " = jsonObject.opt" + varClasses.get(i) + "(" + jsonNames.get(i) + ");";
		}
		constText += "\n}\n}";
		System.out.println(constText);
	}

	private void printJSONConverter() {
		String conText = "public JSONObject toJSONObject() {";
		conText += "\nJSONObject jsonObject = new JSONObject();";
		conText += "\ntry {";
		for (int i = 0; i < varClasses.size(); i++) {
			conText += "\njsonObject.put(" + jsonNames.get(i) + ", this." + varNames.get(i) + ");";
		}
		conText += "\n} catch (JSONException e) {";
		conText += "\ne.printStackTrace();";
		conText += "\n}";
		conText += "\nreturn jsonObject;";
		conText += "\n}";
		System.out.println(conText);
	}

	private void printListFromJSONConverter() {
		String conText = "public static List<" + CLASS_NAME + "> get" + CLASS_NAME + "s(JSONArray jsonArray) {";
		conText += "\nList<" + CLASS_NAME + "> " + CLASS_NAME.toLowerCase() + "s = new ArrayList<" + CLASS_NAME + ">();";
		conText += "\nif (null != jsonArray) {";
		conText += "\nfor (int i = 0; i < jsonArray.length(); i++) {";
		conText += "\ntry {";
		conText += "\n" + CLASS_NAME.toLowerCase() + "s.add(new " + CLASS_NAME + "(jsonArray.getJSONObject(i)));";
		conText += "\n} catch (JSONException e) {";
		conText += "\ne.printStackTrace();";
		conText += "\n}";
		conText += "\n}";
		conText += "\n}";
		conText += "\nreturn " + CLASS_NAME.toLowerCase() + "s;";
		conText += "\n}";
		System.out.println(conText);
	}

	private void printPersister() {
		String conText = "public void persist(Context context) {";
		conText += "\nSharedPreferences sharedPreferences = context.getSharedPreferences(\"" + CLASS_NAME.toLowerCase()
				+ "Pref\", Context.MODE_PRIVATE);";
		conText += "\nSharedPreferences.Editor prefsEditor = sharedPreferences.edit();";
		conText += "\nprefsEditor.putString(\"" + CLASS_NAME.toLowerCase() + "\", toJSONObject().toString());";
		conText += "\nprefsEditor.commit();";
		conText += "\n}";
		System.out.println(conText);
	}

	private void printFromContext() {
		String conText = "public " + CLASS_NAME + "(Context context) {";
		conText += "\nSharedPreferences sharedPreferences = context.getSharedPreferences(\"" + CLASS_NAME.toLowerCase()
				+ "Pref\", Context.MODE_PRIVATE);";
		String objectName = CLASS_NAME.toLowerCase();
		String jsonName = objectName + "Json";
		conText += "\nString " + jsonName + " = sharedPreferences.getString(\"" + objectName + "\", \"{}\");";
		conText += "\nif (null != " + jsonName + ") {";
		conText += "\ntry {";
		conText += "\nJSONObject jsonObject = new JSONObject(" + jsonName + ");";
		conText += "\n" + CLASS_NAME + " " + objectName + " = new " + CLASS_NAME + "(jsonObject);";
		for (int i = 0; i < varClasses.size(); i++) {
			conText += "\nthis." + varNames.get(i) + " = " + objectName + "." + varNames.get(i) + ";";
		}
		conText += "\n} catch (JSONException e) {";
		conText += "\ne.printStackTrace();";
		conText += "\n}";
		conText += "\n}";
		conText += "\n}";
		System.out.println(conText);
	}

	private void printClearer() {
		String conText = "public void clearPreferance(Context context) {";
		String objectName = CLASS_NAME.toLowerCase();
		conText += "\nSharedPreferences sharedPreferences = context.getSharedPreferences(\"" + objectName
				+ "Pref\", Context.MODE_PRIVATE);";
		conText += "\nif (sharedPreferences.contains(\"" + objectName + "\")) {";
		conText += "\nSharedPreferences.Editor editor = sharedPreferences.edit();";
		conText += "\neditor.remove(\"" + objectName + "\");";
		conText += "\neditor.commit();";
		conText += "\n}";
		conText += "\n}";
		System.out.println(conText);
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
