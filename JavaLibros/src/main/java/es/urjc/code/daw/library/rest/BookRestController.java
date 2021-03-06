package es.urjc.code.daw.library.rest;

import static es.urjc.code.daw.library.rest.BookRestController.API_PATH;

import java.util.Collection;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import es.urjc.code.daw.library.book.Book;
import es.urjc.code.daw.library.book.BookService;

@Slf4j
@RestController
@RequestMapping(API_PATH)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BookRestController {

	static final String API_PATH = "/api/books";
	static final String HOME_PATH = "/";

	private final BookService service;

	@GetMapping(HOME_PATH)
	public Collection<Book> getBooks() {
		return service.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Book> getBook(@PathVariable long id) {
		
		Optional<Book> op = service.findOne(id);
		if(op.isPresent()) {
			Book book = op.get();
			return new ResponseEntity<>(book, HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}

	@PostMapping(HOME_PATH)
	@ResponseStatus(HttpStatus.CREATED)
	public Book createBook(@RequestBody Book book) {

		return service.save(book);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Book> updateBook(@PathVariable long id, @RequestBody Book updatedBook) {

		if (service.exist(id)) {
			
			updatedBook.setId(id);
			service.save(updatedBook);

			return new ResponseEntity<>(updatedBook, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Book> deleteBook(@PathVariable long id) {

		try {
			service.delete(id);
			return new ResponseEntity<>(null, HttpStatus.OK);

		} catch (EmptyResultDataAccessException e) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}

}
