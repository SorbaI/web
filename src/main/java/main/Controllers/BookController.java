package main.Controllers;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.validation.Valid;
import main.DAO.BookDAO;
import main.java_entities.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class BookController {

    @Autowired
    private BookDAO bookDAO;

    @GetMapping("/books")
    public String listBooks(Model model,
                            @RequestParam(value = "author", required = false) String author,
                            @RequestParam(value = "title", required = false) String title,
                            @RequestParam(value = "genre", required = false) String genre,
                            @RequestParam(value = "minPrice", required = false) Integer minPrice,
                            @RequestParam(value = "maxPrice", required = false) Integer maxPrice) {

        List<Book> books = bookDAO.filterBooks(genre,author,minPrice,maxPrice,title);
        model.addAttribute("books", books);
        model.addAttribute("title", title);
        model.addAttribute("author",author);
        model.addAttribute("genre",genre);
        model.addAttribute("minPrice",minPrice);
        model.addAttribute("maxPrice",maxPrice);
        return "books";
    }

    @GetMapping("books/add")
    public String addBookpage(Model model) {
        model.addAttribute("newBook", new Book());
        return "addBook";
    }

    @PostMapping("/books/add")
    public String addBook(
            @Valid @ModelAttribute("newBook") Book newBook,
            BindingResult result,
            RedirectAttributes redirectAttributes){

        if (result.hasErrors()) {
            String errorMessage = result.getFieldErrors().stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .collect(Collectors.joining("<br>"));
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
            return "redirect:/books";
        }

        try{
            bookDAO.save(newBook);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Ошибка при добавлении книги: " + e.getMessage());
            return "redirect:/books/add";
        }
        return "redirect:/books";

    }
    @GetMapping("/books/{bookId}")
    public String showEditForm(@PathVariable Integer bookId, Model model) {
        Book book = bookDAO.getById(bookId);
        model.addAttribute("book", book);
        return "bookId";
    }

    @PostMapping("/books/{bookId}/edit")
    public String updateBook(@PathVariable Integer bookId, @Valid @ModelAttribute("book") Book book, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            String errorMessage = result.getFieldErrors().stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .collect(Collectors.joining("<br>"));
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
            return "redirect:/books/" + bookId + "/edit";
        }

        bookDAO.update(book);
        return "redirect:/books/" + bookId;
    }

}
