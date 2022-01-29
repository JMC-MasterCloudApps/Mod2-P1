package es.urjc.code.daw.library.rest;

import static es.urjc.code.daw.library.rest.BookRestController.API_PATH;
import static es.urjc.code.daw.library.rest.BookRestController.HOME_PATH;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.github.javafaker.Faker;
import es.urjc.code.daw.library.book.Book;
import es.urjc.code.daw.library.book.BookService;
import java.util.Collections;
import java.util.List;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class BookWebControllerTest {

  @Autowired
  MockMvc mvc;

  @MockBean
  private BookService service;

  private static List<Book> books;

  @BeforeAll
  static void buildBooks() {

    var faker = new Faker();
    var book = new Book(faker.book().title(), faker.company().catchPhrase());
    books = singletonList(book);
  }

  @Test
  @SneakyThrows
  @DisplayName("All books as Guest")
  void allBooksAsGuest() {

    // GIVEN
    var request = get(API_PATH + HOME_PATH).contentType(APPLICATION_JSON);

    // WHEN
    when(service.findAll()).thenReturn(books);
    var response = mvc.perform(request);

    // THEN
    response.andExpectAll(
        status().isOk(),
        content().contentType(APPLICATION_JSON),
        jsonPath("$", hasSize(books.size())),
        jsonPath("$[0].title", equalTo(books.get(0).getTitle())));
  }

  @Test
  @SneakyThrows
  @DisplayName("Create new book as User")
  @WithMockUser(username = "user", password = "pass", roles = "USER")
  void createBookAsUser() {

    // GIVEN
    var request = post(API_PATH + HOME_PATH).contentType(APPLICATION_JSON);

    // WHEN
    when(service.save(books.get(0))).thenReturn(books.get(0));
    var response = mvc.perform(request);

    // THEN
    response.andExpectAll(
        status().isCreated(),
        content().contentType(APPLICATION_JSON),
        jsonPath("$", hasSize(books.size())),
        jsonPath("$[0].title", equalTo(books.get(0).getTitle())));
  }
}
