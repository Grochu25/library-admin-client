package com.grochu.libraryadminclient.Services;

import com.grochu.libraryadminclient.DAL.Book;
import com.grochu.libraryadminclient.Interfaces.BookService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

import java.util.List;

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
    public List<Book> findAll() {
        return restClient.get()
                .uri("http://localhost:8080/api/books/all")
                .header("Authorization", "Bearer " + token)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<List<Book>>() {
                });
    }

    @Override
    public List<Book> findPage(int page) {
        return restClient.get()
                .uri("http://localhost:8080/api/books?page="+page)
                .header("Authorization", "Bearer " + token)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<List<Book>>() {
                });
    }

    @Override
    public List<Book> findBookAndPage(String searchPhrase, int page) {
        return restClient.get()
                .uri("http://localhost:8080/api/books?page="+page+"&search="+searchPhrase)
                .header("Authorization", "Bearer " + token)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<List<Book>>() {
                });
    }

    @Override
    public Book findById(long id) {
        return restClient.get()
                .uri("http://localhost:8080/api/books/"+id)
                .header("Authorization", "Bearer " + token)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(Book.class);
    }

    @Override
    public Book addBook(Book book) {
        return restClient.post()
                .uri("http://localhost:8080/api/books")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .body(book)
                .retrieve()
                .toEntity(Book.class).getBody();
    }

    @Override
    public Integer getAllMatchingBooksNumber(String searchPhrase) {
        var query = restClient.get();
        if(searchPhrase == null)
            query.uri("http://localhost:8080/api/books/number");
        else
            query.uri("http://localhost:8080/api/books/number?search="+searchPhrase);
        Integer result =query.header("Authorization", "Bearer " + token)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(Integer.class);

        return result != null ? result : 0;
    }

}
