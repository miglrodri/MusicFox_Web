package com.musicfox;

public class SemanticResult {
	private String resource_name;
	private String resource_url;
	private String resource_type; // artist, album, track
	
	public String getResource_type() {
		return resource_type;
	}
	public void setResource_type(String resource_type) {
		this.resource_type = resource_type;
	}
	public String getResource_name() {
		return resource_name;
	}
	public void setResource_name(String resource_name) {
		this.resource_name = resource_name;
	}
	public String getResource_url() {
		return resource_url;
	}
	public void setResource_url(String resource_url) {
		this.resource_url = resource_url;
	}
	
	
}
