package br.com.cp2_java.controllers;

import br.com.cp2_java.domainmodel.Order;
import br.com.cp2_java.dtos.orders.OrderRequest;
import br.com.cp2_java.dtos.orders.OrderResponse;
import br.com.cp2_java.dtos.orders.UpdatedOrderRequest;
import br.com.cp2_java.exceptions.BookNotFound;
import br.com.cp2_java.exceptions.InsufficientStock;
import br.com.cp2_java.exceptions.ItemNotFound;
import br.com.cp2_java.exceptions.OrderNotFound;
import br.com.cp2_java.service.OrderBookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderBookService orderBookService;

    public OrderController(OrderBookService orderBookService) {
        this.orderBookService = orderBookService;
    }

    @Operation(summary = "Create a new order", description = "Creates a new order and returns the created order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Order created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input or insufficient stock")
    })
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody @Valid OrderRequest request) throws BookNotFound, InsufficientStock {
        Order order = orderBookService.save(request.toOrder());
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(order.getId()).toUri();
        return ResponseEntity.created(uri).body(order);
    }

    @Operation(summary = "Update an existing order", description = "Updates the items in an existing order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order updated successfully"),
            @ApiResponse(responseCode = "404", description = "Order, item, or book not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input or insufficient stock")
    })
    @PutMapping("/{id}")
    public ResponseEntity<OrderResponse> updateOrder(@PathVariable("id") UUID id, @RequestBody @Valid UpdatedOrderRequest request) throws BookNotFound, InsufficientStock, ItemNotFound, OrderNotFound {
        Order order = this.orderBookService.updateOrder(id, request.items());
        return ResponseEntity.ok(OrderResponse.fromOrder(order));
    }

    @Operation(summary = "Get all orders", description = "Returns a list of all orders")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Orders retrieved successfully")
    })
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderBookService.findAll();
        return ResponseEntity.ok(orders);
    }

    @Operation(summary = "Get order by ID", description = "Returns the order details for a given order ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable("id") UUID id) throws OrderNotFound {
        Order order = orderBookService.findById(id);
        return ResponseEntity.ok(order);
    }

    @Operation(summary = "Delete an order", description = "Deletes an order based on the provided order ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable("id") UUID id) {
        orderBookService.delete(id);
        return ResponseEntity.notFound().build();
    }
}
