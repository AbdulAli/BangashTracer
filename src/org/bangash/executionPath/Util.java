package org.bangash.executionPath;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.bangash.executionPath.resources.AndroidFile;

public class Util {
	
	public Resource resource;
	
	public AndroidFile androidFile;
	
	public void runExecutionPathLogger(File folder, Resource r, AndroidFile aFile) {
		resource = r;
		androidFile = aFile;
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				runExecutionPathLogger(fileEntry,r,aFile);
			} else {
				if (fileEntry.getName().contains(".java")
						&& !fileEntry.getName().contains("$")
						&& !fileEntry.getName().contains("BuildConfig")
						&& !fileEntry.getName()
								.contains("IInAppBillingService")
						&& !fileEntry.getName().contains("Logger.java")) {
					generateLogFiles(fileEntry);
				}
			}
		}
	}

	private void generateLogFiles(File fileEntry) {
		androidFile = new AndroidFile(fileEntry.getName());

		//if (fileEntry.getName().equalsIgnoreCase("ConversationLegacy.java")) {
			//extractMethods(fileEntry);
			BufferedReader br = null;
			try {
				String oldFileLine;
				String newFileLine = "//File appended by Bangash Logger\n";
				System.out.println("\n\n\n=========="+fileEntry.getName()+"==========");
				boolean importLineAdded = false;
				br = new BufferedReader(new FileReader(fileEntry.getPath()));
				while ((oldFileLine = br.readLine()) != null) {
					if (!importLineAdded && oldFileLine.contains("package")) {
						androidFile.packageName = oldFileLine;
						newFileLine += "\n" + oldFileLine;
						newFileLine += "\nimport Logger.Logger;\n";
						importLineAdded = true;
						continue;
					}
					newFileLine += "\n" + oldFileLine;
					if (isMethod(oldFileLine)) {
						String loggerLine = "\n\tLogger.appendLog(\"" +  androidFile.packageName.split(" ")[1].split(";")[0] + "." + fileEntry.getName() + "\");\n";
						do {
							oldFileLine = br.readLine();
							if(oldFileLine.length() > 1
									&& oldFileLine.trim().startsWith("//")){
								newFileLine += "\n" + oldFileLine;
							} else if (oldFileLine.length() > 1
									&& oldFileLine.contains("super")
									&& !oldFileLine.contains("return")) {
								newFileLine += "\n" + oldFileLine + loggerLine;
							} else if (oldFileLine.length() > 1
									&& oldFileLine.contains("this")
									&& !oldFileLine.contains("return")) {
								newFileLine += "\n" + oldFileLine + loggerLine;
							} else if(oldFileLine.contains("@Override")){
								newFileLine += "\n" + oldFileLine;
								break;
							} else{
								newFileLine += loggerLine + oldFileLine;
								break;
							}
						} while (oldFileLine.length() < 1 || oldFileLine.contains("//"));
					}
				}
				  System.out.println("Writing file now..."); 
				  BufferedWriter bw = new BufferedWriter(new FileWriter( fileEntry.getPath()));
				  bw.write(newFileLine); bw.close();
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
	//}
	
	
	private boolean isMethod(String oldFileLine) {
				if(oldFileLine.contains("(") && oldFileLine.contains(")") && oldFileLine.contains("{")
						&& !oldFileLine.contains("//")
						&& !oldFileLine.contains("Runnable()")
						&& !oldFileLine.contains("REGEX")
						&& !oldFileLine.contains("new ")
						&& !oldFileLine.contains("}"))	{
				
					String[] chunks = oldFileLine.split("\\)");
					String[] temp = chunks[0].split("\\(");
					
					if(temp.length<2){
						return true;
					}
					String paramsLine = temp[1];
					if(!paramsLine.contains(",") && paramsLine.matches("[a-zA-Z]* [a-zA-Z]*")){
						return true;
					}else if(paramsLine.split(",")[0].matches("[a-zA-Z]* [a-zA-Z]*")){
						return true;
					} 
				}
				return false;
	}

	/*private static void extractMethods(File fileEntry) {
		BufferedReader br = null;
		try {
			String oldFileLine;
			System.out.println("\nDetecting methods ....\n");
			br = new BufferedReader(new FileReader(fileEntry.getPath()));
			
			while ((oldFileLine = br.readLine()) != null) {
				if(oldFileLine.contains("(") && oldFileLine.contains(")") && oldFileLine.contains("{") 
						&& !oldFileLine.contains("//")
						&& !oldFileLine.contains("Runnable()"))	{
				
					String[] chunks = oldFileLine.split("\\)");
					String[] temp = chunks[0].split("\\(");
					
					if(temp.length<2){
						System.out.println(oldFileLine);
						continue;
					}
					String paramsLine = temp[1];
					if(!paramsLine.contains(",") && paramsLine.matches("[a-zA-Z]* [a-zA-Z]*")){
						System.out.println(oldFileLine);continue;
					}else if(paramsLine.split(",")[0].matches("[a-zA-Z]* [a-zA-Z]*")){
						System.out.println(oldFileLine);continue;
					} 
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
	}*/

	/*
	 * private static boolean validFile(File fileEntry) { BufferedReader br =
	 * null; try { String oldFileLine; br = new BufferedReader(new
	 * FileReader(fileEntry.getPath())); while ((oldFileLine = br.readLine()) !=
	 * null) { if(oldFileLine.contains("class") ||
	 * oldFileLine.contains("interface") ){ return true; } }
	 * 
	 * } catch (IOException e) { e.printStackTrace(); } finally { try { if (br
	 * != null) br.close(); } catch (IOException ex) { ex.printStackTrace(); } }
	 * return false; }
	 */

}
