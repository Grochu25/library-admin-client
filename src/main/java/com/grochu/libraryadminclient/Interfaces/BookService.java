package com.grochu.libraryadminclient.Interfaces;

import com.grochu.libraryadminclient.DAL.Book;

import java.util.List;

public interface BookService
{
    List<Book> findAll();
    List<Book> findPage(int page);
    List<Book> findBookAndPage(String searchPhrase, int page);
    Book findById(long id);
    Book addBook(Book book);
    Integer getAllMatchingBooksNumber(String searchPhrase);
}
