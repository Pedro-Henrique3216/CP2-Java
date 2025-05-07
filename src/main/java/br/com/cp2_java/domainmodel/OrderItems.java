package br.com.cp2_java.domainmodel;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;

@Table(name = "order-items")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderItems {

    @EmbeddedId
    private OrderItemsPk id;

    @Column(length = 30, nullable = false)
    private int quantity;

    @ManyToOne
    @MapsId("orderId")
    private Order order;

    @ManyToOne
    @MapsId("bookId")
    private Book book;

    @Column(nullable = false)
    private BigDecimal valor;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderItems that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
