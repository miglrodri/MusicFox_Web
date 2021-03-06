
package com.musicfox;

import java.util.ArrayList;

public class JavaBean {

	private String option;
	private String page_type;
	private int numberItems;
	private ArrayList<Artist> artistsArray = new ArrayList<Artist>();
	private Album album_information = new Album();
	private Track track_information = new Track();
	private ArrayList<SemanticResult> semanticArray = new ArrayList<SemanticResult>();

	
	
	public Track getTrack_information() {
		return track_information;
	}

	public void setTrack_information(Track track_information) {
		this.track_information = track_information;
	}

	public ArrayList<SemanticResult> getSemanticArray() {
		return semanticArray;
	}

	public void setSemanticArray(ArrayList<SemanticResult> semanticArray) {
		this.semanticArray = semanticArray;
	}
	
	public void addToSemanticArray(SemanticResult semantic_result) {
		this.semanticArray.add(semantic_result);
	}

	public Album getAlbum_information() {
		return album_information;
	}

	public void setAlbum_information(Album album_information) {
		this.album_information = album_information;
	}

	public int getNumberItems() {
		return numberItems;
	}

	public void setNumberItems(int numberItems) {
		this.numberItems = numberItems;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}
	
	public String getPageType() {
		return page_type;
	}

	public void setPageType(String page_type) {
		this.page_type = page_type;
	}

	public ArrayList<Artist> getArtistsArray() {
		return artistsArray;
	}

	public void setArtistsArray(ArrayList<Artist> artistsArray) {
		this.artistsArray = artistsArray;
	}
	
	public void addToArtistsArray(Artist artist) {
		this.artistsArray.add(artist);
	}

}

