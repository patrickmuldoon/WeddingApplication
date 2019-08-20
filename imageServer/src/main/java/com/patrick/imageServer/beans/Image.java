package com.patrick.imageServer.beans;

import java.sql.Timestamp;

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
    private long photoID;

	@Column(name="PHOTO")
    @Lob
    private byte[] image;
	
	@Column(name="TITLE")
	private String title;
	
	@Column(name="DATE_UPLOADED")
	private Timestamp timePhotoUploaded;
	
	@ManyToOne(cascade=CascadeType.DETACH, fetch=FetchType.LAZY)
	private PhotoAlbum photoAlbum;

	public long getPhotoID() {
		return photoID;
	}

	public void setPhotoID(long photoID) {
		this.photoID = photoID;
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
}
