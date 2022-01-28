package es.urjc.code.daw.library.web;

import static es.urjc.code.daw.library.web.BookWebController.HOME_PATH;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class BookWebControllerTest {

  private static final String CONTENT_TYPE = "text/html;charset=UTF-8";

  @Autowired
  MockMvc mvc;

  @Test
  @SneakyThrows
  void getSampleTest() {

    mvc.perform(get(HOME_PATH).contentType(APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(CONTENT_TYPE))
    ;
  }
}
