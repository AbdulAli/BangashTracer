package org.bangash.metricsCalculator;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class MetricsCalculator {

	static String folderPath = "C:\\Users\\AbdulAli\\Downloads\\behe\\behe\\";
	static String folderName = "2.0.1\\";
	
	static float totalEnergyConsumptionOfAllExecutions = 0;
	static float totalMeanPowerConsumptionOfAllExecutions = 0;
	static int totalExecutions = 0;
	
	public static void main(String[] args) {

		final File folder = new File(folderPath + folderName);
		listFilesForFolder(folder);
		System.out.println("\n=====================\n");
		System.out.println("Total energy consumption:"+totalEnergyConsumptionOfAllExecutions/totalExecutions);
		System.out.println("Total mean power consumption:"+totalMeanPowerConsumptionOfAllExecutions/totalExecutions);
	}
	
	public static void listFilesForFolder(final File folder) {
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				listFilesForFolder(fileEntry);
			} else {
				System.out.println(fileEntry.getName());
				readLogFiles(folderPath + folderName + fileEntry.getName());
				System.out.println("===================================");
			}
		}
	}

	public static void readLogFiles(String path) {
		BufferedReader br = null;
		try {
			String sCurrentLine;
			br = new BufferedReader(
					new FileReader(path));
			boolean flag = false;
			String associatedId = "";
			ArrayList<Float> powerConsumptions = new ArrayList<Float>(0);
			float totalTime;
			while ((sCurrentLine = br.readLine()) != null) {
				if (sCurrentLine.contains("associate")
						&& sCurrentLine.contains("beheexplorer")) {
					String[] chunks = sCurrentLine.split(" ");
					associatedId = chunks[1];
					System.out.println(associatedId);
					flag = true;
				}
				if (sCurrentLine.contains("CPU-" + associatedId)
						&& flag == true) { // Caculating power consumption per
											// second
					String[] chunks = sCurrentLine.split(" ");
					powerConsumptions.add(Float.parseFloat(chunks[1]));
				}
					
				
			}
			totalTime = powerConsumptions.size();
			float powerChunk = 0;
			ArrayList<Float> powerChunks = new ArrayList<Float>(0);

			for(int i=0; i<powerConsumptions.size(); i+=5){
				for(int j=i,k=0; (k<5 && j<powerConsumptions.size()); j++,k++){
					powerChunk+=powerConsumptions.get(j);
				}
				powerChunks.add(powerChunk);
				powerChunk = 0;
			}
			
			
			//displayPowerConsumptions(powerConsumptions);
			displayPowerChunks(powerChunks,powerConsumptions.size());
			System.out.println("Total time in seconds: " + totalTime);
			float totalEnergyConsumption=0;
			
			for(float chunks: powerChunks){
				totalEnergyConsumption+=chunks;
			}
			totalEnergyConsumptionOfAllExecutions+=totalEnergyConsumption;
			totalMeanPowerConsumptionOfAllExecutions+= (totalEnergyConsumption/totalTime);
			totalExecutions++;
			System.out.println("Total energy consumption: " + totalEnergyConsumption + "mW");
			System.out.println("Average power consumption: " + totalEnergyConsumption/totalTime + "mW");

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	private static void displayPowerChunks(ArrayList<Float> powerChunks, float numberOfPowerConsumptions) {
		System.out.println("Displaying power chunks");
		float second = 0;
		float iteration = 0;
		for(float temp: powerChunks){
			second+=5;
			iteration++;
			if(iteration == powerChunks.size()){
				if(numberOfPowerConsumptions%5 != 0){
					float dividend = numberOfPowerConsumptions/5;
					dividend++;
					temp/=dividend;
				}
			}else{
				temp/=5;
			}
			System.out.println(second + " seconds average consumption: " +  temp + " mW");
		}
	}

}
