package ch.heigvd.amt.project.two.auth.services;

import ch.heigvd.amt.project.two.auth.api.exceptions.NotFoundException;
import ch.heigvd.amt.project.two.auth.configuration.JwtTokenUtil;
import ch.heigvd.amt.project.two.auth.entities.UserEntity;
import ch.heigvd.amt.project.two.auth.model.JWTToken;
import ch.heigvd.amt.project.two.auth.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JwtAuthService {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    private UserService userService;

    public JwtAuthService(UserService userService) {
        this.userService = userService;
    }

    public JWTToken checkUserAndPass(String username, String password, UserEntity user) {
        if (user.getEmail().equals(username) && user.getPassword().equals(password)) {
            String token = jwtTokenUtil.generateToken(user);
            JWTToken jwtToken = new JWTToken();
            jwtToken.token(token);
            return jwtToken;
        }
        return null;
    }
}