package br.com.cp2_java.domainmodel;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "books")
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false, unique = true, length = 50)
    private String title;
    @Column(nullable = false, length = 100)
    private String author;
    @Column(nullable = false, length = 100)
    private String publisher;
    @Column(nullable = false)
    private LocalDate publicationDate;
    @OneToMany(mappedBy = "book")
    private Set<OrderItems> orderItems = new HashSet<>();
    @Column(nullable = false)
    private int quantity = 0;
    @Column(nullable = false)
    private BigDecimal price = BigDecimal.ZERO;

    public void addBook(int quantity) {
        this.quantity += quantity;
    }

    public void removeBook(int quantity) {
        this.quantity -= quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book book)) return false;
        return Objects.equals(id, book.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
