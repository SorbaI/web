package Controllers;

import java.util.List;

import DAO.BookDAO;
import java_entities.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/orders")
public class BookController {

    @Autowired
    private BookDAO bookDAO;

    @GetMapping("/")
    public String listOrders(Model model) {
        List<Book> books = bookDAO.getAll();
        model.addAttribute("orders", books);
        return "orders";
    }
}
