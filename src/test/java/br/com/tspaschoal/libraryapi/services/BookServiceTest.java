package br.com.tspaschoal.libraryapi.services;

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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
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
}
