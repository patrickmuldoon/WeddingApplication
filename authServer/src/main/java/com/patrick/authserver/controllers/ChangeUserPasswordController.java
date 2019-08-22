package com.patrick.authserver.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.patrick.authserver.auth.beans.ChangePasswordRequest;
import com.patrick.authserver.auth.beans.CurrentUser;
import com.patrick.authserver.service.UsersService;

@RestController
@RequestMapping("/user")
public class ChangeUserPasswordController {
	
	@Autowired
	private UsersService usersService;

	@PostMapping("/changePassword")
	public @ResponseBody ResponseEntity<Void> updateUserPassword(@Valid @RequestBody ChangePasswordRequest request, Authentication auth){
		if(request == null)
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		if(auth.isAuthenticated()) {
			CurrentUser user = (CurrentUser) auth.getPrincipal();
			if(usersService.updateUserPassword(user, request))
				return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
			else
				return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
	}
	
}
