package br.com.tspaschoal.libraryapi.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {

    private Long id;

    @JsonProperty("titulo")
    @Size(min = 3, max = 255)
    @NotEmpty(message = "titulo não pode ser vazio")
    private String title;

    @JsonProperty("subTitulo")
    @Size(min = 3, max = 255)
    @NotEmpty(message = "subtitulo não pode ser vazio")
    private String subTitle;

    @JsonProperty("autor")
    @Size(min = 3, max = 100)
    @NotEmpty(message = "autor não pode ser vazio")
    private String author;

    @JsonProperty("preço")
    @NotNull(message = "preço não pode ser vazio")
    private BigDecimal price;

    @JsonProperty("totalDePaginas")
    @NotNull(message = "total de paginas não pode ser vazio")
    private Integer pageTotal;

    @JsonProperty("isbn")
    @NotEmpty(message = "isbn não pode ser vazio")
    @Pattern(regexp = "^(?=(?:\\D*\\d){10}(?:(?:\\D*\\d){3})?$)[\\d-]+$", message = "isbn deve conter apenas números e estar no formato '000-00-0000-000-0'")
    private String isbn;

    @JsonProperty("dataDePublicação")
    private String publishDate;

}
