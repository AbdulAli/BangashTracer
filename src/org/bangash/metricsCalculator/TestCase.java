package org.bangash.metricsCalculator;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

public class TestCase {

	@Test
	public void test() {
		BufferedReader br = null;
		try {
			String sCurrentLine;
			br = new BufferedReader(
					new FileReader(
							"C:\\Users\\AbdulAli\\Desktop\\3rdSemester\\Thesis-I\\Metrics Calculation\\QKSMS-2.7.3\\PowerTrace1480164726376.log"));
			boolean flag = false;
			String associatedId = "";
			ArrayList<Integer> powerConsumptions = new ArrayList<Integer>(0);
			int totalTime;
			while ((sCurrentLine = br.readLine()) != null) {
				if (sCurrentLine.contains("associate")
						&& sCurrentLine.contains("QKSMS")) {
					String[] chunks = sCurrentLine.split(" ");
					associatedId = chunks[1];
					System.out.println(associatedId);
					flag = true;
				}
				if (sCurrentLine.contains("CPU-" + associatedId)
						&& flag == true) { // Caculating power consumption per
											// second
					String[] chunks = sCurrentLine.split(" ");
					powerConsumptions.add(Integer.parseInt(chunks[1]));
				}
					
				
			}
			totalTime = powerConsumptions.size();
			int powerChunk = 0;
			ArrayList<Integer> powerChunks = new ArrayList<Integer>(0);

			for(int i=0; i<powerConsumptions.size(); i+=5){
				for(int j=i,k=0; (k<5 && j<powerConsumptions.size()); j++,k++){
					powerChunk+=powerConsumptions.get(j);
				}
				powerChunks.add(powerChunk);
				powerChunk = 0;
			}
			
			
			displayPowerConsumptions(powerConsumptions);
			displayPowerChunks(powerChunks,powerConsumptions.size());
			System.out.println("Total time in seconds: " + totalTime);

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

	private void displayPowerChunks(ArrayList<Integer> powerChunks, int numberOfPowerConsumptions) {
		System.out.println("Displaying power chunks");
		int second = 0;
		int iteration = 0;
		for(float temp: powerChunks){
			second+=5;
			iteration++;
			if(iteration == powerChunks.size()){
				if(numberOfPowerConsumptions%5 != 0){
					int dividend = numberOfPowerConsumptions/5;
					dividend++;
					temp/=dividend;
				}
			}else{
				temp/=5;
			}
			System.out.println(second + " seconds average consumption: " +  temp + " mW");
		}
	}

	private void displayPowerConsumptions(ArrayList<Integer> powerConsumptions) {
		System.out.println("Displaying power consumptions");
		int second = 0;
		for(int temp: powerConsumptions){
			System.out.println(++second + " second: " +  temp + " mW");
		}
		
	}

}
