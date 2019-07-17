package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.example.demo.dao.UserDao;
import com.example.demo.models.Users;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/users")
public class UsersResource {

	@Autowired
	private UserDao userDao;
	
	@RequestMapping(path = "/add", method = RequestMethod.POST)
	public ResponseEntity<Users> save(WebRequest request,@RequestBody Users user) {
		userDao.addUser(user);
		return ResponseEntity.ok().build();
	}
	
	@RequestMapping(path = "/getByUsername/{username}", method = RequestMethod.GET)
	public ResponseEntity<Users> getByUsername(@PathVariable("username") String username) {
		Users user=userDao.getByUsername(username);
		if(user!=null) {
			return ResponseEntity.ok().body(user);
		}
		else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@RequestMapping(path = "/getAllUsers", method = RequestMethod.GET)
	public ResponseEntity<List<Users>> getAllUsers() {
		List<Users> users=userDao.getAll();
		if(users!=null) {
			return ResponseEntity.ok().body(users);
		}
		else {
			return ResponseEntity.noContent().build();
		}
	}
	
	@RequestMapping(path = "/update", method = RequestMethod.POST)
	public ResponseEntity<Users> update(@RequestBody Users user) {
		return ResponseEntity.ok().body(userDao.updateUser(user));
	}
}
