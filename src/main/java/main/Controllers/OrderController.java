package main.Controllers;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.validation.Valid;
import main.DAO.BookDAO;
import main.DAO.BookOrderDAO;
import main.DAO.ClientDAO;
import main.DAO.OrderDAO;
import main.java_entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class OrderController {

    @Autowired
    private OrderDAO orderDAO;
    @Autowired
    private ClientDAO clientDAO;
    @Autowired
    private BookDAO bookDAO;
    @Autowired
    private BookOrderDAO bookOrderDAO;

    @GetMapping("/orders/{orderId}")
    public String showClient(@PathVariable Integer orderId, Model model) {
        Order order = orderDAO.getById(orderId);
        if (order == null) {
            return "error";
        }
        model.addAttribute("order", order);
        model.addAttribute("bookOrders",orderDAO.getAllBooksByOrderId(orderId));
        return "orderId";
    }
    @GetMapping("/orders/{orderId}/delete")
    public String deleteOrder(@PathVariable Integer orderId, Model model) {
        Order order = orderDAO.getById(orderId);
        Client client = order.getClient();
        if (order == null) {
            return "error";
        }
        orderDAO.delete(order);
        return "redirect:/clients/" + client.getClientId();
    }
    @GetMapping("/orders/{orderId}/{bookId}/delete")
    public String deleteOrderBook(@PathVariable Integer orderId, @PathVariable Integer bookId, Model model) {
        Order order = orderDAO.getById(orderId);
        if (order == null) {
            return "error";
        }
        orderDAO.deleteBook(order, bookDAO.getById(bookId));
        return "redirect:/orders/" + orderId;
    }
    @GetMapping("/clients/{clientId}/addOrder")
    public String addOrder(@PathVariable Integer clientId,Model model) {
        Client client = clientDAO.getById(clientId);
        Order order = new Order();
        model.addAttribute("client",client);
        model.addAttribute("newOrder",order);
        return "addOrder";
    }

    @PostMapping("/clients/{clientId}/addOrder")
    public String OrderAdd(@PathVariable Integer clientId,
                            Model model,
                            @Valid @ModelAttribute("newOrder") Order newOrder,
                            BindingResult result,
                            RedirectAttributes redirectAttributes){
        if (result.hasErrors()) {
            String errorMessage = result.getFieldErrors().stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .collect(Collectors.joining("<br>"));
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
            return "redirect:/clients/" + clientId + "/addOrder";
        }

        try {
            Client client = clientDAO.getById(clientId);
            newOrder.setClient(client);
            newOrder.setTotal(0);
            orderDAO.save(newOrder);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Ошибка при добавлении клиента: " + e.getMessage());
            return "redirect:/clients/" + clientId + "/addOrder";
        }

        return "redirect:/orders/" + newOrder.getOrderId();
    }

    @PostMapping("/orders/{orderId}/edit")
    public String OrderEdit(
            @PathVariable Integer orderId,
            @Valid @ModelAttribute("newOrder") Order newOrder,
            BindingResult result,
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            String errorMessage = result.getFieldErrors().stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .collect(Collectors.joining("<br>"));
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
            return "redirect:/orders/" + orderId;
        }
        Order existingOrder = orderDAO.getById(orderId);
        if (existingOrder == null) {
            return "error";
        }

        existingOrder.setAddress(newOrder.getAddress());
        existingOrder.setStatus(newOrder.getStatus());
        existingOrder.setCreationTime(newOrder.getCreationTime());
        existingOrder.setDelivTime(newOrder.getDelivTime());

        try {
            orderDAO.update(existingOrder);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Ошибка при добавлении клиента: " + e.getMessage());
        }

        return "redirect:/orders/" + orderId;
    }
    @PostMapping("/orders/{orderId}/{bookId}/updateNum")
    public String updateNumOfBooks(@PathVariable Integer orderId,
                                   @PathVariable Integer bookId,
                                   @RequestParam("newNumOfBooks") Integer newNumOfBooks) {
        BookOrderId bookOrderId = new BookOrderId(orderId,bookId);
        BookOrder bookOrder = bookOrderDAO.getById(bookOrderId);
        bookOrder.setNum_of_books(newNumOfBooks);
        bookOrderDAO.update(bookOrder);
        return "redirect:/orders/" + orderId;
    }
    @GetMapping("/orders/{orderId}/books/add")
    public String addBookForOrder(@PathVariable Integer orderId, Model model) {
        Order order = orderDAO.getById(orderId);
        List <Book> books = bookDAO.getAll();
        if (order == null) {
            return "error";
        }
        model.addAttribute("order", order);
        model.addAttribute("showAddButton",true);
        model.addAttribute("books",books);
        return "books";
    }
    @GetMapping("/orders/{orderId}/books/{bookId}/add")
    public String addBookForOrder(@PathVariable Integer orderId, @PathVariable Integer bookId,
                                  RedirectAttributes redirectAttributes){
        try {

            Order order = orderDAO.getById(orderId);
            Book book = bookDAO.getById(bookId);
            orderDAO.addBook(order,book,1);
            return "redirect:/orders/" + orderId;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Заказ или книга не найдены." +
                    e.getClass().getName());
            return "redirect:/orders/" + orderId;
        }
    }
}





