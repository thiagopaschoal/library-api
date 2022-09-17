package br.com.tspaschoal.libraryapi.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorDTO {
    private String message;
}
