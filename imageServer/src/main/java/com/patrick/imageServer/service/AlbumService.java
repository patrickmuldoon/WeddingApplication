package com.patrick.imageServer.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.patrick.imageServer.beans.Image;
import com.patrick.imageServer.beans.PhotoAlbum;

@Service
public interface AlbumService {

	PhotoAlbum getPhotoAlbumById(String id);
	
	void createPhotoAlbum(PhotoAlbum photoAlbum);
	
	void addImageToPhotoAlbum(PhotoAlbum album, Image image);
	
	void addImagesToPhotoAlbum(PhotoAlbum album, List<Image> images);
	
}
