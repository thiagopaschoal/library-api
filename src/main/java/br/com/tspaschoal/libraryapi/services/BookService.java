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

    @Autowired
    private ModelMapper modelMapper;

    public BookDTO save(BookDTO bookDTO) {
        final var entity = modelMapper.map(bookDTO, Book.class);
        final var savedBook = this.bookRepository.save(entity);
        return modelMapper.map(savedBook, BookDTO.class);
    }
}
