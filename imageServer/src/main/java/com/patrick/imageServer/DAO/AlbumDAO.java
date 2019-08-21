package com.patrick.imageServer.DAO;

import org.springframework.data.repository.CrudRepository;

import com.patrick.imageServer.beans.PhotoAlbum;

public interface AlbumDAO extends CrudRepository<PhotoAlbum, Long> {

}
