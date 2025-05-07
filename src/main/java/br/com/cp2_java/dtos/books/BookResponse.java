package br.com.cp2_java.dtos.books;

import br.com.cp2_java.domainmodel.Book;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record BookResponse(
        UUID id,
        String title,
        String author,
        String publisher,
        LocalDate publicationDate,
        int quantity,
        BigDecimal price
) {

    public static BookResponse toResponse(Book book) {
        return new BookResponse(book.getId(), book.getTitle(), book.getAuthor(), book.getPublisher(), book.getPublicationDate(), book.getQuantity(), book.getPrice());
    }
}
