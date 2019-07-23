package com.example.demo;

import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.dao.BookDaoImpl;
import com.example.demo.models.Book;
import com.example.demo.services.BooksService; 

@SpringBootTest
public class DemoApplicationTests {
// resource https://www.mkyong.com/spring-boot/spring-boot-junit-5-mockito/
@Autowired
BooksService booksService;

@Autowired
BookDaoImpl booksDao;
	@Test
	public void getAllBooks() {
		List<Book> books=new ArrayList<>();
		Book book=new Book();
		book.setIdentifier(2l);
		book.setVersion(0l);
		book.setDescription("string");
		book.setIsbn("string");
		book.setTitle("string");
		books.add(book);
		List<Book> booksActual=new ArrayList();
		booksActual=booksService.getAllBooks();
		assertThat(booksActual,sameBeanAs(books).ignoring("identifier").ignoring("version"));
	}
	
	@Test
	public void getBooksById() {
		Book book=new Book();
		book.setIdentifier(2l);
		book.setVersion(0l);
		book.setDescription("string");
		book.setIsbn("string");
		book.setTitle("string");
		Optional<Book> actualBook=booksDao.findById(3l);
		assertThat(actualBook.get(),sameBeanAs(book).ignoring("identifier").ignoring("version"));
	}

	@Test
	public void getByISBN() {
		Book book=new Book();
		book.setIdentifier(2l);
		book.setVersion(0l);
		book.setDescription("string");
		book.setIsbn("string");
		book.setTitle("string");
		Book actualBook=booksDao.getBookByISBN("string");
		assertThat(actualBook,sameBeanAs(book).ignoring("identifier").ignoring("version"));
	}
}
