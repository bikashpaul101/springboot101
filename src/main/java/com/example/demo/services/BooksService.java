package com.example.demo.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.BookDaoImpl;
import com.example.demo.models.Book;

@Service
public class BooksService {
	@Autowired
	private BookDaoImpl bookDaoImpl;
	
	public List<Book> getAllBooks(){
		Iterable<Book> books=bookDaoImpl.findAll();
		Iterator itr=books.iterator();
		List<Book> bookList=new ArrayList();
		while(itr.hasNext()) {
			bookList.add((Book) itr.next());
		}
		return bookList;
	}
}
