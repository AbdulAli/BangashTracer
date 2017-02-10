package org.bangash.executionPath.resources;

import java.util.ArrayList;

public class AndroidClass {
	
	public String className;
	
	public ArrayList<String> methodNames = new ArrayList<String>(0);
	
	public void addMethod(String name){
		methodNames.add(name);
	}

}
