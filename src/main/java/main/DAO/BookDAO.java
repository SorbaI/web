package main.DAO;

import main.java_entities.Book;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookDAO extends GenericDAO<Book,Integer> {
    @Autowired
    private SessionFactory sessionFactory;

    public BookDAO() {
        super(Book.class);
    }

    public List<Book> filterBooks(String genre, String author, Integer minPrice, Integer maxPrice) {
        Session session = getCurrentSession();
        StringBuilder hql = new StringBuilder("FROM Book WHERE TRUE");
        if (genre != null && !genre.isEmpty()) {
            hql.append(" AND genre LIKE:genre");
        }
        if (author != null && !author.isEmpty()) {
            hql.append(" AND authors LIKE:author");
        }
        if (minPrice != null) {
            hql.append(" AND price >= :minPrice");
        }
        if(maxPrice != null) {
            hql.append(" AND price <= :maxPrice");
        }
        Query<Book> query = session.createQuery(hql.toString(), Book.class);

        if (genre != null && !genre.isEmpty()) {
            query.setParameter("genre", "%" + genre + "%");
        }
        if (author != null && !author.isEmpty()) {
            query.setParameter("author", "%" + author + "%");
        }
        if (minPrice != null) {
            query.setParameter("minPrice", minPrice);
        }
        if (maxPrice != null) {
            query.setParameter("maxPrice",maxPrice);
        }

        return query.list();
    }

}