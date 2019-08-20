package com.patrick.authserver.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.patrick.authserver.auth.beans.UsersProfile;

@Repository
public interface UsersProfileRepository extends CrudRepository<UsersProfile, Long>{

}
