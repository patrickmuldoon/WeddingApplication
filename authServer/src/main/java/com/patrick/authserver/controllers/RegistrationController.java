package com.patrick.authserver.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.patrick.authserver.auth.beans.RegistrationRequest;
import com.patrick.authserver.auth.beans.RegistrationResponse;
import com.patrick.authserver.auth.beans.Users;
import com.patrick.authserver.service.UsersService;

@RestController
@RequestMapping("/signup")
public class RegistrationController {
	
	private UsersService usersService;
	
	@Autowired
	public RegistrationController(UsersService usersService) {
		this.usersService = usersService;
	}

	@CrossOrigin("*")
	@PostMapping("/registration")
	@ResponseBody
	public ResponseEntity<RegistrationResponse> registerNewUser(@Valid @RequestBody RegistrationRequest registrationRequest){
		//to-do
		if(usersService.isEmailAvailable(registrationRequest.getEmail()) && usersService.isUserNameAvailable(registrationRequest.getUsername())) {
			Users user = new Users(registrationRequest);
			usersService.saveUser(user);
			return new ResponseEntity<RegistrationResponse>(new RegistrationResponse("User Created Successfully"), HttpStatus.CREATED);
		}
		else
			return new ResponseEntity<RegistrationResponse>(
					new RegistrationResponse("Username or Email not available. Please Try again"), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler
    public ResponseEntity<Void> handleException(MethodArgumentNotValidException exception) {
		//log error
		//send exception back to client
		//considering redirecting to an error controller to handle exceptions and send back proper exceptions to client
        return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
    }
}
