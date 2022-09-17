package br.com.tspaschoal.libraryapi.repositories;

import br.com.tspaschoal.libraryapi.entities.Book;
import data.BookDataFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
public class BookRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private BookRepository bookRepository;

    @Test
    @DisplayName("deve cadastrar um livro com sucesso")
    public void testShouldCreateBook() {
        final var book = BookDataFactory.oneEntityBook();
        final var bookSaved = bookRepository.save(book);
        assertThat(bookSaved, equalTo(book));
    }

    @Test
    @DisplayName("deve retornar um livro através do id")
    public void testShouldReturnOneBookById() {
        final var book = BookDataFactory.oneEntityBook();
        book.setId(null);
        testEntityManager.persist(book);
        final var bookById = bookRepository.findById(book.getId());
        assertThat(bookById.get(), equalTo(book));
    }
}
