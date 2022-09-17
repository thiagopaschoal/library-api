package br.com.tspaschoal.libraryapi.services;

import br.com.tspaschoal.libraryapi.dtos.BookDTO;
import br.com.tspaschoal.libraryapi.entities.Book;
import br.com.tspaschoal.libraryapi.repositories.BookRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public BookDTO save(BookDTO bookDTO) {
        final var entity = new ModelMapper().map(bookDTO, Book.class);
        final var savedBook = this.bookRepository.save(entity);
        return new ModelMapper().map(savedBook, BookDTO.class);
    }
}
