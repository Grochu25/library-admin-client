package com.grochu.libraryadminclient.Controllers;

import com.grochu.libraryadminclient.DAL.Author;
import com.grochu.libraryadminclient.DAL.Book;
import com.grochu.libraryadminclient.Interfaces.AuthorService;
import com.grochu.libraryadminclient.Interfaces.CopyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/authors")
public class AuthorController
{
    private final CopyService copyService;
    private AuthorService authorService;

    public AuthorController(AuthorService authorService, CopyService copyService) {
        this.authorService = authorService;
        this.copyService = copyService;
    }


    @GetMapping(params="!page")
    public String getAllAuthors(Model model,
                                @RequestParam(value="search", required = false) String searchPhrase)
    {
        List<Author> authors;
        if(searchPhrase == null)
            authors = authorService.getPage(1);
        else
            authors = authorService.findAuthorAndPage(searchPhrase, 1);
        model.addAttribute("authors",authors);
        model.addAttribute("page",1);
        model.addAttribute("search", searchPhrase);
        int count = authorService.getMatchingAuthorsNumber(searchPhrase);
        model.addAttribute("pageNumber", count/25 + ((count%25 == 0)?0:1));
        return "pages/authors";
    }

    @GetMapping(params="page")
    public String getAllAuthorsWithPage(@RequestParam("page") int page, Model model,
                                        @RequestParam(value="search", required = false) String searchPhrase)
    {
        List<Author> authors;
        if(searchPhrase == null)
            authors = authorService.getPage(page);
        else
            authors = authorService.findAuthorAndPage(searchPhrase, page);
        model.addAttribute("authors",authors);
        model.addAttribute("page", page);
        model.addAttribute("search", searchPhrase);
        int count = authorService.getMatchingAuthorsNumber(searchPhrase);
        model.addAttribute("pageNumber", count/25 + ((count%25 == 0)?0:1));
        return "pages/authors";
    }

    @GetMapping("/{id}")
    public String getAuthorsDetails(@PathVariable int id, Model model)
    {
        Author author = authorService.findById(id);
        Iterable<Book> allBooks = authorService.findAllBooksOfAuthor(id);
        Map<Book,int[]> bookNumberMap = new HashMap<>();
        for (Book book : allBooks)
        {
            int all = copyService.findAllCopiesOfBook(book.getId()).size();
            int available = copyService.findAvailableCopiesOfBook(book.getId()).size();
            bookNumberMap.put(book,new int[]{all,available});
        }

        model.addAttribute("books", bookNumberMap);
        model.addAttribute("author", author);
        return "pages/authorManage";
    }

    @GetMapping(path = "/add")
    public String addAuthor(@RequestParam(value="authorId", required = false) Long authorId, Model model)
    {
        if(authorId != null) {
            model.addAttribute("author", authorService.findById(authorId));
            return "pages/authorEditForm";
        }
        return "pages/authorAddForm";
    }

    @PostMapping(path = "/add")
    public String addAuthor(Author author)
    {
        if(authorService.findById(author.getId())!=null)
        {
            authorService.addAuthor(author);
            return "redirect:/authors/" + author.getId();
        }
        authorService.addAuthor(author);
        return "redirect:/authors";
    }
}
