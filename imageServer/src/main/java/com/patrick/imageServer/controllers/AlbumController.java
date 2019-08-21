package com.patrick.imageServer.controllers;

import java.util.List;
import java.util.regex.Pattern;

import javax.validation.Valid;

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

import com.patrick.imageServer.beans.Image;
import com.patrick.imageServer.beans.PhotoAlbum;
import com.patrick.imageServer.beans.PhotoAlbum.PhotoAlbumBuilder;
import com.patrick.imageServer.beans.PhotoAlbumRequest;
import com.patrick.imageServer.service.AlbumService;

@RestController
@RequestMapping("/album")
public class AlbumController {
	
	@Autowired
	private AlbumService albumService;
	
	@GetMapping("/{id}")
	public @ResponseBody ResponseEntity<PhotoAlbum> getAlbumById(@Valid @PathVariable String id){
		//validate id
		if(!Pattern.matches("[0-9]", id))
			return new ResponseEntity<PhotoAlbum>(HttpStatus.BAD_REQUEST);
		PhotoAlbum photoAlbum = albumService.getPhotoAlbumById(id);
		if(photoAlbum == null)
			return new ResponseEntity<PhotoAlbum>(HttpStatus.NO_CONTENT);
		else {
			return new ResponseEntity<PhotoAlbum>(photoAlbum, HttpStatus.OK);
		}
	}
	
	@PostMapping("/create")
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
	
	@PostMapping("/{albumId}/addImage")
	public @ResponseBody ResponseEntity<Void> addImageToPhotoAlbum(@Valid @PathVariable String albumId, Image image){
		if(image == null)
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		PhotoAlbum photoAlbum = albumService.getPhotoAlbumById(albumId);
		if(photoAlbum == null) 
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		albumService.addImageToPhotoAlbum(photoAlbum, image);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
	
	@PostMapping("/{albumId}/addImages")
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
