package main.Controllers;

import jakarta.validation.Valid;
import main.DAO.ClientDAO;
import main.DAO.OrderDAO;
import main.java_entities.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ClientController {
    @Autowired
    ClientDAO clientDAO;

    @Autowired
    OrderDAO orderDAO;

    @GetMapping("/clients")
    public String listCustomers(Model model,
                                @RequestParam(value = "name", required = false) String searchName,
                                @RequestParam(value = "phone", required = false) String searchPhone) {

        List<Client> clients;
        if (searchName != null && !searchName.isEmpty() || searchPhone != null && !searchPhone.isEmpty()) {
            clients = clientDAO.searchClients(searchName, searchPhone);
        } else {
            clients = clientDAO.getAll();
        }

        model.addAttribute("clients", clients);
        model.addAttribute("searchName",searchName);
        model.addAttribute("searchPhone",searchPhone);
        return "clients";
    }


    @GetMapping("/clients/{clientId}")
    public String showClient(@PathVariable Integer clientId, Model model) {
        Client client = clientDAO.getById(clientId);
        if (client == null) {
            return "error";
        }
        model.addAttribute("client", client);
        model.addAttribute("orders",clientDAO.ordersById(clientId));
        return "clientId";
    }

    @GetMapping("/clients/add")
    public String addClientForm(Model model) {
        model.addAttribute("newClient", new Client());
        return "addClient";
    }

    @GetMapping("clients/{clientId}/delete")
    public String deleteClient(Model model, @PathVariable Integer clientId) {
        clientDAO.deleteById(clientId);
        return "redirect:/clients";
    }

    @PostMapping("/clients/add")
    public String addClient(
            @Valid @ModelAttribute("newClient") Client newClient,
            BindingResult result,
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            String errorMessage = result.getFieldErrors().stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .collect(Collectors.joining("<br>"));
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
            return "redirect:/clients/add";
        }

        try {
            clientDAO.save(newClient);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Ошибка при добавлении клиента: " + e.getMessage());
            return "redirect:/clients/add";
        }

        return "redirect:/clients";
    }

    @PostMapping("/clients/{clientId}/edit")
    public String updateClient(@PathVariable Integer clientId, @Valid @ModelAttribute("client") Client client,
                               BindingResult result,
                               RedirectAttributes redirectAttributes) {
        Client existingClient = clientDAO.getById(clientId);
        if (existingClient == null) {
            return "error";
        }

        if (result.hasErrors()) {
            String errorMessage = result.getFieldErrors().stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .collect(Collectors.joining("<br>"));
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
            return "redirect:/clients/{clientId}";
        }

        existingClient.setFullName(client.getFullName());
        existingClient.setEmail(client.getEmail());
        existingClient.setPhone(client.getPhone());
        existingClient.setAddress(client.getAddress());


        try {
            clientDAO.update(existingClient);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Ошибка при обновлении клиента: " + e.getMessage());
        }

        return "redirect:/clients/{clientId}";
    }


}
