package com.patrick.imageServer.beans;

import java.sql.Timestamp;
import java.util.List;
import java.util.function.Consumer;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


@Entity
public class PhotoAlbum {

	@Id
    @Column(nullable=false, name="PHOTO_AlBUM_ID")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PHOTO_ALBUM_ID_SEQUENCE")
	@SequenceGenerator(name="PHOTO_ALBUM_ID_SEQUENCE", sequenceName="PHOTO_ALBUM_ID_SEQUENCE")
    private long id;
	
	@OneToMany(mappedBy="photoAlbum", cascade=CascadeType.PERSIST, fetch=FetchType.LAZY)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Image> images;
	
	@Column(nullable=false, name="PHOTO_ALBUM_NAME")
	private String albumName;
	
	@Column(nullable=false, name="DATE_CREATED")
	private Timestamp dateCreated;
	
	@Column(nullable=false, name="LAST_UPDATED")
	private Timestamp lastUpdated;
	
	@Column(nullable=true, name="DESCRIPTION")
	private String description;
	
	public PhotoAlbum(long id, List<Image> images, String albumName, Timestamp dateCreated, Timestamp lastUpdated,
			String description) {
		super();
		this.id = id;
		this.images = images;
		this.albumName = albumName;
		this.dateCreated = dateCreated;
		this.lastUpdated = lastUpdated;
		this.description = description;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<Image> getImages() {
		return images;
	}

	public void setImages(List<Image> images) {
		this.images = images;
	}

	public String getAlbumName() {
		return albumName;
	}

	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}

	public Timestamp getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Timestamp dateCreated) {
		this.dateCreated = dateCreated;
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
	
	public void addImage(Image image) {
		this.getImages().add(image);
	}
	
	public static class PhotoAlbumBuilder {
		
		public long id;
		public List<Image> images;
		public String albumName;
		public Timestamp dateCreated;
		public Timestamp lastUpdated;
		public String description;
		
		public PhotoAlbumBuilder with(Consumer<PhotoAlbumBuilder> builderFunction) {
			builderFunction.accept(this);
			return this;
		}
		
		public PhotoAlbum createPhotoAlbum() {
			return new PhotoAlbum(id, images, albumName, dateCreated, lastUpdated, description);
		}
	}
}
