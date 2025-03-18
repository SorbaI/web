package main.java_entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;

import java.util.Objects;

@Entity
@Table(name = "books")
@Getter
@Setter
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "books_book_id_seq") // Изменено!
    @SequenceGenerator(name = "books_book_id_seq", sequenceName = "books_book_id_seq", allocationSize = 1)
    @Column(name = "book_id")
    private Integer bookId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "authors")
    private String authors;

    @Column(name = "genre")
    private String genre;

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
    @JdbcType(PostgreSQLEnumJdbcType.class)
    private TypeCover cover;

    @Column(name = "add_info")
    private String add_info;

    public Book() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(bookId, book.bookId);
    }
}
