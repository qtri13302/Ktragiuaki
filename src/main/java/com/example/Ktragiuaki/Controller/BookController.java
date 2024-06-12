package com.example.Ktragiuaki.Controller;

import com.example.Ktragiuaki.Model.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {

    private List<Book> books = new ArrayList<>();
    private int nextId = 1;

    @GetMapping
    public String getBooks(Model model) {
        model.addAttribute("books", books);
        return "books";
    }

    @GetMapping("/add")
    public String showAddBookForm() {
        return "add-book";
    }

    @PostMapping
    public String addBook(@RequestParam String title, @RequestParam String author) {
        Book book = new Book();
        book.setId(nextId++);
        book.setTitle(title);
        book.setAuthor(author);
        books.add(book);
        model.addAttribute("message", "Sách đã được thêm thành công!");
        return "redirect:/books";
    }

    @GetMapping("/edit/{id}")
    public String editBook(@PathVariable int id, Model model) {
        for (Book book : books) {
            if (book.getId() == id) {
                model.addAttribute("book", book);
                return "edit-book";
            }
        }
        model.addAttribute("error", "Không tìm thấy sách với ID: " + id);
        return "redirect:/books";
    }

    @PostMapping("/{id}")
    public String updateBook(@PathVariable int id, @RequestParam String title, @RequestParam String author) {
        for (Book book : books) {
            if (book.getId() == id) {
                book.setTitle(title);
                book.setAuthor(author);
                return "redirect:/books";
            }
        }
        // Nếu không tìm thấy sách, chuyển hướng về trang danh sách sách
        return "redirect:/books";
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable int id) {
        books.removeIf(book -> book.getId() == id);
        return "redirect:/books";
    }
}
