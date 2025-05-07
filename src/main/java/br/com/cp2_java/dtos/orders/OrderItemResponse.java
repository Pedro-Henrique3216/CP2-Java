package br.com.cp2_java.dtos.orders;

import br.com.cp2_java.domainmodel.OrderItems;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderItemResponse(
        UUID bookId,
        int quantity,
        BigDecimal total
) {
    public static OrderItemResponse fromOrderItem(OrderItems orderItem) {
        return new OrderItemResponse(
                orderItem.getBook().getId(),
                orderItem.getQuantity(),
                orderItem.getValor()
        );
    }
}
