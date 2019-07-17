package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.example.demo.dao.BookDaoImpl;
import com.example.demo.models.Book;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/book")
public class BookResource {

	@Autowired
	private BookDaoImpl bookRepository;
	
@RequestMapping(path = "/add", method = RequestMethod.POST)
public ResponseEntity<Book>save(WebRequest request,@RequestBody Book book) {
	bookRepository.save(book);
	return ResponseEntity.ok().eTag("\"" + book.getVersion() + "\"").build();
}

@RequestMapping(path = "/getAll", method = RequestMethod.GET)
public ResponseEntity<List<Book>> getAll() {
	Iterable<Book> books=bookRepository.findAll();
	Iterator itr=books.iterator();
	List<Book> bookList=new ArrayList();
	while(itr.hasNext()) {
		bookList.add((Book) itr.next());
	}
	return new ResponseEntity<List<Book>>(bookList, HttpStatus.OK);
}

@RequestMapping(path = "/get/{id}", method = RequestMethod.GET)
public ResponseEntity<Book> getByIdentifier(@PathVariable("id") Long id) {
	Optional<Book> book=bookRepository.findById(id);
	if(!book.isPresent()) {
		return ResponseEntity.notFound().build();
	}
	else {
		return ResponseEntity.ok().eTag(book.get().getVersion().toString()).body(book.get());
	}
}

@RequestMapping(path = "/getByISBN", method = RequestMethod.POST)
public ResponseEntity<Book> getByISBN(@RequestBody Book bookWithIsbn) {
	Book book=bookRepository.getBookByISBN(bookWithIsbn.getIsbn());
	if(book==null) {
		return ResponseEntity.notFound().build();
	}
	else {
		return ResponseEntity.ok().eTag(book.getVersion().toString()).body(book);
	}
}

@RequestMapping(path = "/update", method = RequestMethod.PUT)
public ResponseEntity<Book> updateBook(WebRequest request,@RequestBody Book book) {
    Optional<Book> book1 = bookRepository.findById(book.getIdentifier());
    if (!book1.isPresent()) {
        return ResponseEntity.notFound().build();	
    }
    String ifMatchValue = request.getHeader("If-Match");
    if (ifMatchValue.isEmpty()) {
        return ResponseEntity.badRequest().build();
    }
    if (!ifMatchValue.equals(book.getVersion().toString())) {
        return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build();
    }
    try {       
        book = bookRepository.save(book);
        return ResponseEntity
                .ok()
                .eTag(book.getVersion().toString())
                .body(book);
    } catch (OptimisticLockingFailureException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
}
}
