package com.patrick.imageServer.beans;

import java.sql.Timestamp;
import java.util.function.Consumer;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity
public class Image {
	
	@Id
    @Column(nullable=false, name="PHOTO_ID")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PHOTO_ID_SEQUENCE")
	@SequenceGenerator(name="PHOTO_ID_SEQUENCE", sequenceName="PHOTO_ID_SEQUENCE")
    private long photoId;

	@Column(name="PHOTO")
    @Lob
    private byte[] image;
	
	@Column(name="TITLE")
	private String title;
	
	@Column(name="DATE_UPLOADED")
	private Timestamp timePhotoUploaded;
	
	@Column(name="LAST_UPDATED")
	private Timestamp lastUpdated;
	
	@Column(name="DESCRIPTION")
	private String description;
	
	@ManyToOne(cascade=CascadeType.DETACH, fetch=FetchType.LAZY)
	private PhotoAlbum photoAlbum;

	public Image(long photoId, byte[] image, String title, Timestamp timePhotoUploaded, Timestamp lastUpdated,
			String description, PhotoAlbum photoAlbum) {
		super();
		this.photoId = photoId;
		this.image = image;
		this.title = title;
		this.timePhotoUploaded = timePhotoUploaded;
		this.lastUpdated = lastUpdated;
		this.description = description;
		this.photoAlbum = photoAlbum;
	}

	public long getPhotoId() {
		return photoId;
	}

	public void setPhotoId(long photoId) {
		this.photoId = photoId;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Timestamp getTimePhotoUploaded() {
		return timePhotoUploaded;
	}

	public void setTimePhotoUploaded(Timestamp timePhotoUploaded) {
		this.timePhotoUploaded = timePhotoUploaded;
	}

	public PhotoAlbum getPhotoAlbum() {
		return photoAlbum;
	}

	public void setPhotoAlbum(PhotoAlbum photoAlbum) {
		this.photoAlbum = photoAlbum;
	}

	public Timestamp getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Timestamp lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	static class ImageBuilder {
		
		public int photoId;
		public byte[] image;
		public String title;
		public Timestamp lastUpdated;
		public Timestamp timePhotoUploaded;
		public String description;
		public PhotoAlbum photoAlbum;
		
		
		public ImageBuilder with(Consumer<ImageBuilder> builderFunction) {
			builderFunction.accept(this);
			return this;
		}
		
		public Image createImage() {
			return new Image(photoId, image, title, timePhotoUploaded, lastUpdated, description, photoAlbum);
		}
		
	}
	
}
