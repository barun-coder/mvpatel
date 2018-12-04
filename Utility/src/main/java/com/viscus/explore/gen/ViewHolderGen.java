package com.viscus.explore.gen;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class ViewHolderGen {
    private static final String LAYOUT_PATH = "G:\\DisplayFort\\androidCode\\JAGUAR APPLICATION\\MVPatel\\app\\src\\main\\res\\layout";
    private static final String XML_FILE_NAME = "fragment_search.xml";

    private static String WIDGETS = "";

    public List<String> varNames = new ArrayList<>();
    public List<String> varClasses = new ArrayList<>();
    public String className, classVarName;

    public static void main(String[] args) {
        ViewHolderGen generator = new ViewHolderGen();
        if (generator.generateWithFile()) {
            generator.buildWidgetIds();
        }
        generator.probeDataClasses();
        generator.generateClassStart();
        generator.generateDiclaration();
        generator.generateViewFinder(true);
        generator.generateClassEnd();
    }

    private boolean generateWithFile() {
        String filePath = LAYOUT_PATH + "\\" + XML_FILE_NAME;
        File file = new File(filePath);
        return file.exists() && file.length() > 0;
    }

    private void buildWidgetIds() {
        final String startPrefix = "android:id=\"@+id/";
        WIDGETS = "";
        String filePath = LAYOUT_PATH + "\\" + XML_FILE_NAME;
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
    }

    private void generateClassStart() {
        int endIndex = XML_FILE_NAME.lastIndexOf("_");
        endIndex = -1 == endIndex ? XML_FILE_NAME.length() : endIndex;
        className = XML_FILE_NAME.substring(0, endIndex);
        className = formatVar(className);
        className = upperCase(className, 0) + "ViewHolder";
        classVarName = "m" + className;
        System.out.println("public class " + className + " {");
    }

    private void generateDiclaration() {
        for (int i = 0; i < varClasses.size(); i++) {
            String varClass = varClasses.get(i);
            System.out.println("public " + varClass + " m"
                    + formatVar(varNames.get(i)) + ";");
        }
    }

    private void generateClassEnd() {
        System.out.println("}");
    }

    private void generateViewFinder(boolean withListener) {
        if (withListener) {
            System.out.println("\npublic " + className
                    + "(View view, View.OnClickListener listener){");
        } else {
            System.out.println("\npublic " + className + "(View view){");
        }
        for (int i = 0; i < varClasses.size(); i++) {
            System.out.println("m" + formatVar(varNames.get(i)) + " = ("
                    + varClasses.get(i) + ") view.findViewById(R.id."
                    + varNames.get(i) + ");");
        }
        if (withListener) {
            System.out.println();
            for (int i = 0; i < varClasses.size(); i++) {
                System.out.println("m" + formatVar(varNames.get(i))
                        + ".setOnClickListener(listener);");
            }
        }
        System.out.println("}");
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
                        } else if ("rv".equalsIgnoreCase(varClass)) {
                            varClass = "RecyclerView";
                        } else if ("sr".equalsIgnoreCase(varClass)) {
                            varClass = "SwipyRefreshLayout";
                        } else if ("vp".equalsIgnoreCase(varClass)) {
                            varClass = "ViewPager";
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

    private String lowerCase(String text, int pos) {
        if (null != text && pos >= 0 && pos < text.length()) {
            text = text.trim();
            if (text.length() == 1) {
                return text.toLowerCase();
            } else {
                String ret = null;
                if (pos == 0) {
                    ret = ("" + text.charAt(0)).toLowerCase()
                            + text.substring(1);
                } else if (pos == text.length() - 1) {
                    ret = text.substring(0, text.length() - 1)
                            + ("" + text.charAt(text.length() - 1))
                            .toLowerCase();
                } else {
                    ret = text.substring(0, pos)
                            + ("" + text.charAt(pos)).toLowerCase()
                            + text.substring(pos + 1);
                }
                return ret;
            }
        }
        return text;
    }
}
