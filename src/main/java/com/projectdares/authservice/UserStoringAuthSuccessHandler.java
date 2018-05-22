package com.projectdares.authservice;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.common.hash.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class UserStoringAuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private UserRepository userRepository;

    @Autowired
    public UserStoringAuthSuccessHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        super.onAuthenticationSuccess(request, response, authentication);
        Object principal = authentication.getPrincipal();
        if (principal instanceof OAuth2User) {
            Map<String, Object> attributes = ((OAuth2User) principal).getAttributes();
            String id = Hashing.sha256().hashString(attributes.get("email").toString(), StandardCharsets.UTF_8).toString();
            UserEntity userEntity =
                    new UserEntity(id, attributes.get("picture").toString(), attributes.get("name").toString());
            userRepository.save(userEntity);
        }
    }
}
