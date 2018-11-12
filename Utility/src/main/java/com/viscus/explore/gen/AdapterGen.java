package com.viscus.explore.gen;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AdapterGen {
	public static final String PROJECT_PATH = "G:\\Android Projects";
	private static final String LAYOUT_PATH = PROJECT_PATH + "\\res\\layout";
	private static String XML_FILE_NAME = "";

	private static String WIDGETS = "";

	public List<String> varNames = new ArrayList<>();
	public List<String> varClasses = new ArrayList<>();
	public String className, classVarName;

	private String PACKAGE_NAME =  "com.zolute.chublu";
	private File adapterDir = null;

	public static void main(String[] args) {
		AdapterGen generator = new AdapterGen();
		if (generator.generateWithFile()) {
			generator.createPaths();
			generator.probeFilesAndGenerate();
		}
	}

	private void probeFilesAndGenerate() {
		File file = new File(LAYOUT_PATH);
		File[] listOfFiles = file.listFiles();
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				String name = listOfFiles[i].getName();
				String path = listOfFiles[i].getPath();
				if (name.endsWith("_row.xml") || name.endsWith("_item.xml")) {
					XML_FILE_NAME = name;
					String data = buildWidgetIds(path);
					writeAdapterFile(data, name);
				}
			}
		}
		// buildWidgetIds();
	}

	private boolean generateWithFile() {
		File file = new File(LAYOUT_PATH);
		return file.exists() && file.isDirectory();
	}

	private void createPaths() {
		File file = new File(PROJECT_PATH);
		if (!file.exists()) {
			file.mkdirs();
		}

		String packagePath = PACKAGE_NAME.replace(".", "\\");
		adapterDir = new File(PROJECT_PATH + "\\src\\" + packagePath
				+ "\\adapter");
		if (!adapterDir.exists()) {
			adapterDir.mkdirs();
		}
	}

	private String buildWidgetIds(String filePath) {
		final String startPrefix = "android:id=\"@+id/";
		WIDGETS = "";
		try {
			BufferedReader br = new BufferedReader(new FileReader(filePath));
			try {
				StringBuilder sb = new StringBuilder();
				String line = br.readLine();

				while (line != null) {
					if (line.contains(startPrefix)) {
						WIDGETS += ","
								+ line.substring(line.indexOf("/") + 1,
										line.length() - 1);
					}
					sb.append(line);
					sb.append(System.lineSeparator());
					line = br.readLine();
				}
			} finally {
				br.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (WIDGETS.startsWith(",")) {
			WIDGETS = WIDGETS.substring(1);
		}

		String ret = "";
		probeDataClasses();
		ret += generateClassStart();
		ret += generateDiclaration();
		ret += generateViewFinder(false);
		ret += generateClassEnd();
		return ret;
	}

	private String generateClassStart() {
		int endIndex = XML_FILE_NAME.lastIndexOf("_");
		endIndex = -1 == endIndex ? XML_FILE_NAME.length() : endIndex;
		className = XML_FILE_NAME.substring(0, endIndex);
		className = formatVar(className);
		className = upperCase(className, 0) + "ViewHolder";
		classVarName = "m" + className;
		return ("public class " + className + " {");
	}

	private String generateDiclaration() {
		String ret = "";
		for (int i = 0; i < varClasses.size(); i++) {
			String varClass = varClasses.get(i);
			ret += "\n";
			ret += ("public " + varClass + " m" + formatVar(varNames.get(i)) + ";");
		}
		return ret;
	}

	private String generateClassEnd() {
		return ("\n}");
	}

	private String generateViewFinder(boolean withListener) {
		String ret = "";
		ret += "\n";
		if (withListener) {
			ret += ("\npublic " + className + "(View view, OnClickListener listener){");
		} else {
			ret += ("\npublic " + className + "(View view){");
		}
		for (int i = 0; i < varClasses.size(); i++) {
			ret += "\n";
			ret += ("m" + formatVar(varNames.get(i)) + " = ("
					+ varClasses.get(i) + ") view.findViewById(R.id."
					+ varNames.get(i) + ");");
		}
		if (withListener) {
			ret += "\n";
			ret += "\n";
			for (int i = 0; i < varClasses.size(); i++) {
				ret += ("m" + formatVar(varNames.get(i)) + ".setOnClickListener(listener);");
				ret += "\n";
			}
		}
		ret += "\n";
		ret += ("}");
		return ret;
	}

	private boolean probeDataClasses() {
		varNames.clear();
		varClasses.clear();
		if (null != WIDGETS) {
			String[] widgets = WIDGETS.split("\\,");
			if (null != widgets && widgets.length > 0) {
				for (int i = 0; i < widgets.length; i++) {
					String varName = widgets[i];
					String[] var = varName.split("\\_");
					if (null != var && var.length >= 2) {
						String varClass = var[var.length - 1].trim();
						if ("tv".equalsIgnoreCase(varClass)) {
							varClass = "TextView";
						} else if ("btn".equalsIgnoreCase(varClass)) {
							varClass = "Button";
						} else if ("tb".equalsIgnoreCase(varClass)) {
							varClass = "ToggleButton";
						} else if ("cb".equalsIgnoreCase(varClass)) {
							varClass = "CheckBox";
						} else if ("rb".equalsIgnoreCase(varClass)) {
							varClass = "RadioButton";
						} else if ("ctv".equalsIgnoreCase(varClass)) {
							varClass = "CheckedTextView";
						} else if ("spnr".equalsIgnoreCase(varClass)) {
							varClass = "Spinner";
						} else if ("pb".equalsIgnoreCase(varClass)) {
							varClass = "ProgressBar";
						} else if ("sb".equalsIgnoreCase(varClass)) {
							varClass = "SeekBar";
						} else if ("rg".equalsIgnoreCase(varClass)) {
							varClass = "RadioGroup";
						} else if ("switch".equalsIgnoreCase(varClass)) {
							varClass = "Switch";
						} else if ("et".equalsIgnoreCase(varClass)) {
							varClass = "EditText";
						} else if ("gl".equalsIgnoreCase(varClass)) {
							varClass = "GridLayout";
						} else if ("ll".equalsIgnoreCase(varClass)) {
							varClass = "LinearLayout";
						} else if ("rl".equalsIgnoreCase(varClass)) {
							varClass = "RelativeLayout";
						} else if ("fl".equalsIgnoreCase(varClass)) {
							varClass = "FrameLayout";
						} else if ("tl".equalsIgnoreCase(varClass)) {
							varClass = "TableLayout";
						} else if ("lv".equalsIgnoreCase(varClass)) {
							varClass = "ListView";
						} else if ("gv".equalsIgnoreCase(varClass)) {
							varClass = "GridView";
						} else if ("elv".equalsIgnoreCase(varClass)) {
							varClass = "ExpandableListView";
						} else if ("hsv".equalsIgnoreCase(varClass)) {
							varClass = "HorizontalScrollView";
						} else if ("sv".equalsIgnoreCase(varClass)) {
							varClass = "ScrollView";
						} else if ("wv".equalsIgnoreCase(varClass)) {
							varClass = "WebView";
						} else if ("iv".equalsIgnoreCase(varClass)) {
							varClass = "ImageView";
						} else if ("ib".equalsIgnoreCase(varClass)) {
							varClass = "ImageButton";
						} else if ("glry".equalsIgnoreCase(varClass)) {
							varClass = "Gallery";
						} else if ("vv".equalsIgnoreCase(varClass)) {
							varClass = "VideoView";
						} else if ("tp".equalsIgnoreCase(varClass)) {
							varClass = "TimePicker";
						} else if ("dp".equalsIgnoreCase(varClass)) {
							varClass = "DatePicker";
						}
						varNames.add(varName);
						varClasses.add(varClass);
					}
				}
				return true;
			}
		}
		return false;
	}

	public void writeAdapterFile(String viewHolderData, String xmlName) {
		String javaClassName = className.replaceAll("ViewHolder", "Adapter");
		xmlName = xmlName.replace(".xml", "");

		// package name
		String data = "package " + PACKAGE_NAME + ".adapter;";
		// class declaration
		data += "\n\npublic class " + javaClassName + " extends BaseAdapter {";
		// class variables
		data += "\nprivate LayoutInflater mLayoutInflater;";
		data += "\nprivate List<Object> mObjects;";
		// consturctors
		data += "\n\npublic "
				+ javaClassName
				+ "(Context context) { "
				+ "\nmLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE); "
				+ "\n}";
		// list setter
		data += "\n\npublic void seList(List<Object> list) { "
				+ "\nthis.mObjects = list; " + "\nnotifyDataSetChanged(); "
				+ "\n}";

		// get count
		data += "\n\n@Override " + "\npublic int getCount() { "
				+ "\nreturn null == mObjects ? 0 : mObjects.size(); " + "\n}";
		// get item
		data += "\n\n@Override " + "\npublic Object getItem(int position) { "
				+ "\nreturn mObjects.get(position); " + "\n}";
		// get item id
		data += "\n\n@Override " + "\npublic long getItemId(int position) { "
				+ "\nreturn position; " + "\n}";
		// getview method
		data += "\n\n@Override "
				+ "\npublic View getView(int position, View convertView, ViewGroup parent) {"
				+ "\n" + className + " viewHolder;"
				+ "\nif (convertView == null) {"
				+ "\nconvertView = mLayoutInflater.inflate(R.layout." + xmlName
				+ ", null);" + "\nviewHolder = new " + className
				+ "(convertView);" + "\nconvertView.setTag(viewHolder);"
				+ "\n} else { " + "\nviewHolder = (" + className
				+ ") convertView.getTag(); " + "\n}"
				+ "\n//set here data to views and handle listeners"
				+ "\nreturn convertView; " + "\n}";

		// view holder class
		data += "\n\n" + viewHolderData;

		// class end
		data += "\n\n}";

		File file = new File(adapterDir, javaClassName + ".java");
		writeToFile(file, data);
	}

	private String formatVar(String unformatted) {
		if (null != unformatted) {
			unformatted = unformatted.trim();
			unformatted = unformatted.replaceAll("\\ ", "");
			if (unformatted.contains("_")) {
				String[] unfArr = unformatted.split("\\_");
				String ret = upperCase(unfArr[0], 0);
				for (int i = 1; i < unfArr.length; i++) {
					ret += upperCase(unfArr[i], 0);
				}
				return ret;
			} else {
				return upperCase(unformatted, 0);
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
					ret = ("" + text.charAt(0)).toUpperCase()
							+ text.substring(1);
				} else if (pos == text.length() - 1) {
					ret = text.substring(0, text.length() - 1)
							+ ("" + text.charAt(text.length() - 1))
									.toUpperCase();
				} else {
					ret = text.substring(0, pos)
							+ ("" + text.charAt(pos)).toUpperCase()
							+ text.substring(pos + 1);
				}
				return ret;
			}
		}
		return text;
	}

	private void writeToFile(File file, String text) {
		if (null != file && null != text) {
			FileOutputStream fop = null;
			try {
				fop = new FileOutputStream(file);

				// if file doesnt exists, then create it
				if (!file.exists()) {
					file.createNewFile();
				}

				// get the content in bytes
				byte[] contentInBytes = text.getBytes();

				fop.write(contentInBytes);
				fop.flush();
				fop.close();

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (fop != null) {
						fop.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
