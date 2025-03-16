package com.grochu.libraryadminclient.Controllers;

import com.grochu.libraryadminclient.DAL.Copy;
import com.grochu.libraryadminclient.Interfaces.BookService;
import com.grochu.libraryadminclient.Interfaces.CopyService;
import com.grochu.libraryadminclient.Interfaces.CustomerService;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/books/{book_id}")
public class CopyController {

    private final CopyService copyService;
    private final BookService bookService;
    private final CustomerService customerService;

    @PostMapping(path="/add")
    public String addNewCopyOfBookWithNumber(@PathVariable("book_id") int book_id,
                                             @RequestParam(value = "copyId", required = false) Long copyId,
                                             Model model)
    {
        if(copyId != null && copyService.findById(copyId) != null)
        {
            return "redirect:/books/"+book_id;
        }
        Copy copy = new Copy(copyId, bookService.findById(book_id));
        copyService.addCopy(copy);
        return "redirect:/books/" + book_id;
    }

    @GetMapping(path="/copy/{copy_id}/return")
    public String returnCopyNow(@PathVariable("book_id") int book_id,
                                @PathVariable("copy_id") Long copyId,
                                @RequestParam(value="userId", required = false) Long userId,
                                Model model)
    {
        String returnDestiny = (userId!=null) ? "redirect:/customers/"+ userId : "redirect:/books/"+book_id;
        if(bookService.findById(book_id) == null || copyService.findById(copyId) == null)
        {
            return returnDestiny;
        }
        copyService.returnCopyNow(copyService.findById(copyId));
        return returnDestiny;
    }

    @GetMapping(path="/copy/{copy_id}/borrow/{user_id}")
    public String borrowCopyNow(@PathVariable("book_id") int book_id, @PathVariable("copy_id")Long copyId,
                                @PathVariable("user_id") long userId, Model model)
    {

        if(bookService.findById(book_id) == null || copyService.findById(copyId) == null
        || customerService.getCustomerById(userId) == null)
        {
            return "redirect:/books/"+book_id;
        }
        copyService.borrowCopyNow(copyService.findById(copyId), customerService.getCustomerById(userId));
        return "redirect:/books/"+book_id;
    }

    @GetMapping(path="/copyDelete", params="copyId")
    public String deleteCopyIfNotBorrowed(@PathVariable("book_id") long bookId, @RequestParam("copyId") long copyId)
    {
        copyService.deleteCopy(copyId);
        return "redirect:/books/" + bookId;
    }
}
