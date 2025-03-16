package com.grochu.libraryadminclient.Services;

import com.grochu.libraryadminclient.DAL.Borrow;
import com.grochu.libraryadminclient.DAL.User;
import com.grochu.libraryadminclient.Interfaces.CustomerService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

import java.util.List;

public class RestCustomerService implements CustomerService
{
    private RestClient restClient;
    private String token;

    public RestCustomerService(String token)
    {
        restClient = RestClient.create();
        this.token = token;
    }

    @Override
    public List<User> getPage(int page) {
        return restClient.get()
                .uri("http://localhost:8080/api/users?page="+page)
                .header("Authorization", "Bearer " + token)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<List<User>>(){});
    }

    @Override
    public List<User> findCustomerAndPage(String searchPhrase, int page) {
        return restClient.get()
                .uri("http://localhost:8080/api/users?page="+page+"&search="+searchPhrase)
                .header("Authorization", "Bearer " + token)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<List<User>>(){});
    }

    @Override
    public User getCustomerById(long id) {
        return restClient.get()
                .uri("http://localhost:8080/api/users/"+id)
                .header("Authorization", "Bearer " + token)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(User.class);
    }

    @Override
    public List<Borrow> getBorrowedCopiesForUser(long userId) {
        return restClient.get()
                .uri("http://localhost:8080/api/users/"+userId+"/borrows/all")
                .header("Authorization", "Bearer " + token)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<List<Borrow>>(){});
    }

    @Override
    public Integer getAllMatchingCustomersNumber(String searchPhrase) {
        var query = restClient.get();
        if(searchPhrase == null)
            query.uri("http://localhost:8080/api/users/number");
        else
            query.uri("http://localhost:8080/api/users/number?search="+searchPhrase);
        Integer result =query.header("Authorization", "Bearer " + token)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(Integer.class);
        return result != null ? result : 0;
    }
}
