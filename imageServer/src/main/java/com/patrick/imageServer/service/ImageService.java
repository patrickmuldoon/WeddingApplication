package com.patrick.imageServer.service;

import org.springframework.stereotype.Service;

import com.patrick.imageServer.beans.Image;

@Service
public interface ImageService {

	Long getCount();
	
	Image findById(long id);
	
	void saveImage(Image image);
	
	void deleteById(long id);
	
	void deleteByImage(Image image);
}
