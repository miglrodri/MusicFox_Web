package com.musicfox;

public class Track {
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTrackIndex() {
		return trackIndex;
	}
	public void setTrackIndex(String trackIndex) {
		this.trackIndex = trackIndex;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	private String id;
	private String title;
	private String trackIndex;
	private String duration;
}
