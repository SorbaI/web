package java_entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Integer orderId;

    @Column(name = "creation_time", nullable = false)
    private Timestamp creationTime;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "deliv_time", nullable = false)
    private Timestamp delivTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderStatus status;

    @Column(name = "total")
    private Integer total;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToMany
    @JoinTable(
            name = "books_in_order",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private List<Book> books;

    public Order() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(orderId, order.orderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId);
    }
}