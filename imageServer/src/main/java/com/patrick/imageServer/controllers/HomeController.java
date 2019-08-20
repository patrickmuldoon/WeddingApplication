package com.patrick.imageServer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.patrick.imageServer.beans.Image;
import com.patrick.imageServer.service.ImageServiceImpl;

@RequestMapping("/")
@RestController
public class HomeController {
	
	@Autowired
	private ImageServiceImpl imageService;

	@GetMapping("/images/{id}")
	public @ResponseBody ResponseEntity<Image> getImageById(@PathVariable String id){
		Image image = this.imageService.findById(new Long(id));
		if(image != null)
			return new ResponseEntity<Image>(image, HttpStatus.OK);
		else
			return new ResponseEntity<Image>(HttpStatus.BAD_REQUEST);
	}
}
