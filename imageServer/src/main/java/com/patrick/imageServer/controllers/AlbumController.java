package com.patrick.imageServer.controllers;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.patrick.imageServer.beans.Image;
import com.patrick.imageServer.beans.ImageRequest;
import com.patrick.imageServer.beans.PhotoAlbum;
import com.patrick.imageServer.beans.PhotoAlbum.PhotoAlbumBuilder;
import com.patrick.imageServer.beans.PhotoAlbumRequest;
import com.patrick.imageServer.service.AlbumService;

@RestController
@RequestMapping("/album")
public class AlbumController {
	
	@Autowired
	private AlbumService albumService;
	
	private static final Logger log = LoggerFactory.getLogger(AlbumController.class);
	
	@GetMapping("/{id}")
	public @ResponseBody ResponseEntity<PhotoAlbum> getAlbumById(@Valid @PathVariable String id){
		//validate id, fix regex
		if(!Pattern.matches("[0-9]+", id)) {
			System.out.println("It doesn't match");
			return new ResponseEntity<PhotoAlbum>(HttpStatus.BAD_REQUEST);
		}
		PhotoAlbum photoAlbum = albumService.getPhotoAlbumById(id);
		if(photoAlbum == null)
			return new ResponseEntity<PhotoAlbum>(HttpStatus.NO_CONTENT);
		else {
			return new ResponseEntity<PhotoAlbum>(photoAlbum, HttpStatus.OK);
		}
	}
	
	@PostMapping("/admin/create")
	public @ResponseBody ResponseEntity<Void> createNewPhotoAlbum(@Valid @RequestBody PhotoAlbumRequest request ){
		//validate request if need be
		if(request == null)
			return new ResponseEntity<Void>(HttpStatus.NOT_ACCEPTABLE);
		PhotoAlbum photoAlbum = new PhotoAlbumBuilder().with(photoAlbumBuilder -> {
			photoAlbumBuilder.albumName = request.getAlbumName();
			photoAlbumBuilder.description = request.getDescription();
		}).createPhotoAlbum();
		albumService.createPhotoAlbum(photoAlbum);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
	
	//consider adding @Valid to Image and making an ImageRequest
	@PostMapping("/admin/{albumId}/addImage")
	public @ResponseBody ResponseEntity<Void> addImageToPhotoAlbum(@Valid @PathVariable String albumId, ImageRequest image, MultipartFile file){
		if(image == null)
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		PhotoAlbum photoAlbum = albumService.getPhotoAlbumById(albumId);
		if(photoAlbum == null) 
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		Image imageToSave = new Image.ImageBuilder().with(imageBuilder -> {
			try {
				imageBuilder.image = file.getBytes();
			} catch (IOException e) {
				log.error("Image could not be found, or could not be converted properly. ", e);
			}
			imageBuilder.description = image.getDescription();
			imageBuilder.title = image.getTitle();
			imageBuilder.photoAlbum = photoAlbum;
		}).createImage();
		albumService.addImageToPhotoAlbum(photoAlbum, imageToSave);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
	
	//consider add @Valid to images and making an ImageListRequest
	//will need to rewrite to handle a list of MultipartFiles for images as well as json object that map to those files
	@PostMapping("/admin/{albumId}/addImages")
	public @ResponseBody ResponseEntity<Void> addImagesToPhotoAlbum(@Valid @PathVariable String albumId, List<Image> images){
		if(images == null)
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		PhotoAlbum photoAlbum = albumService.getPhotoAlbumById(albumId);
		if(photoAlbum == null) 
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		albumService.addImagesToPhotoAlbum(photoAlbum, images);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
}
