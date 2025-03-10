package com.grochu.libraryadminclient.Controllers;

import com.grochu.libraryadminclient.DAL.Book;
import com.grochu.libraryadminclient.Interfaces.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequestMapping("/books")
public class BooksController
{
    private BookService bookService;

    public BooksController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping(params="!page")
    public String getAllBooks(Model model)
    {
        var list = bookService.findAll();
        log.info(list.toString());
        model.addAttribute("books",list);
        model.addAttribute("page",1);
        model.addAttribute("pageNumber",bookService.getAllBooksNumber()/25 + 1);
        return "books";
    }

    @GetMapping(params="page")
    public String getAllBooksWithPage(@RequestParam("page") int page, Model model)
    {
        var list = bookService.findAll();
        log.info(list.toString());
        model.addAttribute("books",list);
        model.addAttribute("page", page);
        model.addAttribute("pageNumber",bookService.getAllBooksNumber()/25 + 1);
        return "books";
    }

    @GetMapping("/{id}")
    public String getBookDetails(@PathVariable int id, Model model)
    {
        Book book = bookService.findById(id);
        log.info(book.toString());
        model.addAttribute("book",book);
        return "bookManage";
    }

    @GetMapping("/add")
    public String addBook()
    {
        return "bookForm";
    }
}
