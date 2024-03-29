package com.patrick.imageServer.service;


import java.sql.Timestamp;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.patrick.imageServer.DAO.ImageDAO;
import com.patrick.imageServer.beans.Image;

@Service
public class ImageServiceImpl implements ImageService {

	@Autowired
	private ImageDAO imageDAO;
	
	public void setImageDAO(ImageDAO imageDAO) {
		this.imageDAO = imageDAO;
	}

	public Long getCount() {
		return this.imageDAO.count();
	}

	@Override
	public Image findById(long id) {
		return this.imageDAO.findById(id).orElse(null);
	}

	@Override
	public Image saveImage(Image image) {
		image.setTimePhotoUploaded(Timestamp.from(Instant.now()));
		image.setLastUpdated(Timestamp.from(Instant.now()));
		return this.imageDAO.save(image);
	}

	@Override
	public void deleteById(long id) {
		this.imageDAO.deleteById(id);
	}

	@Override
	public void deleteByImage(Image image) {
		this.imageDAO.delete(image);
	}

}
