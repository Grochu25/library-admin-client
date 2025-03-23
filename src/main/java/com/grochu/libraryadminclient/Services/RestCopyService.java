package com.grochu.libraryadminclient.Services;

import com.grochu.libraryadminclient.Configs.EnvironmentalConfig;
import com.grochu.libraryadminclient.Domain.Borrow;
import com.grochu.libraryadminclient.Domain.Copy;
import com.grochu.libraryadminclient.Domain.User;
import com.grochu.libraryadminclient.Interfaces.CopyService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

import java.util.List;

public class RestCopyService implements CopyService
{
    private RestClient restClient;
    private String token;
    private EnvironmentalConfig envConf;

    public RestCopyService(String token, EnvironmentalConfig envConf)
    {
        restClient = RestClient.create();
        this.token = token;
        this.envConf = envConf;
    }

    @Override
    public List<Copy> findAllCopiesOfBook(long bookId)
    {
        return restClient.get()
                .uri("http://"+envConf.getResourceHostname()+":"+envConf.getResourcePort()+"/api/copies/"+bookId+"/all")
                .header("Authorization", "Bearer " + token)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(new ParameterizedTypeReference<List<Copy>>() {
                }).getBody();
    }

    @Override
    public List<Copy> findAvailableCopiesOfBook(long bookId)
    {
        return restClient.get()
                .uri("http://"+envConf.getResourceHostname()+":"+envConf.getResourcePort()+"/api/copies/"+bookId+"/available")
                .header("Authorization", "Bearer " + token)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(new ParameterizedTypeReference<List<Copy>>() {
                }).getBody();
    }

    @Override
    public List<Copy> findDestroyedCopiesOfBook(long bookId)
    {
        return restClient.get()
                .uri("http://"+envConf.getResourceHostname()+":"+envConf.getResourcePort()+"/api/copies/"+bookId+"/destroyed")
                .header("Authorization", "Bearer " + token)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(new ParameterizedTypeReference<List<Copy>>() {
                }).getBody();
    }

    @Override
    public Copy findById(long id) {
        return restClient.get()
                .uri("http://"+envConf.getResourceHostname()+":"+envConf.getResourcePort()+"/api/copies/"+id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(Copy.class);
    }

    @Override
    public Copy addCopy(Copy copy)
    {
        return restClient.post()
                .uri("http://"+envConf.getResourceHostname()+":"+envConf.getResourcePort()+"/api/copies")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .body(copy)
                .retrieve()
                .toEntity(Copy.class).getBody();
    }

    @Override
    public Borrow actualBorrowOfCopy(long id) {
        return restClient.get()
                .uri("http://"+envConf.getResourceHostname()+":"+envConf.getResourcePort()+"/api/borrows/copy/"+id)
                .header("Authorization", "Bearer " + token)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(Borrow.class);
    }

    @Override
    public void deleteCopy(long copyId)
    {
        restClient.delete()
                .uri("http://"+envConf.getResourceHostname()+":"+envConf.getResourcePort()+"/api/copies?copyId="+copyId)
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .toBodilessEntity();
    }

    @Override
    public Borrow returnCopyNow(Copy copy) {
        return restClient.post()
                .uri("http://"+envConf.getResourceHostname()+":"+envConf.getResourcePort()+"/api/copies/return")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .body(copy)
                .retrieve()
                .toEntity(Borrow.class).getBody();
    }

    @Override
    public Borrow borrowCopyNow(Copy copy, User user) {
        Borrow borrow = new Borrow();
        borrow.setUser(user);
        borrow.setCopy(copy);
        return restClient.post()
                .uri("http://"+envConf.getResourceHostname()+":"+envConf.getResourcePort()+"/api/copies/borrow")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .body(borrow)
                .retrieve()
                .toEntity(Borrow.class).getBody();
    }
}
