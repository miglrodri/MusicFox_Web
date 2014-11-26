package com.musicfox;

import java.util.HashMap;
import java.util.Map;


public class JavaBean {

	private String option;
	private Map<String, String> resultsMap = new HashMap<String, String>();

	public Map<String, String> getResultsMap() {
		return resultsMap;
	}

	public void setResultsMap(Map<String, String> resultsMap) {
		this.resultsMap = resultsMap;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

}
