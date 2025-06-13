package ee.mihkel.rendipood.controller;

import ee.mihkel.rendipood.entity.Film;
import ee.mihkel.rendipood.repository.FilmRepository;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class FilmControllerTest {

    private MockMvc mvc;

    @Mock
    FilmRepository filmRepository;

    @InjectMocks
    FilmController filmController;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(filmController).build();
    }

    @Test
    void addFilm() throws Exception {
        String body = "{\"name\": \"Avatar\", \"filmType\": \"REGULAR\"}";
        MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders
                        .post("/films")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andReturn().getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void addFilmThrowsExceptionIfBodyIsEmpty() {
        String body = "{}";
        ServletException exception = assertThrows(ServletException.class, () -> mvc.perform(MockMvcRequestBuilders
                        .post("/films")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andReturn().getResponse());

        assertEquals("Request processing failed: java.lang.RuntimeException: Nimi ei tohi olla puudu", exception.getMessage());
    }

    @Test
    void deleteFilm() throws Exception {
        MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders.delete("/films/3"))
                .andReturn().getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void changeFilmTypeGivesBadRequestIfTypeIsIncorrect() throws Exception {
        MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders.patch("/films?id=1&newType=OLDD"))
                .andReturn().getResponse();
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    void changeFilmType() throws Exception {
        when(filmRepository.findById(1L)).thenReturn(Optional.of(new Film()));
        MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders.patch("/films?id=1&newType=OLD"))
                .andReturn().getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void allFilms() throws Exception {
        MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders.get("/films"))
                .andReturn().getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void availableFilms() throws Exception {
        MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders.get("/available-films"))
                .andReturn().getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }
}