package br.com.tspaschoal.libraryapi.controllers;

import br.com.tspaschoal.libraryapi.dtos.BookDTO;
import br.com.tspaschoal.libraryapi.services.BookService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/books")
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
}
