package com.grochu.libraryadminclient.Interfaces;

import com.grochu.libraryadminclient.DAL.Borrow;
import com.grochu.libraryadminclient.DAL.Copy;
import com.grochu.libraryadminclient.DAL.User;

import java.util.List;

public interface CustomerService {
    List<User> getPage(int page);
    List<User> findCustomerAndPage(String searchPhrase, int page);
    User getCustomerById(long id);
    List<Borrow> getBorrowedCopiesForUser(long userId);
    Integer getAllMatchingCustomersNumber(String searchPhrase);
}
