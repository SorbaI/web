package main.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/clients")
    public String clients() {
        return "clients";
    }

    @GetMapping("/books")
    public String books() {
        return "books";
    }
}

