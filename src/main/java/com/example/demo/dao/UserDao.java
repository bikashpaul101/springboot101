package com.example.demo.dao;

import java.util.List;

import com.example.demo.models.Users;

public interface UserDao {
	void addUser(Users user);
	List<Users> getAll();
	Users getById(Long userId);
	Users getByUsername(String username);
	Users updateUser(Users user);
}
