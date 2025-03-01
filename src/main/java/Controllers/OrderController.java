package Controllers;
import java.util.List;

import DAO.ClientDAO;
import DAO.OrderDAO;
import java_entities.Client;
import java_entities.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderDAO orderDAO;
    @Autowired
    private ClientDAO clientDAO;

    @GetMapping("/list")
    public String listOrders(Model model) {
        List<Order> orders = orderDAO.getAll();
        model.addAttribute("orders", orders);
        return "orders/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("order", new Order());
        List<Client> clients = clientDAO.getAll();
        model.addAttribute("clients", clients);
        return "orders/create";
    }

    @PostMapping("/create")
    public String createOrder(@ModelAttribute Order order, @RequestParam("clientId") Integer clientId) {
        Client client = clientDAO.getById(clientId);
        order.setClient(client);
        orderDAO.save(order);
        return "redirect:/orders/list";
    }
}