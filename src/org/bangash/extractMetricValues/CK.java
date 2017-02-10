package org.bangash.extractMetricValues;

public class CK {
	
	String name;
	
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	String CBO;
	String DIT;
	String LCOM;
	String NOC;
	String RFC;
	String WMC;
	int frequency = 0;

	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	public String getCBO() {
		return CBO;
	}

	public void setCBO(String cBO) {
		CBO = cBO;
	}

	public String getDIT() {
		return DIT;
	}

	public void setDIT(String dIT) {
		DIT = dIT;
	}

	public String getLCOM() {
		return LCOM;
	}

	public void setLCOM(String lCOM) {
		LCOM = lCOM;
	}

	public String getNOC() {
		return NOC;
	}

	public void setNOC(String nOC) {
		NOC = nOC;
	}

	public String getRFC() {
		return RFC;
	}

	public void setRFC(String rFC) {
		RFC = rFC;
	}

	public String getWMC() {
		return WMC;
	}

	public void setWMC(String wMC) {
		WMC = wMC;
	}
}
