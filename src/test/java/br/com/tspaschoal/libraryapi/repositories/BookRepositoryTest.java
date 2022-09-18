package br.com.tspaschoal.libraryapi.repositories;

import data.BookDataFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

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
        book.setId(null);
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

    @Test
    @DisplayName("deve retornar todos os livros cadastrados")
    public void testShouldReturnAllBooks() {

        final var book1 = BookDataFactory.oneEntityBook();
        book1.setId(null);
        final var book2 = BookDataFactory.oneEntityBook();
        book2.setId(null);

        testEntityManager.persist(book1);
        testEntityManager.persist(book2);

        final var books = bookRepository.findAll();
        assertThat(books, hasSize(2));

    }

    @Test
    @DisplayName("deve retornar um livro através do isbn")
    public void testShouldReturnOneBookByIsbn() {
        final var ISBN = "978-85-5519-297-5";
        final var book = BookDataFactory.oneEntityBook();
        book.setId(null);
        testEntityManager.persist(book);
        final var bookByIsbn = bookRepository.findByIsbn(ISBN);
        assertThat(bookByIsbn.get(), equalTo(book));
    }
}
