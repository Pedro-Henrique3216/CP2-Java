package br.com.cp2_java.dtos.orders;

import br.com.cp2_java.domainmodel.OrderItems;

import java.util.UUID;

public record OrderItemsRequest(
        UUID bookId,
        int quantity
) {
    public OrderItems toOrderItem() {
        OrderItems orderItem = new OrderItems();
        orderItem.setQuantity(this.quantity);
        orderItem.getId().setBookId(bookId);
        return orderItem;
    }
}
