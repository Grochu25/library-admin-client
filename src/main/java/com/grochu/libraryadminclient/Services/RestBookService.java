package com.grochu.libraryadminclient.Services;

import com.grochu.libraryadminclient.Configs.EnvironmentalConfig;
import com.grochu.libraryadminclient.Domain.Book;
import com.grochu.libraryadminclient.Interfaces.BookService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

import java.util.List;

public class RestBookService implements BookService
{
    private RestClient restClient;
    private String token;
    private EnvironmentalConfig envConf;

    public RestBookService(String token, EnvironmentalConfig environmentalConfig)
    {
        restClient = RestClient.create();
        this.token = token;
        this.envConf = environmentalConfig;
    }

    @Override
    public List<Book> findAll() {
        return restClient.get()
                .uri("http://"+envConf.getResourceHostname()+":"+envConf.getResourcePort()+"/api/books/all")
                .header("Authorization", "Bearer " + token)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<List<Book>>() {
                });
    }

    @Override
    public List<Book> findPage(int page) {
        return restClient.get()
                .uri("http://"+envConf.getResourceHostname()+":"+envConf.getResourcePort()+"/api/books?page="+page)
                .header("Authorization", "Bearer " + token)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<List<Book>>() {
                });
    }

    @Override
    public List<Book> findBookAndPage(String searchPhrase, int page) {
        return restClient.get()
                .uri("http://"+envConf.getResourceHostname()+":"+envConf.getResourcePort()+"/api/books?page="+page+"&search="+searchPhrase)
                .header("Authorization", "Bearer " + token)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<List<Book>>() {
                });
    }

    @Override
    public Book findById(long id) {
        return restClient.get()
                .uri("http://"+envConf.getResourceHostname()+":"+envConf.getResourcePort()+"/api/books/"+id)
                .header("Authorization", "Bearer " + token)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(Book.class);
    }

    @Override
    public Book addBook(Book book) {
        return restClient.post()
                .uri("http://"+envConf.getResourceHostname()+":"+envConf.getResourcePort()+"/api/books")
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
            query.uri("http://"+envConf.getResourceHostname()+":"+envConf.getResourcePort()+"/api/books/number");
        else
            query.uri("http://"+envConf.getResourceHostname()+":"+envConf.getResourcePort()+"/api/books/number?search="+searchPhrase);
        Integer result =query.header("Authorization", "Bearer " + token)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(Integer.class);

        return result != null ? result : 0;
    }

}
