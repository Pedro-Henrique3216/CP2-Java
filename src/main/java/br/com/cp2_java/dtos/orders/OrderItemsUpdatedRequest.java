package br.com.cp2_java.dtos.orders;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record OrderItemsUpdatedRequest(
        @NotNull
        UUID bookId,
        @NotNull
        int quantity
) {
}
