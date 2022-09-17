package br.com.tspaschoal.libraryapi.services;

import br.com.tspaschoal.libraryapi.dtos.BookDTO;
import br.com.tspaschoal.libraryapi.repositories.BookRepository;
import data.BookDataFactory;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class BookServiceTest {

    @Mock
    private BookRepository repository;

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
}
