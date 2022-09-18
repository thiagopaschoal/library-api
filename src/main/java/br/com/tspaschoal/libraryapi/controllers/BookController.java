package br.com.tspaschoal.libraryapi.controllers;

import br.com.tspaschoal.libraryapi.dtos.BookDTO;
import br.com.tspaschoal.libraryapi.services.BookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/books")
@Api(value = "Book API")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping
    @ApiOperation(value = "Create book")
    @ApiResponses({
            @ApiResponse(code = 201, message = "create book with success"),
            @ApiResponse(code = 400, message = "invalid parameters are provided")
    })
    public ResponseEntity<BookDTO> createBook(@RequestBody @Valid BookDTO content) {
        final var savedBook = bookService.save(content);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get book details by id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "book detail"),
            @ApiResponse(code = 404, message = "book not found")
    })
    public ResponseEntity<BookDTO> bookById(@PathVariable Long id) {
        final var book = this.bookService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(book);
    }

    @GetMapping("/isbn/{isbn}")
    @ApiOperation(value = "Get book details by isbn")
    @ApiResponses({
            @ApiResponse(code = 200, message = "book detail"),
            @ApiResponse(code = 404, message = "book not found")
    })
    public ResponseEntity<BookDTO> bookById(@PathVariable String isbn) {
        final var book = this.bookService.findByIsbn(isbn);
        return ResponseEntity.status(HttpStatus.OK).body(book);
    }

    @GetMapping
    @ApiOperation(value = "Get all books")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Get all books")
    })
    public ResponseEntity<List<BookDTO>> books() {
        final var books = bookService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(books);
    }
}
