package br.com.cp2_java.service;

import br.com.cp2_java.domainmodel.OrderItems;
import br.com.cp2_java.repository.OrderItemsRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class OrderItemsService {

    private final OrderItemsRepository orderItemsRepository;

    public OrderItemsService(OrderItemsRepository orderItemsRepository) {
        this.orderItemsRepository = orderItemsRepository;
    }

    public void saveOrderItems(Set<OrderItems> orderItems) {
        this.orderItemsRepository.saveAll(orderItems);
    }

    public void save(OrderItems orderItems) {
        this.orderItemsRepository.save(orderItems);
    }
}
