package es.urjc.code.daw.library.web;

import es.urjc.code.daw.library.book.Book;
import es.urjc.code.daw.library.book.BookService;
import es.urjc.code.daw.library.user.UserComponent;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BookWebController {

	static final String HOME_PATH = "/";

	private final BookService service;
	private final UserComponent userComponent;

	@ModelAttribute
	public void addAttributes(Model model) {

		boolean logged = userComponent.getLoggedUser() != null;

		model.addAttribute("logged", logged);
		model.addAttribute("notLogged", !logged);

		if(logged){
			model.addAttribute("userName",userComponent.getLoggedUser().getName());
			model.addAttribute("admin", userComponent.getLoggedUser().getRoles().contains("ROLE_ADMIN"));
		}
	}

	@GetMapping(HOME_PATH)
	public String showBooks(Model model) {

		model.addAttribute("books", service.findAll());
		
		return "books";
	}

	@GetMapping("/books/{id}")
	public String showBook(Model model, @PathVariable long id) {

		Optional<Book> op = service.findOne(id);
		if(op.isPresent()) {
			Book book = op.get();
			model.addAttribute("book", book);
			return "book";
		}else {
			return "books";
		}

	}

	@GetMapping("/removebook/{id}")
	public String removeBook(Model model, @PathVariable long id) {

		Optional<Book> op = service.findOne(id);
		if(op.isPresent()) {
			Book book = op.get();
			service.delete(id);
			model.addAttribute("book", book);
			return "redirect:/";
		}else {
			return "redirect:/";
		}

	}

	@GetMapping("/newbook")
	public String newBook() {

		return "newBookPage";
	}
	
	@PostMapping("/createbook")
	public String newBookProcess(Book book) {

		Book newBook = service.save(book);

		return "redirect:/books/" + newBook.getId();
	}

	@GetMapping("/editbook/{id}")
	public String editBook(Model model, @PathVariable long id) {

		Optional<Book> op = service.findOne(id);
		if(op.isPresent()) {
			Book book = op.get();
			model.addAttribute("book", book);
			return "editBookPage";
		}else {
			return "books";
		}
		
	}
	
	@PostMapping("/editbook")
	public String editBookProcess(Book book) {
		
		
		service.save(book);
		
		return "bookEdited";
	}


}
