package data;

import br.com.tspaschoal.libraryapi.dtos.BookDTO;
import br.com.tspaschoal.libraryapi.entities.Book;

import java.math.BigDecimal;

public class BookDataFactory {

    public static BookDTO oneValidBook() {
        final var book = BookDTO.builder()
                .id(1L)
                .title("Roadmap back-end")
                .subTitle("Conhecendo o protocolo HTTP e arquiteturas REST")
                .author("Victor Osório")
                .price(new BigDecimal(79.90))
                .isbn("978-85-5519-297-5")
                .pageTotal(151)
                .publishDate("04/2022")
                .build();
        return book;
    }

    public static Book oneEntityBook() {
        final var book = Book.builder()
                .id(1L)
                .title("Roadmap back-end")
                .subTitle("Conhecendo o protocolo HTTP e arquiteturas REST")
                .author("Victor Osório")
                .price(new BigDecimal(79.90))
                .isbn("978-85-5519-297-5")
                .pageTotal(151)
                .publishDate("04/2022")
                .build();
        return book;
    }
}
