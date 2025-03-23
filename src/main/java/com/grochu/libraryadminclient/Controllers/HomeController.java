package com.grochu.libraryadminclient.Controllers;

import com.grochu.libraryadminclient.Configs.EnvironmentalConfig;
import com.grochu.libraryadminclient.Interfaces.CustomerService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController
{
    private final EnvironmentalConfig envConfig;
    private final CustomerService customerService;

    @GetMapping
    public String getHomeScreen()
    {
        return "pages/index";
    }

    @GetMapping("/login")
    public String login()
    {
        if(SecurityContextHolder.getContext().getAuthentication() != null && !SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser")) {
            try{
                customerService.getPage(1);
                return "redirect:/";
            }catch (Exception e) {
                log.error(e.getMessage());
                return "redirect:/logout";
            }
        }
        return "";
    }

    @GetMapping("/logout")
    public String logaout(Authentication authentication, HttpSession session){
        if(SecurityContextHolder.getContext().getAuthentication() != null &&
                !SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser")) {
            OAuth2AuthenticationToken oauth2Auth = (OAuth2AuthenticationToken) authentication;
            String idToken = ((DefaultOidcUser) oauth2Auth.getPrincipal()).getIdToken().getTokenValue();
            session.invalidate();
            return "redirect:http://"+envConfig.getAuthHostname()+":"+envConfig.getAuthPort()+"/connect/logout?id_token_hint="+idToken+"&client_id=library-admin-client&post_logout_redirect_uri=http://"+envConfig.getAdminHostname()+":"+envConfig.getAdminPort();
        }
        return "redirect:/";
    }
}
