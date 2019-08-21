package com.patrick.imageServer.beans;


public class PhotoAlbumRequest {

	private String albumName;
	
	private String description;
	

	public PhotoAlbumRequest(String albumName, String description) {
		super();
		this.albumName = albumName;
		this.description = description;
	}

	public String getAlbumName() {
		return albumName;
	}

	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
