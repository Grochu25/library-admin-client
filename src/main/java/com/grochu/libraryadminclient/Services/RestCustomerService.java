package com.grochu.libraryadminclient.Services;

import com.grochu.libraryadminclient.Configs.EnvironmentalConfig;
import com.grochu.libraryadminclient.Domain.Borrow;
import com.grochu.libraryadminclient.Domain.User;
import com.grochu.libraryadminclient.Interfaces.CustomerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

import java.util.List;

public class RestCustomerService implements CustomerService
{
    private RestClient restClient;
    private String token;
    private EnvironmentalConfig envConf;

    public RestCustomerService(String token, EnvironmentalConfig envConf)
    {
        restClient = RestClient.create();
        this.token = token;
        this.envConf = envConf;
    }

    @Override
    public List<User> getPage(int page) {
        return restClient.get()
                .uri("http://"+envConf.getResourceHostname()+":"+envConf.getResourcePort()+"/api/users?page="+page)
                .header("Authorization", "Bearer " + token)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<List<User>>(){});
    }

    @Override
    public List<User> findCustomerAndPage(String searchPhrase, int page) {
        return restClient.get()
                .uri("http://"+envConf.getResourceHostname()+":"+envConf.getResourcePort()+"/api/users?page="+page+"&search="+searchPhrase)
                .header("Authorization", "Bearer " + token)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<List<User>>(){});
    }

    @Override
    public User getCustomerById(long id) {
        return restClient.get()
                .uri("http://"+envConf.getResourceHostname()+":"+envConf.getResourcePort()+"/api/users/"+id)
                .header("Authorization", "Bearer " + token)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(User.class);
    }

    @Override
    public List<Borrow> getBorrowedCopiesForUser(long userId) {
        return restClient.get()
                .uri("http://"+envConf.getResourceHostname()+":"+envConf.getResourcePort()+"/api/users/"+userId+"/borrows/all")
                .header("Authorization", "Bearer " + token)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<List<Borrow>>(){});
    }

    @Override
    public Integer getAllMatchingCustomersNumber(String searchPhrase) {
        var query = restClient.get();
        if(searchPhrase == null)
            query.uri("http://"+envConf.getResourceHostname()+":"+envConf.getResourcePort()+"/api/users/number");
        else
            query.uri("http://"+envConf.getResourceHostname()+":"+envConf.getResourcePort()+"/api/users/number?search="+searchPhrase);
        Integer result =query.header("Authorization", "Bearer " + token)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(Integer.class);
        return result != null ? result : 0;
    }
}
