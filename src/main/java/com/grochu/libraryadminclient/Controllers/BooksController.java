package com.grochu.libraryadminclient.Controllers;

import com.grochu.libraryadminclient.DAL.Author;
import com.grochu.libraryadminclient.DAL.Book;
import com.grochu.libraryadminclient.DAL.Borrow;
import com.grochu.libraryadminclient.DAL.Copy;
import com.grochu.libraryadminclient.DTO.AddBookAndAuthorDTO;
import com.grochu.libraryadminclient.DTO.AddBookDTO;
import com.grochu.libraryadminclient.Interfaces.AuthorService;
import com.grochu.libraryadminclient.Interfaces.BookService;
import com.grochu.libraryadminclient.Interfaces.CopyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Slf4j
@Controller
@RequestMapping("/books")
public class BooksController
{
    private AuthorService authorService;
    private BookService bookService;
    private CopyService copyService;

    public BooksController(BookService bookService, CopyService copyService, AuthorService authorService) {
        this.bookService = bookService;
        this.copyService = copyService;
        this.authorService = authorService;
    }

    @GetMapping(params="!page")
    public String getAllBooks(Model model,
                              @RequestParam(value="search", required = false) String searchPhrase)
    {
        List<Book> books;
        if(searchPhrase == null)
            books = bookService.findPage(1);
        else
            books = bookService.findBookAndPage(searchPhrase, 1);
        model.addAttribute("books",books);
        model.addAttribute("page",1);
        model.addAttribute("search",searchPhrase);
        int count = bookService.getAllMatchingBooksNumber(searchPhrase);
        model.addAttribute("pageNumber",count/25 + ((count%25 == 0)?0:1));
        return "pages/books";
    }

    @GetMapping(params="page")
    public String getAllBooksWithPage(@RequestParam("page") int page, Model model,
                                      @RequestParam(value="search", required = false) String searchPhrase)
    {
        List<Book> books;
        if(searchPhrase == null)
            books = bookService.findPage(page);
        else
            books = bookService.findBookAndPage(searchPhrase, page);
        model.addAttribute("books",books);
        model.addAttribute("page", page);
        model.addAttribute("search",searchPhrase);
        int count = bookService.getAllMatchingBooksNumber(searchPhrase);
        model.addAttribute("pageNumber",count/25 + ((count%25 == 0)?0:1));
        return "pages/books";
    }

    @GetMapping("/{id}")
    public String getBookDetails(@PathVariable int id, Model model)
    {
        Book book = bookService.findById(id);
        List<Copy> allCopies = copyService.findAllCopiesOfBook(id).stream().sorted(Comparator.comparing(Copy::getId)).toList();
        List<Copy> availableCopies = copyService.findAvailableCopiesOfBook(id).stream().sorted(Comparator.comparing(Copy::getId)).toList();
        List<Copy> destroyed = copyService.findDestroyedCopiesOfBook(id).stream().sorted(Comparator.comparing(Copy::getId)).toList();
        Map<Copy,Boolean> copies = new TreeMap<>(Comparator.comparing(Copy::getDestroyed).thenComparingLong(Copy::getId));
        availableCopies.forEach(copy -> copies.put(copy,true));
        allCopies.forEach(copy -> {if(!destroyed.contains(copy))copies.putIfAbsent(copy,false);});
        destroyed.forEach(copy -> copies.putIfAbsent(copy,false));

        List<Copy> borrowedCopies = allCopies.stream().filter(c-> !availableCopies.contains(c)).toList();
        Map<Copy, Borrow> borrowInfo = new HashMap<>();
        borrowedCopies.forEach(copy -> borrowInfo.put(copy, copyService.actualBorrowOfCopy(copy.getId())));

        model.addAttribute("book",book);
        model.addAttribute("copies", copies);
        model.addAttribute("borrowInfo", borrowInfo);
        return "pages/bookManage";
    }

    @GetMapping(path = "/add", params="!author")
    public String addBook(@RequestParam(value="bookId", required = false) Long bookId,Model model)
    {
        Iterable<Author> authors = authorService.getAllAuthors();
        model.addAttribute("authors", authors);

        if(bookId != null)
        {
            Book book = bookService.findById(bookId);
            if(book != null) {
                model.addAttribute("book", book);
                return "pages/bookEditForm";
            }
        }
        return "pages/bookAddForm";
    }

    @GetMapping(path = "/add", params="author")
    public String addBookAdnAuthor(@RequestParam("author") String option, Model model)
    {
        if(option.equals("new"))
            return "pages/bookAddFormWithAuthor";
        try{
            long authorId = Long.parseLong(option);
            model.addAttribute("preselectedAuthor", authorService.findById(authorId));
            return "pages/bookAddForm";
        }catch(Exception e){
            return "redirect:/books/add";
        }
    }

    @PostMapping(path = "/add/onlyBook")
    public String addBook(AddBookDTO bookDTO)
    {
        Book book = new Book();
        if(bookDTO.id()!=null) book.setId(bookDTO.id());
        book.setTitle(bookDTO.title());
        book.setPublishYear(bookDTO.publishYear());
        book.setAuthor(authorService.findById(bookDTO.author()));

        bookService.addBook(book);
        return "redirect:/books";
    }

    @PostMapping(path = "/add/authorAndBook")
    public String addBookAndAuthor(AddBookAndAuthorDTO bookAndAuthorDTO)
    {
        Author author = new Author();
        author.setName(bookAndAuthorDTO.author());
        author = authorService.addAuthor(author);

        Book book = new Book();
        book.setTitle(bookAndAuthorDTO.title());
        book.setPublishYear(bookAndAuthorDTO.publishYear());
        book.setAuthor(author);

        bookService.addBook(book);
        return "redirect:/books";
    }
}
