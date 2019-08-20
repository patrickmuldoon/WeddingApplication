package com.patrick.imageServer.DAO;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.patrick.imageServer.beans.Image;

@Repository
public interface ImageDAO extends CrudRepository<Image, Long>{

}
