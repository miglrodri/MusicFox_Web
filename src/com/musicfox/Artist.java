
package com.musicfox;

import java.util.ArrayList;

public class Artist {

	public ArrayList<Album> getAlbumsArray() {
		return albumsArray;
	}

	public void setAlbumsMap(ArrayList<Album> albumsArray) {
		this.albumsArray = albumsArray;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMainGenre() {
		return mainGenre;
	}

	public void setMainGenre(String mainGenre) {
		this.mainGenre = mainGenre;
	}

	public String getDecade() {
		return decade;
	}

	public void setDecade(String decade) {
		this.decade = decade;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getVevoUrl() {
		return vevoUrl;
	}

	public void setVevoUrl(String vevoUrl) {
		this.vevoUrl = vevoUrl;
	}

	public long getVevoViewsLastMonth() {
		return vevoViewsLastMonth;
	}

	public void setVevoViewsLastMonth(long vevoViewsLastMonth) {
		this.vevoViewsLastMonth = vevoViewsLastMonth;
	}

	public long getVevoViewsTotal() {
		return vevoViewsTotal;
	}

	public void setVevoViewsTotal(long vevoViewsTotal) {
		this.vevoViewsTotal = vevoViewsTotal;
	}

	public String getTwitterUrl() {
		return twitterUrl;
	}

	public void setTwitterUrl(String twitterUrl) {
		this.twitterUrl = twitterUrl;
	}

	public long getTwitterFollowers() {
		return twitterFollowers;
	}

	public void setTwitterFollowers(long twitterFollowers) {
		this.twitterFollowers = twitterFollowers;
	}

	public String getFacebookUrl() {
		return facebookUrl;
	}

	public void setFacebookUrl(String facebookUrl) {
		this.facebookUrl = facebookUrl;
	}

	public long getFacebookPeopleTalkingAbout() {
		return facebookPeopleTalkingAbout;
	}

	public void setFacebookPeopleTalkingAbout(long facebookPeopleTalkingAbout) {
		this.facebookPeopleTalkingAbout = facebookPeopleTalkingAbout;
	}

	public long getFacebookLikes() {
		return facebookLikes;
	}

	public void setFacebookLikes(long facebookLikes) {
		this.facebookLikes = facebookLikes;
	}

	public String getLastFMUrl() {
		return lastFMUrl;
	}

	public void setLastFMUrl(String lastFMUrl) {
		this.lastFMUrl = lastFMUrl;
	}

	public long getLastFMListeners() {
		return lastFMListeners;
	}

	public void setLastFMListeners(long lastFMListeners) {
		this.lastFMListeners = lastFMListeners;
	}

	public long getLastFMPlayCount() {
		return lastFMPlayCount;
	}

	public void setLastFMPlayCount(long lastFMPlayCount) {
		this.lastFMPlayCount = lastFMPlayCount;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public void addToAlbumsArray(Album album){
		albumsArray.add(album);
	}

	public void setCover(String url) {
		setCoverUrl(url);
		
	}

	public String getCoverUrl() {
		return coverUrl;
	}

	public void setCoverUrl(String coverUrl) {
		this.coverUrl = coverUrl;
	}

	/**
	 * albumsMap contains info about the artist albums (album_id, album_title)
	 */
	private ArrayList<Album> albumsArray = new ArrayList<Album>();

	private String id;
	private String name;
	private String mainGenre;
	private String decade;
	private String gender;
	private String vevoUrl;
	private long vevoViewsLastMonth;
	private long vevoViewsTotal;
	private String twitterUrl;
	private long twitterFollowers;
	private String facebookUrl;
	private long facebookPeopleTalkingAbout;
	private long facebookLikes;
	private String lastFMUrl;
	private long lastFMListeners;
	private long lastFMPlayCount;
	private String coverUrl;
}
