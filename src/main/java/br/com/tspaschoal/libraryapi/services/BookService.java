package br.com.tspaschoal.libraryapi.services;

import br.com.tspaschoal.libraryapi.dtos.BookDTO;
import br.com.tspaschoal.libraryapi.entities.Book;
import br.com.tspaschoal.libraryapi.exceptions.DataNotFoundException;
import br.com.tspaschoal.libraryapi.repositories.BookRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public BookDTO findById(Long id) {
        final var book = bookRepository
                .findById(id)
                .orElseThrow(() -> new DataNotFoundException("livro não encontrado"));
        return modelMapper.map(book, BookDTO.class);
    }

    public List<BookDTO> findAll() {
        final var books = bookRepository.findAll();
        return modelMapper.map(books, new TypeToken<List<BookDTO>>(){}.getType());
    }

    public BookDTO findByIsbn(String isbn) {
        final var book = bookRepository
                .findByIsbn(isbn)
                .orElseThrow(() -> new DataNotFoundException("livro não encontrado"));
        return modelMapper.map(book, BookDTO.class);
    }
}
