package br.com.cp2_java.dtos.books;

import jakarta.validation.constraints.Min;

public record UpdateBookQuantityRequest(
        @Min(1)
        int quantity
) {
}
