package main;

import main.DAO.BookDAO;
import main.DAO.ClientDAO;
import main.DAO.OrderDAO;
import main.configs.HibernateConfig;
import main.java_entities.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
@ContextConfiguration(classes = HibernateConfig.class)
public class DAOTests {

    class BookIdAndNums {
        private Integer bookId;
        private Integer num_of_books;

        public BookIdAndNums(Integer bookId, Integer num_of_books) {
            this.bookId = bookId;
            this.num_of_books = num_of_books;
        }

        public Integer getBookId() {
            return bookId;
        }

        public Integer getNumOfBooks() {
            return num_of_books;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            BookIdAndNums that = (BookIdAndNums) o;
            return Objects.equals(bookId, that.getBookId()) && Objects.equals(num_of_books, that.getNumOfBooks());
        }

        @Override
        public int hashCode() {
            return Objects.hash(bookId, num_of_books);
        }
    }

    @Autowired
    private BookDAO bookDAO;

    @Autowired
    private ClientDAO clientDAO;

    @Autowired
    private OrderDAO orderDAO;


    @Test
    public void testBookCRUD() {
        Book book = new Book();
        book.setTitle("Test Book");
        book.setAuthors("Test Author");
        book.setPrice(10);
        book.setAvailable(5);
        book.setCover(TypeCover.Hardcover);
        bookDAO.save(book);

        Book retrievedBook = bookDAO.getById(book.getBookId());
        assertNotNull(retrievedBook);
        assertEquals("Test Book", retrievedBook.getTitle());

        retrievedBook.setTitle("Updated Test Book");
        bookDAO.update(retrievedBook);

        Book updatedBook = bookDAO.getById(book.getBookId());
        Assertions.assertEquals("Updated Test Book", updatedBook.getTitle());

        bookDAO.delete(updatedBook);
        Assertions.assertNull(bookDAO.getById(book.getBookId()));
    }

    @Test
    public void testBookfilters() {
        List<Book> books = bookDAO.filterBooks(null, "Miranda", null, null);
        Set<Integer> checkId = new HashSet<>();
        for (Book book : books) {
            checkId.add(book.getBookId());
        }
        Set<Integer> realId = new HashSet<>();
        realId.add(7);
        realId.add(8);
        realId.add(9);
        Assertions.assertEquals(realId, checkId);

        books = bookDAO.filterBooks("History", "", null, null);
        checkId = new HashSet<>();
        for (Book book : books) {
            checkId.add(book.getBookId());
        }
        realId = new HashSet<>();
        realId.add(2);
        realId.add(5);
        realId.add(9);
        Assertions.assertEquals(realId, checkId);

        books = bookDAO.filterBooks("", null, 200, 500);
        checkId = new HashSet<>();
        for (Book book : books) {
            checkId.add(book.getBookId());
        }
        realId = new HashSet<>();
        realId.add(1);
        realId.add(2);
        realId.add(7);
        Assertions.assertEquals(realId, checkId);
    }

    @Test
    public void ClientTests() {
        List<Client> clients = clientDAO.findByName("Harry");
        Set<Integer> get = new HashSet<>();
        for (Client client : clients) {
            get.add(client.getClientId());
        }
        Set<Integer> ans = new HashSet<>();
        ans.add(1);
        Assertions.assertEquals(get, ans);

        clients = clientDAO.findByPhone("+44 7700 900000");
        get = new HashSet<>();
        for (Client client : clients) {
            get.add(client.getClientId());
        }
        ans = new HashSet<>();
        ans.add(1);
        Assertions.assertEquals(get, ans);
    }

    @Test
    public void getAllBooksInOrder() {

        List<BookOrder> booksInOrder = orderDAO.getAllBooks(orderDAO.getById(1));

        Set<BookIdAndNums> actual = new HashSet<>();
        actual.add(new BookIdAndNums(1, 1));
        actual.add(new BookIdAndNums(2, 1));

        Set<BookIdAndNums> received = new HashSet<>();
        for (BookOrder result : booksInOrder) {
            Book book = result.getBook();
            Integer num_of_books = result.getNum_of_books();
            received.add(new BookIdAndNums(book.getBookId(), num_of_books));
        }
        Assertions.assertEquals(received, actual);
        booksInOrder = orderDAO.getAllBooksByOrderId(6);
        assert (booksInOrder.isEmpty());
    }

    @Test
    public void AddDeleteBookInOrder() {
        Book book = bookDAO.getById(1);
        Order order = orderDAO.getById(1);
        Integer was_avaible = book.getAvailable();
        BookOrder bookOrder = orderDAO.getBookOrder(order, book);
        Integer was_nums = bookOrder.getNum_of_books();
        orderDAO.addBook(order, book, was_nums + 1);
        Assertions.assertEquals(orderDAO.getBookOrder(order, book).getNum_of_books(), was_nums + 1);
        Assertions.assertEquals(was_avaible - 1, book.getAvailable());
        Assertions.assertNotNull(orderDAO.getBookOrder(order, book));
        orderDAO.deleteBook(order, book);
        Assertions.assertNull(orderDAO.getBookOrder(order, book));
        orderDAO.deleteBook(order,book);
        book = bookDAO.getById(5);
        was_avaible = book.getAvailable();
        orderDAO.addBook(order,book,2);
        Assertions.assertEquals(was_avaible - 2,book.getAvailable());

    }


    @Test
    public void testClientCRUD() {
        Client client = new Client();
        client.setFullName("Test Client");
        client.setEmail("test@example.com");
        client.setAddress("Example address");
        client.setPhone("+123243523");
        clientDAO.save(client);

        Client retrievedClient = clientDAO.getById(client.getClientId());
        Assertions.assertNotNull(retrievedClient);
        Assertions.assertEquals("Test Client", retrievedClient.getFullName());

        retrievedClient.setFullName("Updated Test Client");
        clientDAO.update(retrievedClient);

        Client updatedClient = clientDAO.getById(client.getClientId());
        Assertions.assertEquals("Updated Test Client", updatedClient.getFullName());

        clientDAO.delete(updatedClient);
        Assertions.assertNull(clientDAO.getById(client.getClientId()));
        Assertions.assertEquals(6, clientDAO.getAll().size());
    }
    @Test
    public void deleteorders() {
        orderDAO.deleteById(1);
        Assertions.assertNull(orderDAO.getById(1));
        orderDAO.deleteById(1);
        Assertions.assertEquals(4, orderDAO.getAll().size());
    }

}