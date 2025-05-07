package br.com.cp2_java.dtos.orders;

import java.util.Set;
import java.util.UUID;

public record UpdatedOrderRequest(
        Set<OrderItemsUpdatedRequest> items
) {
}
