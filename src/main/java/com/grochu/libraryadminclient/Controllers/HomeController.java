package com.grochu.libraryadminclient.Controllers;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class HomeController
{

    @GetMapping
    public String getHomeScreen()
    {
        log.info(SecurityContextHolder.getContext().getAuthentication().toString());
        log.warn(SecurityContextHolder.getContext().getAuthentication().getName());
        return "pages/index";
    }

    @GetMapping("/login")
    public String login()
    {
        if(SecurityContextHolder.getContext().getAuthentication() != null && !SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser"))
            return "redirect:/";
        return "";
    }

    @GetMapping("/logout")
    public String logaout(Authentication authentication, HttpSession session){
        if(SecurityContextHolder.getContext().getAuthentication() != null &&
                !SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser")) {
            OAuth2AuthenticationToken oauth2Auth = (OAuth2AuthenticationToken) authentication;
            String idToken = ((DefaultOidcUser) oauth2Auth.getPrincipal()).getIdToken().getTokenValue();
            session.invalidate();
            return "redirect:http://localhost:9000/connect/logout?id_token_hint="+idToken+"&client_id=library-admin-client&post_logout_redirect_uri=http://127.0.0.1:8000";
        }
        return "redirect:/";
    }
}
