package org.bangash.cleanLogFiles;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class CleanLogForCK {

	/*static String path = "C:\\Users\\AbdulAli\\Desktop\\Metrics Values\\";

	static String[] sourcePaths = { "QKSMS-2.1.0\\2.1.0", "QKSMS-2.2.0\\2.2.0",
			"QKSMS-2.2.1\\2.2.1", "QKSMS-2.2.2\\2.2.2", "QKSMS-2.2.5\\2.2.5",
			"QKSMS-2.3.1\\2.3.1", "QKSMS-2.4.0\\2.4.0", "QKSMS-2.4.1\\2.4.1",
			"QKSMS-2.5.0\\2.5.0", "QKSMS-2.5.1\\2.5.1", "QKSMS-2.5.2\\2.5.2",
			"QKSMS-2.5.3\\2.5.3", "QKSMS-2.5.4\\2.5.4", "QKSMS-2.5.5\\2.5.5",
			"QKSMS-2.6.0\\2.6.0", "QKSMS-2.6.1\\2.6.1", "QKSMS-2.6.2\\2.6.2",
			"QKSMS-2.6.3\\2.6.3", "QKSMS-2.7.0\\2.7.0", "QKSMS-2.7.1\\2.7.1",
			"QKSMS-2.7.2\\2.7.2", "QKSMS-2.7.3\\2.7.3" };

	static String[] fileNames = { "-tc1.txt", "-tc2.txt", "-tc3.txt",
			"-tc4.txt", "-tc5.txt" };*/
	
	static String path = "C:\\Users\\AbdulAli\\Desktop\\Behe Explorer - Metrics\\";

	static String[] sourcePaths = { "2.0.1\\2.0.1", "2.0.0.1\\2.0.0.1",
			"1.0.0.3\\1.0.0.3", "1.0.0.2\\1.0.0.2", "1.0.0.1a\\1.0.0.1a",
			"1.0.0.1\\1.0.0.1", "1.0.0.0\\1.0.0.0", "1.0.0\\1.0.0"};

	static String[] fileNames = {".txt"};

	public static void main(String[] args) throws IOException {

		for (String folder : sourcePaths) {
			String sourceFilePath = path + folder;
			String destinationFilePath = sourceFilePath;
			for (String filename : fileNames) {
				BufferedReader reader = new BufferedReader(new FileReader(
						sourceFilePath + filename));
				Set<String> lines = new HashSet<String>(10000);
				String line;
				while ((line = reader.readLine()) != null) {
					lines.add(line);
				}
				reader.close();
				BufferedWriter writer = new BufferedWriter(new FileWriter(
						destinationFilePath + "_" + filename));
				for (String unique : lines) {
					writer.write(unique.split(".java")[0]);
					writer.newLine();
				}
				writer.close();
			}
		}
		System.out.println("Unique tracing completed");
	}

}
