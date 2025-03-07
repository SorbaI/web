package java_entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(name = "books_in_order")
@Getter
@Setter
public class BookOrder {

        @EmbeddedId
        private BookOrderId id = new BookOrderId();

        @ManyToOne
        @MapsId("orderId")
        @JoinColumn(name = "order_id")
        private Order order;

        @ManyToOne
        @MapsId("bookId")
        @JoinColumn(name = "book_id")
        private Book book;


        @Column(name = "num_of_books",nullable = false)
        private Integer num_of_books;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            BookOrder bookOrder = (BookOrder) o;
            return Objects.equals(bookOrder.id,id) &&
                    num_of_books.equals(bookOrder.num_of_books);
    }

}
