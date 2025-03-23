package com.grochu.libraryadminclient.Services;

import com.grochu.libraryadminclient.Configs.EnvironmentalConfig;
import com.grochu.libraryadminclient.Domain.Author;
import com.grochu.libraryadminclient.Domain.Book;
import com.grochu.libraryadminclient.Interfaces.AuthorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

import java.util.List;

@Slf4j
public class RestAuthorService implements AuthorService
{
    private RestClient restClient;
    private String token;
    private EnvironmentalConfig envConf;

    public RestAuthorService(String token, EnvironmentalConfig envConf)
    {
        restClient = RestClient.create();
        this.token = token;
        this.envConf = envConf;
    }

    @Override
    public List<Author> getAllAuthors()
    {
        log.info(token);
        return restClient.get()
                .uri("http://"+envConf.getResourceHostname()+":"+envConf.getResourcePort()+"/api/authors/all")
                .header("Authorization", "Bearer "+token)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(new ParameterizedTypeReference<List<Author>>(){})
                .getBody();
    }

    @Override
    public List<Author> getPage(int page) {
        return restClient.get()
                .uri("http://"+envConf.getResourceHostname()+":"+envConf.getResourcePort()+"/api/authors?page="+page)
                .header("Authorization", "Bearer "+token)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(new ParameterizedTypeReference<List<Author>>(){})
                .getBody();
    }

    @Override
    public List<Author> findAuthorAndPage(String searchPhrase, int page) {
        return restClient.get()
                .uri("http://"+envConf.getResourceHostname()+":"+envConf.getResourcePort()+"/api/authors?page="+page+"&search="+searchPhrase)
                .header("Authorization", "Bearer "+token)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(new ParameterizedTypeReference<List<Author>>(){})
                .getBody();
    }

    @Override
    public Author findById(long id) {
        return restClient.get()
                .uri("http://"+envConf.getResourceHostname()+":"+envConf.getResourcePort()+"/api/authors/"+id)
                .header("Authorization", "Bearer "+token)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(Author.class);
    }

    @Override
    public Author addAuthor(Author author) {
        return restClient.post()
                .uri("http://"+envConf.getResourceHostname()+":"+envConf.getResourcePort()+"/api/authors")
                .header("Authorization", "Bearer "+token)
                .contentType(MediaType.APPLICATION_JSON)
                .body(author)
                .retrieve()
                .toEntity(Author.class).getBody();
    }

    @Override
    public Integer getMatchingAuthorsNumber(String searchPhrase) {
        var query = restClient.get();
        if(searchPhrase == null)
            query.uri("http://"+envConf.getResourceHostname()+":"+envConf.getResourcePort()+"/api/authors/number");
        else
            query.uri("http://"+envConf.getResourceHostname()+":"+envConf.getResourcePort()+"/api/authors/number?search="+searchPhrase);
        Integer result = query.header("Authorization", "Bearer "+token)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(Integer.class);
        return (result!=null)?result:0;
    }

    @Override
    public List<Book> findAllBooksOfAuthor(long authorId) {
        return restClient.get()
                .uri("http://"+envConf.getResourceHostname()+":"+envConf.getResourcePort()+"/api/authors/"+authorId+"/books")
                .header("Authorization", "Bearer "+token)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<List<Book>>(){});
    }
}
