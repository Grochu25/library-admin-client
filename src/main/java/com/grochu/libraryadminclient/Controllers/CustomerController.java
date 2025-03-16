package com.grochu.libraryadminclient.Controllers;

import com.grochu.libraryadminclient.DAL.Borrow;
import com.grochu.libraryadminclient.DAL.User;
import com.grochu.libraryadminclient.Interfaces.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.print.attribute.standard.Copies;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/customers")
public class CustomerController
{
    private CustomerService customerService;

    public CustomerController(CustomerService customerService)
    {
        this.customerService = customerService;
    }

    @GetMapping
    public String getCustomersPage(@RequestParam(value="page", required = false) Integer page, Model model,
                                   @RequestParam(value="search", required = false) String searchPhrase,
                                   @RequestParam(value="borrowMode", required = false) Boolean borrowMode,
                                   @RequestParam(value="copyId", required = false) Long copyId,
                                   @RequestParam(value="bookId", required = false) Long bookId)
    {
        if (page == null) page = 1;
        List<User> users;
        if(searchPhrase == null)
            users = customerService.getPage(page);
        else
            users = customerService.findCustomerAndPage(searchPhrase, page);
        model.addAttribute("users", users);
        model.addAttribute("page", page);
        model.addAttribute("search", searchPhrase);
        model.addAttribute("borrowMode", borrowMode);
        model.addAttribute("copyId", copyId);
        model.addAttribute("bookId", bookId);
        int count = customerService.getAllMatchingCustomersNumber(searchPhrase);
        model.addAttribute("pageNumber", count/25 + ((count%25 == 0)?0:1));

        return "pages/customers";
    }

    @GetMapping(path="/{id}")
    public String getCustomersPage(@PathVariable("id") long id, Model model)
    {
        List<Borrow> borrows = customerService.getBorrowedCopiesForUser(id);

        model.addAttribute("user", customerService.getCustomerById(id));
        model.addAttribute("borrows", sortBorrows(borrows));

        return "pages/customerManage";
    }

    private List<Borrow> sortBorrows(List<Borrow> borrows)
    {
        List<Borrow> sortedBorrows = new ArrayList<>();
        sortedBorrows.addAll(borrows.stream().filter(b -> b.getUntil()==null).toList());
        sortedBorrows.sort(Comparator.comparing(Borrow::getSince));
        sortedBorrows.addAll(borrows.stream().filter(b -> b.getUntil()!=null)
                .sorted(Comparator.comparing(Borrow::getUntil)).toList());

        return sortedBorrows;
    }
}
