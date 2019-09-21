package com.patrick.imageServer.beans;

public class ImageRequest {

	private String description;
	
	private String title;

	public ImageRequest(String description, String title) {
		super();
		this.description = description;
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	
}
