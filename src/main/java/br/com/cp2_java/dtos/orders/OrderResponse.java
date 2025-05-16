package br.com.cp2_java.dtos.orders;

import br.com.cp2_java.domainmodel.Order;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public record OrderResponse(
        UUID id,
        LocalDate orderDate,
        BigDecimal total,
        Set<OrderItemResponse> orderItems
) {
    public static OrderResponse fromResponse(Order order) {
        Set<OrderItemResponse> itemResponses = order.getOrderItems().stream()
                .map(OrderItemResponse::fromOrderItem)
                .collect(Collectors.toSet());
        return new OrderResponse(
                order.getId(),
                order.getOrderDate(),
                order.getTotal(),
                itemResponses
        );
    }
}