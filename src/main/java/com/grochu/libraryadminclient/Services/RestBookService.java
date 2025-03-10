package com.grochu.libraryadminclient.Services;

import com.grochu.libraryadminclient.DAL.Book;
import com.grochu.libraryadminclient.Interfaces.BookService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

public class RestBookService implements BookService
{
    private RestClient restClient;
    private String token;

    public RestBookService(String token)
    {
        restClient = RestClient.create();
        this.token = token;
    }

    @Override
    public Iterable<Book> findAll() {
        return restClient.get()
                .uri("http://localhost:8080/api/books")
                .header("Authentication", "Bearer " + token)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(new ParameterizedTypeReference<Iterable<Book>>() {
                }).getBody();
    }

    @Override
    public Book findById(int id) {
        return restClient.get()
                .uri("http://localhost:8080/api/books/"+id)
                .header("Authentication", "Bearer " + token)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(Book.class).getBody();
    }

    @Override
    public Book addBook(Book book) {
        return null;
    }

    @Override
    public Integer getAllBooksNumber() {
        Integer result = restClient.get()
                .uri("http://localhost:8080/api/books/number")
                .header("Authentication", "Bearer " + token)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(Integer.class).getBody();

        return result != null ? result : 0;
    }
}
