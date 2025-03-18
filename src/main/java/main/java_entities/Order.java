package main.java_entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcType;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orders_order_id_seq") // Изменено!
    @SequenceGenerator(name = "orders_order_id_seq", sequenceName = "orders_order_id_seq", allocationSize = 1)
    @Column(name = "order_id")
    private Integer orderId;

    @Column(name = "creation_time", nullable = false)
    private LocalDateTime creationTime;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "deliv_time", nullable = false)
    private LocalDateTime delivTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    private OrderStatus status;

    @Column(name = "total")
    private Integer total;

    @ManyToOne
    @JoinColumn(name = "client_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Client client;

    @OneToMany(mappedBy = "order")
    private List<BookOrder> BookOrder;

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