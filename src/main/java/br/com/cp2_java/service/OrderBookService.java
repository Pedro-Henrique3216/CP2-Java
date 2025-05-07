package br.com.cp2_java.service;

import br.com.cp2_java.domainmodel.Book;
import br.com.cp2_java.domainmodel.Order;
import br.com.cp2_java.domainmodel.OrderItems;
import br.com.cp2_java.dtos.orders.OrderItemsUpdatedRequest;
import br.com.cp2_java.exceptions.BookNotFound;
import br.com.cp2_java.exceptions.InsufficientStock;
import br.com.cp2_java.exceptions.ItemNotFound;
import br.com.cp2_java.exceptions.OrderNotFound;
import br.com.cp2_java.repository.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class OrderBookService {

    private final OrderRepository orderRepository;

    private final BookService bookService;

    private final OrderItemsService orderItemsService;

    public OrderBookService(OrderRepository orderRepository, BookService bookService, OrderItemsService orderItemsService) {
        this.orderRepository = orderRepository;
        this.bookService = bookService;
        this.orderItemsService = orderItemsService;
    }

    @Transactional
    public Order save(Order order) throws BookNotFound, InsufficientStock {
        for (OrderItems orderItem : order.getOrderItems()) {
            orderItem.setOrder(order);
            orderItem.getId().setOrderId(order.getId());
            Book book = bookService.findById(orderItem.getId().getBookId());
            orderItem.setBook(book);
            orderItem.setValor(book.getPrice().multiply(new BigDecimal(orderItem.getQuantity())));
            bookService.removeBook(book.getId(), orderItem.getQuantity());
            order.setTotal(order.getTotal().add(orderItem.getValor()));
        }
        this.orderRepository.save(order);
        this.orderItemsService.saveOrderItems(order.getOrderItems());
        return order;
    }

    public List<Order> findAll() {
        return this.orderRepository.findAll();
    }

    public Order findById(UUID id) throws OrderNotFound {
        return this.orderRepository.findById(id).orElseThrow(() -> new OrderNotFound("Order not found"));
    }

    public Page<Order> findAll(Pageable pageable) {
        return this.orderRepository.findAll(pageable);
    }

    public void delete(UUID id) {
        this.orderRepository.deleteById(id);
    }

    @Transactional
    public Order updateOrder(UUID orderID, Set<OrderItemsUpdatedRequest> request) throws BookNotFound, InsufficientStock, OrderNotFound, ItemNotFound {
        Order order = findById(orderID);
        for (OrderItemsUpdatedRequest requestItem : request) {

            OrderItems itemToUpdate = order.getOrderItems().stream()
                    .filter(item -> item.getBook().getId().equals(requestItem.bookId()))
                    .findFirst()
                    .orElseThrow(() -> new ItemNotFound("Item not Found in Order"));
            order.setTotal(order.getTotal().subtract(itemToUpdate.getValor()));
            int oldQuantity = itemToUpdate.getQuantity();
            int newQuantity = requestItem.quantity();

            if (newQuantity > oldQuantity) {
                int diff = newQuantity - oldQuantity;
                bookService.removeBook(requestItem.bookId(), diff);
            } else if (newQuantity < oldQuantity) {
                int diff = oldQuantity - newQuantity;
                bookService.addBook(requestItem.bookId(), diff);
            }
            itemToUpdate.setQuantity(newQuantity);
            itemToUpdate.setValor(itemToUpdate.getBook().getPrice().multiply(BigDecimal.valueOf(itemToUpdate.getQuantity())));
            order.setTotal(order.getTotal().add(itemToUpdate.getValor()));

            this.orderItemsService.save(itemToUpdate);
        }
        return this.orderRepository.save(order);
    }

}
