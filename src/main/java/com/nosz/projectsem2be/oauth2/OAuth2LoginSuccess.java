package com.nosz.projectsem2be.oauth2;

import com.nosz.projectsem2be.entity.AuthProvider;
import com.nosz.projectsem2be.entity.Role;
import com.nosz.projectsem2be.entity.RoleName;
import com.nosz.projectsem2be.entity.User;
import com.nosz.projectsem2be.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Component
public class OAuth2LoginSuccess extends SimpleUrlAuthenticationSuccessHandler {
    @Autowired
    private UserService userService;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        DefaultOidcUser oAuth2User = (DefaultOidcUser) authentication.getPrincipal();
        String email =oAuth2User.getEmail();
        String name = oAuth2User.getName();
        User user = userService.getUser(email);
        if(user == null){
            userService.createNewUserOAuthLogin(email,name, AuthProvider.google);
        }

        super.onAuthenticationSuccess(request, response, authentication);

    }
}
