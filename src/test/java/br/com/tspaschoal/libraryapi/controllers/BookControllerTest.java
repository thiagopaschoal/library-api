package br.com.tspaschoal.libraryapi.controllers;

import br.com.tspaschoal.libraryapi.dtos.BookDTO;
import br.com.tspaschoal.libraryapi.exceptions.DataNotFoundException;
import br.com.tspaschoal.libraryapi.services.BookService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import data.BookDataFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest
@AutoConfigureMockMvc
public class BookControllerTest {

    private static final String ENDPOINT = "/api/books";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Test
    @DisplayName("deve cadastrar um livro com sucesso")
    public void testShouldCreateOneBookWithSuccess() throws Exception {

        final var content = BookDataFactory.oneValidBook();
        when(bookService.save(any())).thenReturn(content);
        final var json = new ObjectMapper().writeValueAsString(content);

        final var request = MockMvcRequestBuilders.post(ENDPOINT)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id", equalTo(content.getId().intValue())))
                .andExpect(jsonPath("titulo", equalTo(content.getTitle())))
                .andExpect(jsonPath("subTitulo", equalTo(content.getSubTitle())))
                .andExpect(jsonPath("autor", equalTo(content.getAuthor())))
                .andExpect(jsonPath("preço", equalTo(content.getPrice())))
                .andExpect(jsonPath("totalDePaginas", equalTo(content.getPageTotal())))
                .andExpect(jsonPath("isbn", equalTo(content.getIsbn())))
                .andExpect(jsonPath("dataDePublicação", not(emptyOrNullString())));
    }

    @Test
    @DisplayName("não deve cadastrar um livro quando nenhuma informação for enviada")
    public void testShouldNotCreateBook_WhenDataIsNotProvided() throws Exception {
        final var json = new ObjectMapper().writeValueAsString(BookDTO.builder().build());
        final var request = MockMvcRequestBuilders.post(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);
        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", hasSize(6)));
    }

    @Test
    @DisplayName("não deve cadastrar um livro quando um isbn com formato inválido for enviado")
    public void testShouldNotCreateBook_WhenInvalidIsbnIsProvided() throws Exception {
        final var book = BookDataFactory.oneValidBook();
        book.setIsbn("1234");
        final var json = new ObjectMapper().writeValueAsString(book);
        final var request = MockMvcRequestBuilders.post(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);
        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].message", equalTo("isbn deve estar no formato '000-00-0000-000-0'")));
    }

    @Test
    @DisplayName("deve retornar os detalhes de um livro através do id")
    public void testShouldReturnBookDetailsById() throws Exception {

        final var ID = 1L;
        final var expectedBook = BookDataFactory.oneValidBook();
        when(bookService.findById(anyLong())).thenReturn(expectedBook);

        final var request = MockMvcRequestBuilders.get(String.format("%s/{id}", ENDPOINT), ID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", equalTo(expectedBook.getId().intValue())))
                .andExpect(jsonPath("titulo", equalTo(expectedBook.getTitle())))
                .andExpect(jsonPath("subTitulo", equalTo(expectedBook.getSubTitle())))
                .andExpect(jsonPath("autor", equalTo(expectedBook.getAuthor())))
                .andExpect(jsonPath("preço", equalTo(expectedBook.getPrice())))
                .andExpect(jsonPath("totalDePaginas", equalTo(expectedBook.getPageTotal())))
                .andExpect(jsonPath("isbn", equalTo(expectedBook.getIsbn())))
                .andExpect(jsonPath("dataDePublicação", not(emptyOrNullString())));
    }

    @Test
    @DisplayName("deve lançar erro quanto não existir o livro pesquisado através do id")
    public void testShouldThrowError_WhenBookNotFound() throws Exception {
        final var ID = 9999L;
        when(bookService.findById(anyLong())).thenThrow(new DataNotFoundException("livro não encontrado"));
        final var request = MockMvcRequestBuilders.get(String.format("%s/{id}", ENDPOINT), ID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("message", equalTo("livro não encontrado")));
    }


}
