package org.bangash.executionPath;

import java.io.File;

import org.bangash.executionPath.resources.AndroidFile;

public class ExecutionPath {

	public static Resource resource;

	public static AndroidFile androidFile;

	public static void main(String[] args) {

		String projectPath = "C:\\Users\\AbdulAli\\Downloads\\behe-explorer-2.0.1\\behe-explorer-2.0.1\\app\\src";
		
		final File folder = new File(projectPath);

		Util utility = new Util();
		
		utility.runExecutionPathLogger(folder, resource, androidFile);
		
		System.out.println("======== Execution complete ==========");
	}
}
