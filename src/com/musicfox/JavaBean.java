
package com.musicfox;

import java.util.ArrayList;

public class JavaBean {

	private String option;
	private String page_type;
	private int numberItems;
	private ArrayList<Artist> artistsArray = new ArrayList<Artist>();
	private Album album_information = new Album();

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

