package DAO;

import java_entities.Order;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java_entities.Book;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class BookDAO extends GenericDAO<Book,Integer> {
    @Autowired
    private SessionFactory sessionFactory;

    public BookDAO() {
        super(Book.class);
    }

    @Transactional
    public List<Book> findBooksByOrder(Order order) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "SELECT b FROM Order o JOIN o.books b WHERE o = :order";
        Query<Book> query = session.createQuery(hql, Book.class);
        query.setParameter("order", order);
        return query.list();
    }
}