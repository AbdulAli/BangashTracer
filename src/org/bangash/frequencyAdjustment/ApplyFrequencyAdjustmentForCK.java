package org.bangash.frequencyAdjustment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.bangash.extractMetricValues.CK;

public class ApplyFrequencyAdjustmentForCK {
	
	static String path = "C:\\Users\\AbdulAli\\Desktop\\Metrics Values_CK+Martin+Frequencies\\";

	static String[] sourcePaths = { "2.1.0", "2.2.0", "2.2.1", "2.2.2",
			"2.2.5", "2.3.1", "2.4.0", "2.4.1", "2.5.0", "2.5.1", "2.5.2",
			"2.5.3", "2.5.4", "2.5.5", "2.6.0", "2.6.1", "2.6.2", "2.6.3",
			"2.7.0", "2.7.1", "2.7.2", "2.7.3" };

	static String[] ckMetricsFiles = { "_-tc1CK.csv", "_-tc2CK.csv", "_-tc3CK.csv",
			"_-tc4CK.csv", "_-tc5CK.csv" };

	static String[] frequencyFiles = { "-tc1CKFrequency.csv", "-tc2CKFrequency.csv", "-tc3CKFrequency.csv",
			"-tc4CKFrequency.csv", "-tc5CKFrequency.csv" };
	
	static String[] tcFileNames = { "t1CK_FrequencyAdjustment.csv", "tc2CK_FrequencyAdjustment.csv", "tc3CK_FrequencyAdjustment.csv",
		"tc4CK_FrequencyAdjustment.csv", "tc5CK_FrequencyAdjustment.csv" };

	static ArrayList<CK> ckMetrics = new ArrayList<CK>(10000);

	static Map<String, Float> frequencies = new HashMap<String, Float>();
	
	static final int numberOfTestCases = 5;

	public static void main(String[] args) throws IOException {

		//Traversing through directories
		for(String directory: sourcePaths){
			String directoryPath = path+directory;
			//Traverse to read ck metrics
			for(int i=0; i<numberOfTestCases; i++){
				//System.out.println("\n=======================\n===============\n");
				System.out.println("File: path QKSMS-"+directory+ "\\" +ckMetricsFiles[i]);
				readCKMetrics(path+"QKSMS-"+directory,ckMetricsFiles[i]);
				//writeCKMetrics();
				//System.out.println("\n=======================\n===============\n");
				System.out.println("File: path QKSMS-"+directory+ "\\" +frequencyFiles[i]);
				readCKFrequencies(path+"QKSMS-"+directory,directory+frequencyFiles[i]);
				//writeFrequencies();
				applyFrequencyAdjustment(path+"QKSMS-"+directory,tcFileNames[i]);
				ckMetrics = new ArrayList<CK>(10000);
				frequencies = new HashMap<String, Float>();
			}
		}
	}

	private static void applyFrequencyAdjustment(String directory,
			String fileName) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(directory + "\\" + fileName));
		BufferedWriter writingAverage = new BufferedWriter(new FileWriter(directory + "\\" + (fileName.split(".csv")[0]+".txt")));
		float CBO_avg = 0,DIT_avg = 0,LCOM_avg = 0,NOC_avg = 0,RFC_avg = 0,WMC_avg = 0;
		for (CK ck : ckMetrics) {
			Float frequency = frequencies.get(ck.getName());
			Float CBO = Float.parseFloat(ck.getCBO()) * frequency;
			CBO_avg += CBO;
			Float DIT = Float.parseFloat(ck.getDIT()) * frequency;
			DIT_avg += DIT;
			Float LCOM = Float.parseFloat(ck.getLCOM()) * frequency;
			LCOM_avg += LCOM;
			Float NOC = Float.parseFloat(ck.getNOC()) * frequency;
			NOC_avg += NOC;
			Float RFC = Float.parseFloat(ck.getRFC()) * frequency;
			RFC_avg += RFC;
			Float WMC = Float.parseFloat(ck.getWMC()) * frequency;
			WMC_avg += WMC;
			writer.write(ck.getName() + "," + CBO + "," + DIT + "," + LCOM
					+ "," + NOC + "," + RFC + "," + WMC);
			writer.newLine();
		}
		CBO_avg = CBO_avg/ckMetrics.size();
		DIT_avg = DIT_avg/ckMetrics.size();
		LCOM_avg = LCOM_avg/ckMetrics.size();
		NOC_avg = NOC_avg/ckMetrics.size();
		RFC_avg = RFC_avg/ckMetrics.size();
		WMC_avg = WMC_avg/ckMetrics.size();
		
		//writingAverage.write(directory + "\\" + fileName);
		writingAverage.write(CBO_avg+","+DIT_avg+","+LCOM_avg+","+NOC_avg+","+RFC_avg+","+WMC_avg);
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
		for(CK ck : ckMetrics){
			System.out.println(ck.getName() + "," + ck.getCBO() + "," + ck.getDIT()  + "," + ck.getLCOM()  + "," + ck.getNOC()  + "," + ck.getRFC()  + "," + ck.getWMC());
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

	private static void readCKMetrics(String directoryPath, String ckMetricFile) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(directoryPath + "\\" + ckMetricFile));
		String line;
		while ((line = reader.readLine()) != null) {
			String[] values = line.split(",");
			CK ck = new CK();
			if(line.contains("n/a")){
				for(int i=0;i<values.length;i++){
					if(values[i].contains("n/a")){
						values[i] = "0";
					}
				}
			}
			ck.setName(values[0]);
			ck.setCBO(values[1]);
			ck.setDIT(values[2]);
			ck.setLCOM(values[3]);
			ck.setNOC(values[4]);
			ck.setRFC(values[5]);
			ck.setWMC(values[6]);
			ckMetrics.add(ck);
		}
		reader.close();
	}
}
