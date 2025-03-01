package java_entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "books")
@Getter
@Setter
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Integer bookId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "authors")
    private String authors;

    @Column(name = "pub_year")
    private Integer pubYear;

    @Column(name = "pub_company")
    private String pubCompany;

    @Column(name = "pages")
    private Integer pages;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "available", nullable = false)
    private Integer available;

    @Enumerated(EnumType.STRING)
    @Column(name = "cover", nullable = false)
    private TypeCover cover;

    @ManyToMany(mappedBy = "books")
    private List<Order> orders;

    public Book() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(bookId, book.bookId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookId);
    }
}
