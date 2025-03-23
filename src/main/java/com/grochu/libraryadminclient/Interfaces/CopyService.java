package com.grochu.libraryadminclient.Interfaces;

import com.grochu.libraryadminclient.Domain.Borrow;
import com.grochu.libraryadminclient.Domain.Copy;
import com.grochu.libraryadminclient.Domain.User;

import java.util.List;

public interface CopyService {
    List<Copy> findAllCopiesOfBook(long bookId);
    List<Copy> findAvailableCopiesOfBook(long bookId);

    List<Copy> findDestroyedCopiesOfBook(long bookId);

    Copy findById(long id);
    Copy addCopy(Copy copy);
    Borrow actualBorrowOfCopy(long id);

    void deleteCopy(long copyId);

    Borrow returnCopyNow(Copy copy);
    Borrow borrowCopyNow(Copy copy, User user);
}
