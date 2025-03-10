package com.grochu.libraryadminclient.Interfaces;

import com.grochu.libraryadminclient.DAL.Book;

public interface BookService
{
    Iterable<Book> findAll();
    Book findById(int id);
    Book addBook(Book book);
    Integer getAllBooksNumber();
}
