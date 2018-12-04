package com.viscus.explore.gen;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//D:\Android Projects\Peat
public class ActivityGen {
    public static final String PROJECT_PATH = "G:\\Android Projects";
    public static final String VIEW_NAMES = "home_activity";
    // com.crown.NavadMap.NavigationPlaces
    //com.crown.peat.Activity
    private String PACKAGE_NAME = "com.displayfort.mvpatel.Screen";
    private File srcDir = null, layoutDir = null;

    public List<String> varNames = new ArrayList<>();
    public List<String> varClasses = new ArrayList<>();

    public static void main(String[] args) {
        ActivityGen generator = new ActivityGen();
        generator.createPaths();
        generator.probeDataClasses();
        generator.generateXMLFiles();
        generator.generateJavaFiles();
        System.out.println("Done, Please check: " + PROJECT_PATH);
    }

    private void createPaths() {
        File file = new File(PROJECT_PATH);
        if (!file.exists()) {
            file.mkdirs();
        }

        layoutDir = new File(PROJECT_PATH + "\\res\\layout");
        if (!layoutDir.exists()) {
            layoutDir.mkdirs();
        }

        String packagePath = PACKAGE_NAME.replace(".", "\\");
        srcDir = new File(PROJECT_PATH + "\\src\\" + packagePath);
        if (!srcDir.exists()) {
            srcDir.mkdirs();
        }
    }

    private boolean probeDataClasses() {
        varNames.clear();
        varClasses.clear();
        if (null != VIEW_NAMES) {
            String[] views = VIEW_NAMES.split("\\,");
            if (null != views && views.length > 0) {
                for (int i = 0; i < views.length; i++) {
                    varNames.add(views[i].trim());
                    varClasses.add(formatVar(views[i].trim()));
                }
                return true;
            }
            System.err.println("varNames=" + varNames);
        }
        return false;
    }

    private void generateXMLFiles() {
        String xmlTxt = "<LinearLayout xmlns:android=\"http://schemas.android.com/apk/res/android\" \n"
                + "android:id=\"@+id/container_ll\" \n"
                + "android:layout_width=\"match_parent\" \n"
                + "android:layout_height=\"match_parent\" \n"
                + " android:orientation=\"vertical\"> \n";
        xmlTxt += "\n<!-- Write your xml here -->\n\n";
        xmlTxt += "</LinearLayout>";

        for (String layout : varNames) {
            File layoutFile = new File(layoutDir.getPath() + "\\" + layout
                    + ".xml");
            if (!layoutFile.exists()) {
                writeToFile(layoutFile, xmlTxt);
            }
        }
    }

    private void generateJavaFiles() {
        for (int i = 0; i < varClasses.size(); i++) {
            String java = varClasses.get(i);
            if (java.endsWith("Row") || java.endsWith("Item")) {
                continue;
            }
            String xml = varNames.get(i);
            boolean isFragment = java.endsWith("Fragment");

            String javaTxt = "package " + PACKAGE_NAME + ";\n\n";
            if (isFragment) {
                javaTxt += "public class "
                        + java
                        + " extends android.support.v4.app.Fragment implements OnClickListener {\n\n";
            } else {
                javaTxt += "public class "
                        + java
                        + " extends BaseActivity implements OnClickListener {\n\n";
            }
            // onCreate method
            if (isFragment) {
                javaTxt += "@Override \n"
                        + "public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) { \n"
                        + "View view = inflater.inflate(R.layout." + xml
                        + ", null);\n" + "return view;\n";
            } else {
                javaTxt+="  private Context context;\n";
                javaTxt += "@Override \n"
                        + "public void onCreate(Bundle savedInstanceState) { \n"
                        + "super.onCreate(savedInstanceState);\n"
                        + "setContentView(R.layout." + xml + ");\n"
                        + "context = this;\n";
            }
            javaTxt += "}\n\n";
            // getIntent method
            if (!isFragment) {
                javaTxt += "public static Intent getIntent(Context context) { \n"
                        + "Intent intent = new Intent(context, "
                        + java
                        + ".class); \n" + "return intent; " + "}\n\n";
            }
            // onClick method

            javaTxt += "@Override \n" + "public void onClick(View v) { \n"
                    + "switch (v.getId()) {\n" + "default: \n" + "break; \n"
                    + "} \n" + "}\n\n";
            javaTxt += "}";

            File javaFile = new File(srcDir.getPath() + "\\" + java + ".java");
            if (!javaFile.exists()) {
                writeToFile(javaFile, javaTxt);
            }
        }
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
}
