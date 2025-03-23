package com.grochu.libraryadminclient.Configs;

import com.grochu.libraryadminclient.Interfaces.*;
import com.grochu.libraryadminclient.Services.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.context.annotation.RequestScope;

@Slf4j
@Configuration
public class ServicesConfig
{

    public String getBearerToken(OAuth2AuthorizedClientService oAuth2AuthorizedClientService){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String token = null;

        if(authentication.getClass().isAssignableFrom(OAuth2AuthenticationToken.class))
        {
            OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
            String clientId = oauthToken.getAuthorizedClientRegistrationId();

            if(clientId.equals("library-admin-client"))
            {
                OAuth2AuthorizedClient client = oAuth2AuthorizedClientService.loadAuthorizedClient(clientId, oauthToken.getName());
                token = client.getAccessToken().getTokenValue();
            }
        }
        return token;
    }

    @Bean
    @RequestScope
    public BookService bookService(OAuth2AuthorizedClientService oAuth2AuthorizedClientService, EnvironmentalConfig environmentalConfig)
    {
        String token = getBearerToken(oAuth2AuthorizedClientService);
        return new RestBookService(token, environmentalConfig);
    }

    @Bean
    @RequestScope
    public CopyService copyService(OAuth2AuthorizedClientService oAuth2AuthorizedClientService, EnvironmentalConfig environmentalConfig)
    {
        String token = getBearerToken(oAuth2AuthorizedClientService);
        return new RestCopyService(token, environmentalConfig);
    }

    @Bean
    @RequestScope
    public AuthorService authorService(OAuth2AuthorizedClientService oAuth2AuthorizedClientService, EnvironmentalConfig environmentalConfig)
    {
        String token = getBearerToken(oAuth2AuthorizedClientService);
        return new RestAuthorService(token, environmentalConfig);
    }

    @Bean
    @RequestScope
    public CustomerService customerService(OAuth2AuthorizedClientService oAuth2AuthorizedClientService, EnvironmentalConfig environmentalConfig)
    {
        String token = getBearerToken(oAuth2AuthorizedClientService);
        return new RestCustomerService(token, environmentalConfig);
    }
}
