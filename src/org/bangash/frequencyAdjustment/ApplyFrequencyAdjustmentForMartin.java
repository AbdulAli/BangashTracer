package org.bangash.frequencyAdjustment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.bangash.extractMetricValues.Martin;

public class ApplyFrequencyAdjustmentForMartin {
	
	static String path = "C:\\Users\\AbdulAli\\Desktop\\Metrics Values_CK+Martin+Frequencies\\";

	static String[] sourcePaths = { "2.1.0", "2.2.0", "2.2.1", "2.2.2",
			"2.2.5", "2.3.1", "2.4.0", "2.4.1", "2.5.0", "2.5.1", "2.5.2",
			"2.5.3", "2.5.4", "2.5.5", "2.6.0", "2.6.1", "2.6.2", "2.6.3",
			"2.7.0", "2.7.1", "2.7.2", "2.7.3" };

	static String[] martinMetricsFiles = { "_-tc1_packageMartin.csv", "_-tc2_packageMartin.csv", "_-tc3_packageMartin.csv",
			"_-tc4_packageMartin.csv", "_-tc5_packageMartin.csv" };

	static String[] frequencyFiles = { "-tc1PackageFrequency.csv", "-tc2PackageFrequency.csv", "-tc3PackageFrequency.csv",
			"-tc4PackageFrequency.csv", "-tc5PackageFrequency.csv" };
	
	static String[] tcFileNames = { "t1Martin_FrequencyAdjustment.csv", "tc2Martin_FrequencyAdjustment.csv", "tc3Martin_FrequencyAdjustment.csv",
		"tc4Martin_FrequencyAdjustment.csv", "tc5Martin_FrequencyAdjustment.csv" };

	static ArrayList<Martin> martinMetrics = new ArrayList<Martin>(10000);

	static Map<String, Float> frequencies = new HashMap<String, Float>();
	
	static final int numberOfTestCases = 5;

	public static void main(String[] args) throws IOException {

		//Traversing through directories
		for(String directory: sourcePaths){
			String directoryPath = path+directory;
			//Traverse to read ck metrics
			for(int i=0; i<numberOfTestCases; i++){
				//System.out.println("\n=======================\n===============\n");
				System.out.println("File: path QKSMS-"+directory+ "\\" +martinMetricsFiles[i]);
				readCKMetrics(path+"QKSMS-"+directory,martinMetricsFiles[i]);
				//writeCKMetrics();
				//System.out.println("\n=======================\n===============\n");
				System.out.println("File: path QKSMS-"+directory+ "\\" +frequencyFiles[i]);
				readCKFrequencies(path+"QKSMS-"+directory,directory+frequencyFiles[i]);
				//writeFrequencies();
				applyFrequencyAdjustment(path+"QKSMS-"+directory,tcFileNames[i]);
				martinMetrics = new ArrayList<Martin>(10000);
				frequencies = new HashMap<String, Float>();
			}
		}
	}

	private static void applyFrequencyAdjustment(String directory,
			String fileName) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(directory + "\\" + fileName));
		BufferedWriter writingAverage = new BufferedWriter(new FileWriter(directory + "\\" + (fileName.split(".csv")[0]+".txt")));
		float A_avg = 0,Ca_avg = 0,Ce_avg = 0,D_avg = 0,I_avg = 0;	
		for (Martin martin : martinMetrics) {
			String name = martin.getName().trim();
			Float frequency = frequencies.get(name);
			if(frequency!=null){
			Float A = Float.parseFloat(martin.getA()) * frequency;
			A_avg += A;
			Float Ca = Float.parseFloat(martin.getCa()) * frequency;
			Ca_avg += Ca;
			Float Ce = Float.parseFloat(martin.getCe()) * frequency;
			Ce_avg += Ce;
			Float D = Float.parseFloat(martin.getD()) * frequency;
			D_avg += D;
			Float I = Float.parseFloat(martin.getI()) * frequency;
			I_avg += I;
			writer.write(martin.getName() + "," + A + "," + Ca + "," + Ce
					+ "," + D + "," + I );
			writer.newLine();
			}else{
				System.out.println("\n=== BAD!! ===\n" + directory + "\\" + fileName);
				break;
			}			
		}
		A_avg = A_avg/martinMetrics.size();
		Ca_avg = Ca_avg/martinMetrics.size();
		Ce_avg = Ce_avg/martinMetrics.size();
		D_avg = D_avg/martinMetrics.size();
		I_avg = I_avg/martinMetrics.size();
		
		//writingAverage.write(directory + "\\" + fileName);

		writingAverage.write(A_avg+","+Ca_avg+","+Ce_avg+","+D_avg+","+I_avg);
		writingAverage.close();
		writer.close();
	}

	private static void writeFrequencies() {
		Iterator it = frequencies.entrySet().iterator();
		//BufferedWriter writer = new BufferedWriter(new FileWriter(path + fileName));
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			String name = (String) pair.getKey();
			String frequency = pair.getValue().toString();
			System.out.println(pair.getKey() + " = " + pair.getValue());
			//writer.write(name + "," + frequency);
			//writer.newLine();
			it.remove();
		}
		//writer.close();
	}

	private static void writeCKMetrics() {
		for(Martin martin : martinMetrics){
			System.out.println(martin.getName() + "," + martin.getA() + "," + martin.getCa()  + "," + martin.getCe()  + "," + martin.getD()  + "," + martin.getI() );
		}
	}

	private static void readCKFrequencies(String directoryPath, String frequencyFile) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(directoryPath + "\\" + frequencyFile));
		String line;
		while ((line = reader.readLine()) != null) {
			String[] values = line.split(",");
			frequencies.put(values[0], Float.parseFloat(values[2]));
		}
		reader.close();
	}

	private static void readCKMetrics(String directoryPath, String martinMetricFile) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(directoryPath + "\\" + martinMetricFile));
		String line;
		while ((line = reader.readLine()) != null) {
			String[] values = line.split(",");
			Martin martin = new Martin();
			if(line.contains("n/a")){
				for(int i=0;i<values.length;i++){
					if(values[i].contains("n/a")){
						values[i] = "0";
					}
				}
			}
			martin.setName(values[0]);
			martin.setA(values[1]);
			martin.setCa(values[2]);
			martin.setCe(values[3]);
			martin.setD(values[4]);
			martin.setI(values[5]);
			martinMetrics.add(martin);
		}
		reader.close();
	}
}
