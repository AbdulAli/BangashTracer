package org.bangash.extractMetricValues;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ExtractMartinMetrics {

	/*static String path = "C:\\Users\\AbdulAli\\Desktop\\Metrics Values\\";

	static String[] sourcePaths = { "2.1.0", "2.2.0", "2.2.1", "2.2.2",
			"2.2.5", "2.3.1", "2.4.0", "2.4.1", "2.5.0", "2.5.1", "2.5.2",
			"2.5.3", "2.5.4", "2.5.5", "2.6.0", "2.6.1", "2.6.2", "2.6.3",
			"2.7.0", "2.7.1", "2.7.2", "2.7.3" };

	static String[] sourceFileNames = { "_-tc1_package.txt", "_-tc2_package.txt", "_-tc3_package.txt",
			"_-tc4_package.txt", "_-tc5_package.txt" };*/
	
	static String path = "C:\\Users\\AbdulAli\\Desktop\\Behe Explorer - Metrics\\";

	static String[] sourcePaths = { "2.0.1", "2.0.0.1",
			"1.0.0.3", "1.0.0.2", "1.0.0.1a",
			"1.0.0.1", "1.0.0.0", "1.0.0"};

	static String[] sourceFileNames = {"_package.txt"};

	static ArrayList<Martin> martinMetrics = new ArrayList<Martin>(0);

	public static void main(String[] args) throws IOException {

		for (String pathTemp : sourcePaths) {
			//String sourceFilePath = path + "QKSMS-" + pathTemp + "\\";
			String sourceFilePath = path + pathTemp + "\\";
			System.out.println(sourceFilePath);
			String metricsCSVFilePath = sourceFilePath;

			for (String filename : sourceFileNames) {
				BufferedReader reader = new BufferedReader(new FileReader(
						sourceFilePath + pathTemp + filename));
				Set<String> traces = new HashSet<String>(10000);
				String line;
				while ((line = reader.readLine()) != null) {
					traces.add(line);
				}
				reader.close();
				extractMetrics(traces,metricsCSVFilePath);
				traces = new HashSet<String>(10000);
				System.out.println("Printing CK for: " + filename);
				printCkMetrics(sourceFilePath,filename);
				martinMetrics = new ArrayList<Martin>(0);
			}
		}
	}

	private static void extractMetrics(Set<String> traces, String metricsCSVFilePath) throws IOException {
		
		for(String trace: traces){
			BufferedReader reader = new BufferedReader(new FileReader(metricsCSVFilePath+"Martin.csv"));
			String line;
			while ((line = reader.readLine()) != null) {
				if(line.contains(trace)){
					parseLineforCkCsv(line);
					break;
				}
			}
			reader.close();
		}
	}


	private static void parseLineforCkCsv(String line) {
		String tokens[] = line.split(",");
		Martin martin = new Martin();
		martin.setName(tokens[0]);
		martin.setA(tokens[1]);
		martin.setCa(tokens[2]);
		martin.setCe(tokens[3]);
		martin.setD(tokens[4]);
		martin.setI(tokens[5]);
		martinMetrics.add(martin);
	}

	public static void printCkMetrics(String path, String fileName) throws IOException {
		//System.out.println(path+fileName.split(".txt")[0]+"CK.csv");
		//BufferedWriter writer = new BufferedWriter(new FileWriter(path+fileName.split(".txt")[0]+"Martin.csv"));
		System.out.println(path+"Martin.csv");
		BufferedWriter writer = new BufferedWriter(new FileWriter(path+"UniqueMartin.csv"));
		for (Martin m : martinMetrics) {
			writer.write(m.getName()+","+m.getA()+","+m.getCa()+","+m.getCe()+","+m.getD()+","+m.getI());
			writer.newLine();
		}
		writer.close();
		System.out.println("Unique tracing completed");
	}
	
}
