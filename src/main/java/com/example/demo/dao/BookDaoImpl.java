package com.example.demo.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.demo.models.Book;

public interface BookDaoImpl extends CrudRepository<Book, Long> {
	
	@Query("SELECT b from Book b where b.isbn=:isbn")
	Book getBookByISBN(String isbn);
}
