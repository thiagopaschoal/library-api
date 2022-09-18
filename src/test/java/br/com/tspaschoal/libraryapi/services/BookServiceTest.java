package br.com.tspaschoal.libraryapi.services;

import br.com.tspaschoal.libraryapi.exceptions.DataNotFoundException;
import br.com.tspaschoal.libraryapi.repositories.BookRepository;
import data.BookDataFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class BookServiceTest {

    @Mock
    private BookRepository repository;

    @Spy
    private ModelMapper modelMapper;

    @InjectMocks
    private BookService bookService;

    @Test
    @DisplayName("deve cadastrar um livro com sucesso")
    public void testShouldCreateBook() {

        final var book = BookDataFactory.oneEntityBook();
        final var expectedBookDTO = BookDataFactory.oneValidBook();
        when(repository.save(any())).thenReturn(book);

        final var response = bookService.save(expectedBookDTO);
        verify(repository, times(1)).save(book);
        assertThat(response, equalTo(expectedBookDTO));
    }

    @Test
    @DisplayName("deve retornar os detalhes de um livro através do id")
    public void testShouldReturnBookDetailById() {
        final var ID = 1L;
        final var book = BookDataFactory.oneEntityBook();
        final var expectedBook = BookDataFactory.oneValidBook();
        when(repository.findById(anyLong())).thenReturn(Optional.of(book));
        final var response = bookService.findById(ID);
        verify(repository, times(1)).findById(ID);
        assertThat(response, equalTo(expectedBook));
    }

    @Test
    @DisplayName("deve lançar erro quanto não existir o livro pesquisado através do id")
    public void testShouldThrowError_WhenBookNotFoundById() {
        final var ID = 9999L;
        when(repository.findById(ID)).thenReturn(Optional.empty());
        final var exception = assertThrows(DataNotFoundException.class, () -> bookService.findById(ID));
        assertThat(exception.getMessage(), equalTo("livro não encontrado"));
        verify(modelMapper, never()).map(any(), any());
    }

    @Test
    @DisplayName("deve retornar os todos os livros")
    public void testShouldReturnAllBooks() {
        final var books = BookDataFactory.allBooksEntity();
        final var expectedBook = BookDataFactory.allBooks();
        when(repository.findAll()).thenReturn(books);
        final var response = bookService.findAll();
        verify(repository, times(1)).findAll();
        assertThat(response, hasSize(3));
        assertThat(response, equalTo(expectedBook));
    }

    @Test
    @DisplayName("deve retornar os detalhes de um livro através do isbn")
    public void testShouldReturnBookDetailByIsbn() {
        final var ISBN = "978-85-5519-297-5";
        final var book = BookDataFactory.oneEntityBook();
        final var expectedBook = BookDataFactory.oneValidBook();
        when(repository.findByIsbn(ISBN)).thenReturn(Optional.of(book));
        final var response = bookService.findByIsbn(ISBN);
        verify(repository, times(1)).findByIsbn(ISBN);
        assertThat(response, equalTo(expectedBook));
    }

    @Test
    @DisplayName("deve lançar erro quanto não existir o livro pesquisado através do isbn")
    public void testShouldThrowError_WhenBookNotFoundByIsbn() {
        final var ISBN = "978-85-5519-2-5";
        when(repository.findByIsbn(ISBN)).thenReturn(Optional.empty());
        final var exception = assertThrows(DataNotFoundException.class, () -> bookService.findByIsbn(ISBN));
        assertThat(exception.getMessage(), equalTo("livro não encontrado"));
        verify(modelMapper, never()).map(any(), any());
    }


}
