package com.patrick.imageServer.beans;

import java.util.List;

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
}
