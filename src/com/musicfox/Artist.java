package com.musicfox;

import java.util.HashMap;
import java.util.Map;

public class Artist {

	public Map<String, String> getAlbumsMap() {
		return albumsMap;
	}

	public void setAlbumsMap(Map<String, String> albumsMap) {
		this.albumsMap = albumsMap;
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

	public int getVevoViewsLastMonth() {
		return vevoViewsLastMonth;
	}

	public void setVevoViewsLastMonth(int vevoViewsLastMonth) {
		this.vevoViewsLastMonth = vevoViewsLastMonth;
	}

	public int getVevoViewsTotal() {
		return vevoViewsTotal;
	}

	public void setVevoViewsTotal(int vevoViewsTotal) {
		this.vevoViewsTotal = vevoViewsTotal;
	}

	public String getTwitterUrl() {
		return twitterUrl;
	}

	public void setTwitterUrl(String twitterUrl) {
		this.twitterUrl = twitterUrl;
	}

	public int getTwitterFollowers() {
		return twitterFollowers;
	}

	public void setTwitterFollowers(int twitterFollowers) {
		this.twitterFollowers = twitterFollowers;
	}

	public String getFacebookUrl() {
		return facebookUrl;
	}

	public void setFacebookUrl(String facebookUrl) {
		this.facebookUrl = facebookUrl;
	}

	public int getFacebookPeopleTalkingAbout() {
		return facebookPeopleTalkingAbout;
	}

	public void setFacebookPeopleTalkingAbout(int facebookPeopleTalkingAbout) {
		this.facebookPeopleTalkingAbout = facebookPeopleTalkingAbout;
	}

	public int getFacebookLikes() {
		return facebookLikes;
	}

	public void setFacebookLikes(int facebookLikes) {
		this.facebookLikes = facebookLikes;
	}

	public String getLastFMUrl() {
		return lastFMUrl;
	}

	public void setLastFMUrl(String lastFMUrl) {
		this.lastFMUrl = lastFMUrl;
	}

	public int getLastFMListeners() {
		return lastFMListeners;
	}

	public void setLastFMListeners(int lastFMListeners) {
		this.lastFMListeners = lastFMListeners;
	}

	public int getLastFMPlayCount() {
		return lastFMPlayCount;
	}

	public void setLastFMPlayCount(int lastFMPlayCount) {
		this.lastFMPlayCount = lastFMPlayCount;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}



	/**
	 * albumsMap contains info about the artist albums (album_id, album_title)
	 */
	private Map<String, String> albumsMap = new HashMap<String, String>();

	private int id;
	private String name;
	private String mainGenre;
	private String decade;
	private String gender;
	private String vevoUrl;
	private int vevoViewsLastMonth;
	private int vevoViewsTotal;
	private String twitterUrl;
	private int twitterFollowers;
	private String facebookUrl;
	private int facebookPeopleTalkingAbout;
	private int facebookLikes;
	private String lastFMUrl;
	private int lastFMListeners;
	private int lastFMPlayCount;
}