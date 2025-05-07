package br.com.cp2_java.dtos.books;

import br.com.cp2_java.domainmodel.Book;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public record UpdateBookRequest(
        @NotNull @Size(min = 1, max = 100) String title,
        @NotNull @Size(min = 1, max = 100) String author,
        @NotNull @Size(min = 1, max = 100) String publisher,
        @NotNull LocalDate publicationDate,
        @NotNull int quantity,
        @NotNull BigDecimal price
) {

    public Book toBook(){
        return new Book(null, title, author, publisher, publicationDate, null, quantity, price);
    }
}
