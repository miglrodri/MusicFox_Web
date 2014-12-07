
package com.musicfox;

import java.util.ArrayList;

public class Album {

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ArrayList<Track> getTracksArray() {
		return tracksArray;
	}

	public void setTracksMap(ArrayList<Track> tracksArray) {
		this.tracksArray = tracksArray;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	public int getNumberOfTracks() {
		return numberOfTracks;
	}

	public void setNumberOfTracks(int numberOfTracks) {
		this.numberOfTracks = numberOfTracks;
	}

	public String getDecade() {
		return decade;
	}

	public void setDecade(String decade) {
		this.decade = decade;
	}
	
	public void addToTracksArray(Track track) {
		this.tracksArray.add(track);
	}

	/**
	 * tracksMap contains info about the album tracks (track_id, MAP
	 * info_about_track)
	 */
	private ArrayList<Track> tracksArray = new ArrayList<Track>();

	private String id;
	private String title;
	private String releaseDate;
	private int numberOfTracks;
	private String decade;

}
