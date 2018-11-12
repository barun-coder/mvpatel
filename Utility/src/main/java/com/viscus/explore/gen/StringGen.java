package com.viscus.explore.gen;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class StringGen {
	private static final String LAYOUT_PATH = "C:\\Users\\ramanands\\Desktop";
	private static final String FILE_NAME = "log.txt";

	public static void main(String[] args) {
		StringGen generator = new StringGen();
		if (generator.generateWithFile()) {
			generator.buildWidgetIds();
		}
	}

	private boolean generateWithFile() {
		String filePath = LAYOUT_PATH + "\\" + FILE_NAME;
		File file = new File(filePath);
		return file.exists() && file.length() > 0;
	}

	private void buildWidgetIds() {
		String filePath = LAYOUT_PATH + "\\" + FILE_NAME;
		try {
			BufferedReader br = new BufferedReader(new FileReader(filePath));
			try {
				String line = br.readLine();

				while (line != null) {
					String name = line.substring(0,
							line.length() > 32 ? 32 : line.length()).trim();
					name = name.replaceAll("[ .:-]", "_");
					System.out.println("<string name=\"" + name + "\">" + line
							+ "</string>");
					line = br.readLine();
				}
			} finally {
				br.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
