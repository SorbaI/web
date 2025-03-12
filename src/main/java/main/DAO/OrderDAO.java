package main.DAO;

import main.java_entities.Book;
import main.java_entities.BookOrder;
import main.java_entities.BookOrderId;
import main.java_entities.Order;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class OrderDAO extends GenericDAO<Order,Integer> {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    public OrderDAO() {
        super(Order.class);
    }

    public BookOrder getBookOrder (Order order, Book book) {
        Session session = getCurrentSession();

        BookOrderId bookOrderId = new BookOrderId();
        bookOrderId.setOrderId(order.getOrderId());
        bookOrderId.setBookId(book.getBookId());

        BookOrder bookOrder = session.get(BookOrder.class, bookOrderId);

        return bookOrder;
    }

    public List<BookOrder> getAllBooks(Order order) {
        Integer orderId = order.getOrderId();
        return getAllBooksByOrderId(orderId);
    }

    public List<BookOrder> getAllBooksByOrderId(Integer orderId){
        Session session = getCurrentSession();

        String hql = "FROM BookOrder bo WHERE bo.order.orderId = :orderId";

        Query<BookOrder> query = session.createQuery(hql, BookOrder.class);
        query.setParameter("orderId", orderId);
        return query.list();
    }

    public void addBook(Order order, Book book, Integer num_of_books) {
        Session session = getCurrentSession();

        BookOrder bookOrder = getBookOrder(order, book);

        if (bookOrder == null) {
            bookOrder = new BookOrder();
            bookOrder.setId(new BookOrderId(order.getOrderId(),book.getBookId()));
            bookOrder.setOrder(order);
            bookOrder.setBook(book);
            bookOrder.setNum_of_books(num_of_books);

            session.persist(bookOrder);
        } else {
            bookOrder.setNum_of_books(num_of_books);
            session.merge(bookOrder);
        }

        session.flush();
        session.refresh(book);
    }
    public void deleteBook(Order order, Book book) {
        Session session = getCurrentSession();

        BookOrder bookOrder = getBookOrder(order,book);

        if(bookOrder != null) {
            session.delete(bookOrder);
        }
    }
}