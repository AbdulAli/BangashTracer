package org.bangash.extractMetricValues;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CalculateMartinMetrics {

	/*static String path = "C:\\Users\\AbdulAli\\Desktop\\Metrics Values_CK+Martin+Frequencies\\";

	static String[] sourcePaths = { "2.1.0", "2.2.0", "2.2.1", "2.2.2",
			"2.2.5", "2.3.1", "2.4.0", "2.4.1", "2.5.0", "2.5.1", "2.5.2",
			"2.5.3", "2.5.4", "2.5.5", "2.6.0", "2.6.1", "2.6.2", "2.6.3",
			"2.7.0", "2.7.1", "2.7.2", "2.7.3" };

	static String[] unqiueFiles = { "_-tc1_package.txt", "_-tc2_package.txt", "_-tc3_package.txt",
		"_-tc4_package.txt", "_-tc5_package.txt" };

	static String[] rawFiles = { "-tc1.txt", "-tc2.txt", "-tc3.txt",
			"-tc4.txt", "-tc5.txt" };*/
	
	static String path = "C:\\Users\\AbdulAli\\Desktop\\Behe Explorer - Metrics\\";

	static String[] sourcePaths = { "2.0.1", "2.0.0.1",
			"1.0.0.3", "1.0.0.2", "1.0.0.1a",
			"1.0.0.1", "1.0.0.0", "1.0.0"};

	static String[] unqiueFiles = {"_package.txt"};
	
	static String[] rawFiles = { ".txt" };

	static ArrayList<String> traces = new ArrayList<String>(10000);

	static Map<String, Integer> frequencies = new HashMap<String, Integer>();
	
	//static final int numberOfTestCases = 5;
	static final int numberOfTestCases = 1;

	public static void main(String[] args) throws IOException {

		for (String pathTemp : sourcePaths) {
			String sourceFilePath = path + pathTemp + "\\";
			System.out.println(sourceFilePath);

			for (int i = 0; i < numberOfTestCases; i++) {
				String filename = unqiueFiles[i];
				String rawFile = rawFiles[i];
				BufferedReader reader = new BufferedReader(new FileReader(
						sourceFilePath + pathTemp + filename));

				String line;
				while ((line = reader.readLine()) != null) {
					frequencies.put(line, 0);
				}
				reader.close();
				reader = new BufferedReader(new FileReader(sourceFilePath
						+ pathTemp + rawFile));
				while ((line = reader.readLine()) != null) {
					traces.add(line.split(".\\w*.java")[0]);
				}
				reader.close();
				calculateFrequencies();
				//printFrequencies(sourceFilePath + pathTemp, rawFile.split(".txt")[0] + "PackageFrequency.csv");
				printFrequencies(sourceFilePath + pathTemp, "PackageFrequency.csv");
				frequencies = new HashMap<String, Integer>();
				traces = new ArrayList<String>(10000);
			}
		}
	}

	private static void calculateFrequencies() {
		for (String trace : traces) {
			Integer count = frequencies.get(trace);
			count++;
			frequencies.put(trace, count);
		}
	}

	private static void printFrequencies(String path, String fileName) throws IOException {
		Iterator it = frequencies.entrySet().iterator();
		BufferedWriter writer = new BufferedWriter(new FileWriter(path + fileName));
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			String name = (String) pair.getKey();
			String frequency = pair.getValue().toString();
			System.out.println(pair.getKey() + " = " + pair.getValue());
			writer.write(name + "," + frequency);
			writer.newLine();
			it.remove();
		}
		writer.close();
	}

}
