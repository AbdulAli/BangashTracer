package org.bangash.extractMetricValues;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ExtractCKMetrics {

	/*static String path = "C:\\Users\\AbdulAli\\Desktop\\Metrics Values\\";

	static String[] sourcePaths = { "2.1.0", "2.2.0", "2.2.1", "2.2.2",
			"2.2.5", "2.3.1", "2.4.0", "2.4.1", "2.5.0", "2.5.1", "2.5.2",
			"2.5.3", "2.5.4", "2.5.5", "2.6.0", "2.6.1", "2.6.2", "2.6.3",
			"2.7.0", "2.7.1", "2.7.2", "2.7.3" };

	static String[] sourceFileNames = { "_-tc1.txt", "_-tc2.txt", "_-tc3.txt",
			"_-tc4.txt", "_-tc5.txt" };*/
	
	static String path = "C:\\Users\\AbdulAli\\Desktop\\Behe Explorer - Metrics\\";

	static String[] sourcePaths = { "2.0.1", "2.0.0.1",
			"1.0.0.3", "1.0.0.2", "1.0.0.1a",
			"1.0.0.1", "1.0.0.0", "1.0.0"};

	static String[] sourceFileNames = {"_.txt"};
	
	static ArrayList<CK> ckMetrics = new ArrayList<CK>(0);
	static ArrayList<LOC> locMetrics = new ArrayList<LOC>(0);
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
				ckMetrics = new ArrayList<CK>(0);
			}
		}
	}

	private static void extractMetrics(Set<String> traces, String metricsCSVFilePath) throws IOException {
		
		for(String trace: traces){
			BufferedReader reader = new BufferedReader(new FileReader(metricsCSVFilePath+"CK.csv"));
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
		CK ck = new CK();
		ck.setName(tokens[0]);
		ck.setCBO(tokens[1]);
		ck.setDIT(tokens[2]);
		ck.setLCOM(tokens[3]);
		ck.setNOC(tokens[4]);
		ck.setRFC(tokens[5]);
		ck.setWMC(tokens[6]);
		ckMetrics.add(ck);
	}

	public static void printCkMetrics(String path, String fileName) throws IOException {
		System.out.println(path+fileName.split(".txt")[0]+"CK.csv");
		//BufferedWriter writer = new BufferedWriter(new FileWriter(path+fileName.split(".txt")[0]+"CK.csv"));
		BufferedWriter writer = new BufferedWriter(new FileWriter(path+"CK_Unique.csv"));
		for (CK ck : ckMetrics) {
			writer.write(ck.getName()+","+ck.getCBO()+","+ck.getDIT()+","+ck.getLCOM()+","+ck.getNOC()+","+ck.getRFC()+","+ck.getWMC());
			writer.newLine();
		}
		writer.close();
		System.out.println("Unique tracing completed");
	}
	
}
