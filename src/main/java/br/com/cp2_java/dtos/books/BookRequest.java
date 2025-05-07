package br.com.cp2_java.dtos.books;

import br.com.cp2_java.domainmodel.Book;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;

public record BookRequest(
        @NotBlank(message = "O título é obrigatório")
        @Size(max = 50, message = "O título deve ter no máximo 50 caracteres")
        String title,

        @NotBlank(message = "O autor é obrigatório")
        @Size(max = 100, message = "O autor deve ter no máximo 100 caracteres")
        String author,

        @NotBlank(message = "A editora é obrigatória")
        @Size(max = 100, message = "A editora deve ter no máximo 100 caracteres")
        String publisher,

        @NotNull(message = "A data de publicação é obrigatória")
        @PastOrPresent(message = "A data de publicação não pode estar no futuro")
        LocalDate publicationDate,

        @Min(value = 0, message = "A quantidade não pode ser negativa")
        int quantity,

        @Min(value = 1, message = "A quantidade não pode ser menor que 1")
        BigDecimal price
) {

    public Book toBook(){
        return new Book(null, title, author, publisher, publicationDate, new HashSet<>(), quantity, price);
    }
}
