package main.DAO;

import main.java_entities.BookOrder;
import main.java_entities.BookOrderId;
import org.springframework.stereotype.Repository;

@Repository
public class BookOrderDAO extends GenericDAO<BookOrder, BookOrderId> {
    public BookOrderDAO() {
        super(BookOrder.class);
    }
}
