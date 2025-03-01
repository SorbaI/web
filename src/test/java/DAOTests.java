import DAO.*;
import java_entities.*;
import configs.HibernateConfig;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ContextConfiguration(classes = HibernateConfig.class)
@Transactional
public class DAOTests {

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
        assertEquals("Updated Test Book", updatedBook.getTitle());

        bookDAO.delete(updatedBook);
        assertNull(bookDAO.getById(book.getBookId()));
    }

    @Test
    public void testClientCRUD() {
        // Create
        Client client = new Client();
        client.setFullName("Test Client");
        client.setContactInfo(new String[]{"test@example.com"});
        clientDAO.save(client);

        // Read
        Client retrievedClient = clientDAO.getById(client.getClientId());
        assertNotNull(retrievedClient);
        assertEquals("Test Client", retrievedClient.getFullName());

        // Update
        retrievedClient.setFullName("Updated Test Client");
        clientDAO.update(retrievedClient);

        Client updatedClient = clientDAO.getById(client.getClientId());
        assertEquals("Updated Test Client", updatedClient.getFullName());

        // Delete
        clientDAO.delete(updatedClient);
        assertNull(clientDAO.getById(client.getClientId()));
    }
    @Test
    public void testOrderCRUDAndFindByClient() {
        // Create Client
        Client client = new Client();
        client.setFullName("Test Client");
        client.setContactInfo(new String[]{"test@example.com"});
        clientDAO.save(client);

        // Create Order
        Order order = new Order();
        order.setClient(client);
        order.setAddress("Test Address");
        order.setCreationTime(new Timestamp(System.currentTimeMillis()));
        order.setDelivTime(new Timestamp(System.currentTimeMillis() + 86400000)); // Tomorrow
        order.setStatus(OrderStatus.processed);
        order.setTotal(20);
        orderDAO.save(order);

        // Read
        Order retrievedOrder = orderDAO.getById(order.getOrderId());
        assertNotNull(retrievedOrder);
        assertEquals("Test Address", retrievedOrder.getAddress());
        assertEquals(client.getClientId(), retrievedOrder.getClient().getClientId());

        // Find by client
        List<Order> ordersByClient = orderDAO.findByClient(client);
        assertNotNull(ordersByClient);
        assertEquals(1, ordersByClient.size());
        assertEquals(order.getOrderId(), ordersByClient.get(0).getOrderId());

        // Update
        retrievedOrder.setAddress("Updated Test Address");
        orderDAO.update(retrievedOrder);

        Order updatedOrder = orderDAO.getById(order.getOrderId());
        assertEquals("Updated Test Address", updatedOrder.getAddress());

        // Delete
        orderDAO.delete(updatedOrder);
        assertNull(orderDAO.getById(order.getOrderId()));

        clientDAO.delete(client); // Clean up client
    }
}
