package br.com.cp2_java.dtos.orders;


import br.com.cp2_java.domainmodel.Order;
import br.com.cp2_java.domainmodel.OrderItems;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

public record OrderRequest(
        Set<OrderItemsRequest> orderItems,
        LocalDate orderDate
) {
    public Order toOrder() {
        Order order = new Order();
        order.setOrderDate(this.orderDate != null ? this.orderDate : LocalDate.now());
        for (OrderItemsRequest itemRequest : orderItems) {
            OrderItems orderItem = itemRequest.toOrderItem();
            order.getOrderItems().add(orderItem);
        }
        return order;
    }
}

