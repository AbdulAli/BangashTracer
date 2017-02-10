package org.bangash.executionPath;

import java.io.File;

public class StatisticsExtractor {
	
	
	static int totalFiles = 0;
	static int totalValidfiles = 0;

	public static void main(String[] args) {

		String projectPath = "C:\\Casestudy-QKSMS\\qksms-2.7.1\\qksms-2.7.1\\QKSMS\\src\\";
		final File folder = new File(projectPath);
		listFilesForFolder(folder);
		
		System.out.println("Total files:" + totalFiles);
		System.out.println("Total valid files:" + totalValidfiles);
	}


	public static void listFilesForFolder(final File folder) {
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				listFilesForFolder(fileEntry);
			} else {
				if (fileEntry.getName().contains(".java")
						&& !fileEntry.getName().contains("$")
						&& !fileEntry.getName().contains("BuildConfig")
						&& !fileEntry.getName()
								.contains("IInAppBillingService")
						&& !fileEntry.getName().contains("Logger.java")) {
						readFileContents(fileEntry);
					//System.out.println(fileEntry.getName());
					totalFiles++;
				}
			}
		}
	}


	private static void readFileContents(File fileEntry) {
		/*if (validFile(fileEntry)) {
			BufferedReader br = null;
			try {
				String oldFileLine;
				String newFileLine = "";
				br = new BufferedReader(new FileReader(fileEntry.getPath()));
				while ((oldFileLine = br.readLine()) != null) {
					newFileLine.concat(oldFileLine);
				}

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
		}*/
			totalValidfiles++;
		
	}

/*
	private static boolean validFile(File fileEntry) {
		BufferedReader br = null;
		try {
			String oldFileLine;
			br = new BufferedReader(new FileReader(fileEntry.getPath()));
			while ((oldFileLine = br.readLine()) != null) {
				if(oldFileLine.contains("class") || oldFileLine.contains("interface") ){
					return true;
				}
			}

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
		return false;
	}*/

}
