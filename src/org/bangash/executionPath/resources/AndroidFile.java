package org.bangash.executionPath.resources;

public class AndroidFile {
	
	public AndroidFile(String className){
		AndroidClass class_ = new AndroidClass();
		class_.className = className;
	}
	 
	public String packageName;
	
	public AndroidClass androidClass = new AndroidClass();

}
