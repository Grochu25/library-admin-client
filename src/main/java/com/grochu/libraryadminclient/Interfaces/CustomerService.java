package com.grochu.libraryadminclient.Interfaces;

import com.grochu.libraryadminclient.Domain.Borrow;
import com.grochu.libraryadminclient.Domain.User;

import java.util.List;

public interface CustomerService {
    List<User> getPage(int page);
    List<User> findCustomerAndPage(String searchPhrase, int page);
    User getCustomerById(long id);
    List<Borrow> getBorrowedCopiesForUser(long userId);
    Integer getAllMatchingCustomersNumber(String searchPhrase);
}
