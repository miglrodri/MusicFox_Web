package com.musicfox;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("rawtypes")
public class Album {

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Map<String, Map> getTracksMap() {
		return tracksMap;
	}

	public void setTracksMap(Map<String, Map> tracksMap) {
		this.tracksMap = tracksMap;
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

	/**
	 * tracksMap contains info about the album tracks (track_id, MAP
	 * info_about_track)
	 */
	private Map<String, Map> tracksMap = new HashMap<String, Map>();

	private int id;
	private String title;
	private String releaseDate;
	private int numberOfTracks;
	private String decade;

}
