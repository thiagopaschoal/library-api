package br.com.tspaschoal.libraryapi.repositories;

import br.com.tspaschoal.libraryapi.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}
