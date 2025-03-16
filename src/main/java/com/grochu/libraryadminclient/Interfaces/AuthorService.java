package com.grochu.libraryadminclient.Interfaces;

import com.grochu.libraryadminclient.DAL.Author;
import com.grochu.libraryadminclient.DAL.Book;

import java.util.List;

public interface AuthorService {
    List<Author> getAllAuthors();
    List<Author> getPage(int page);
    List<Author> findAuthorAndPage(String searchPhrase, int page);
    Author findById(long id);
    Author addAuthor(Author author);
    Integer getMatchingAuthorsNumber(String searchPhrase);
    List<Book> findAllBooksOfAuthor(long authorId);
}
