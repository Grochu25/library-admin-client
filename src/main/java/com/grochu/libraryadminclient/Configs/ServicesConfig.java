package com.grochu.libraryadminclient.Configs;

import com.grochu.libraryadminclient.Interfaces.BookService;
import com.grochu.libraryadminclient.Services.RestBookService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.context.annotation.RequestScope;

@Configuration
public class ServicesConfig
{
    @Bean
    @RequestScope
    public BookService bookService(OAuth2AuthorizedClientService oAuth2AuthorizedClientService)
    {
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

        return new RestBookService(token);
    }
}
