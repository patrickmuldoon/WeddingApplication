package com.patrick.imageServer.service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.patrick.imageServer.DAO.AlbumDAO;
import com.patrick.imageServer.beans.Image;
import com.patrick.imageServer.beans.PhotoAlbum;

@Service
public class AlbumServiceImpl implements AlbumService {
	
	@Autowired
	private AlbumDAO albumDAO;
	
	@Autowired
	private ImageService imageService;

	@Override
	public PhotoAlbum getPhotoAlbumById(String id) {
		return albumDAO.findById(new Long(id)).orElse(null);
	}

	@Override
	public void createPhotoAlbum(PhotoAlbum photoAlbum) {
		photoAlbum.setDateCreated(Timestamp.valueOf(LocalDateTime.now()));
		photoAlbum.setLastUpdated(Timestamp.valueOf(LocalDateTime.now()));
		albumDAO.save(photoAlbum);
	}

	@Override
	public void addImageToPhotoAlbum(PhotoAlbum album, Image image) {
		image = imageService.saveImage(image);
		album.getImages().add(image);
		albumDAO.save(album);
	}

	@Override
	public void addImagesToPhotoAlbum(PhotoAlbum album, List<Image> images) {
		//loop through list of images, save image to db, save album to db
		for(Image img : images) {
			//validate image, implement later
			img = imageService.saveImage(img);
			album.addImage(img);
		}
		albumDAO.save(album);
	}

}
