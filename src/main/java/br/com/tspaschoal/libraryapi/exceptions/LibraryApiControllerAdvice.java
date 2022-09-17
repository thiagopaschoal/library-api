package br.com.tspaschoal.libraryapi.exceptions;

import br.com.tspaschoal.libraryapi.dtos.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestControllerAdvice
public class LibraryApiControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public List<ErrorDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        final var errors = e.getAllErrors();
        return errors.stream()
                .map(error -> ErrorDTO.builder().message(error.getDefaultMessage()).build())
                .collect(toList());
    }

}
