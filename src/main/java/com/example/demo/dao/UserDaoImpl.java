package com.example.demo.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.example.demo.models.Users;

@Repository
@Transactional
public class UserDaoImpl implements UserDao{

	@PersistenceContext
    private EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Users> getAll() {
		Query query=entityManager.createQuery("SELECT u from Users as u",Users.class);
		return query.getResultList();
	}

	@Override
	public Users getById(Long userId) {
		return entityManager.find(Users.class, userId);
	}

	@Override
	public Users getByUsername(String username) {
		Query query=entityManager.createQuery("SELECT u from Users as u where u.username=:username");
		query.setParameter("username", username);
		return (Users) query.getSingleResult();
	}

	@Override
	public void addUser(Users user) {
		entityManager.persist(user);
	}

	@Override
	public Users updateUser(Users user) {
		return entityManager.merge(user);
	}

}
