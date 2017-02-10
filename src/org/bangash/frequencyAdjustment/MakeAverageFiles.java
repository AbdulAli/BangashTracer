package org.bangash.frequencyAdjustment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MakeAverageFiles {

	static String path = "C:\\Users\\AbdulAli\\Desktop\\Metrics Values_CK+Martin+Frequencies\\";

	static String[] sourcePaths = { "2.1.0", "2.2.0", "2.2.1", "2.2.2",
			"2.2.5", "2.3.1", "2.4.0", "2.4.1", "2.5.0", "2.5.1", "2.5.2",
			"2.5.3", "2.5.4", "2.5.5", "2.6.0", "2.6.1", "2.6.2", "2.6.3",
			"2.7.0", "2.7.1", "2.7.2", "2.7.3" };

	static String[] destinationCKFiles = { "tc1_CKFrequencyAdjustment.csv",
			"tc2_CKFrequencyAdjustment.csv", "tc3_CKFrequencyAdjustment.csv",
			"tc4_CKFrequencyAdjustment.csv", "tc5_CKFrequencyAdjustment.csv" };
	
	static String[] destinationMartinFiles = { "tc1_MartinFrequencyAdjustment.csv",
		"tc2_MartinFrequencyAdjustment.csv", "tc3_MartinFrequencyAdjustment.csv",
		"tc4_MartinFrequencyAdjustment.csv", "tc5_MartinFrequencyAdjustment.csv" };

	static String[] tcCKFileNames = { "t1CK_FrequencyAdjustment.txt",
			"tc2CK_FrequencyAdjustment.txt", "tc3CK_FrequencyAdjustment.txt",
			"tc4CK_FrequencyAdjustment.txt", "tc5CK_FrequencyAdjustment.txt" };
	
	static String[] tcMartinFileNames = { "t1Martin_FrequencyAdjustment.txt",
		"tc2Martin_FrequencyAdjustment.txt", "tc3Martin_FrequencyAdjustment.txt",
		"tc4Martin_FrequencyAdjustment.txt", "tc5Martin_FrequencyAdjustment.txt" };
	
	static int numberOftestCases = 5;

	public static void main(String[] args) throws IOException {

		for(int i=0; i<numberOftestCases; i++){
			BufferedWriter writer = new BufferedWriter(new FileWriter(path + destinationCKFiles[i]));
			BufferedWriter writer2 = new BufferedWriter(new FileWriter(path + destinationMartinFiles[i]));
			System.out.println("Writing" + path + "\\" + destinationCKFiles[i]);
			for(String version: sourcePaths){
				BufferedReader reader = new BufferedReader(new FileReader(path + "QKSMS-"  + version + "\\" + tcCKFileNames[i]));
				System.out.println("Reading" + path + version + "\\" + tcCKFileNames[i]);
				String line;
				while ((line = reader.readLine()) != null) {
					writer.write(line);
					writer.newLine();
				}
				reader.close();
				
				BufferedReader reader2 = new BufferedReader(new FileReader(path + "QKSMS-"  + version + "\\" + tcMartinFileNames[i]));
				System.out.println("Reading" + path + version + "\\" + tcMartinFileNames[i]);
				line = "";
				while ((line = reader2.readLine()) != null) {
					writer2.write(line);
					writer2.newLine();
				}
				reader2.close();
			}
			writer2.close();
			writer.close();
		}
	}
}
