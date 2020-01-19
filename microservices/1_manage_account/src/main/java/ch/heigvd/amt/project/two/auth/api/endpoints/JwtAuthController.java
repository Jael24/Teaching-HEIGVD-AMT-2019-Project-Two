package ch.heigvd.amt.project.two.auth.api.endpoints;

import ch.heigvd.amt.project.two.auth.api.LoginApi;
import ch.heigvd.amt.project.two.auth.entities.UserEntity;
import ch.heigvd.amt.project.two.auth.model.InlineObject1;
import ch.heigvd.amt.project.two.auth.model.JWTToken;
import ch.heigvd.amt.project.two.auth.repositories.UserRepository;
import ch.heigvd.amt.project.two.auth.services.JwtAuthService;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class JwtAuthController implements LoginApi {

    @Autowired
    UserRepository userRepository;
    @Autowired
    private JwtAuthService jwtAuthService;

    public ResponseEntity<Object> login(@ApiParam(value = "" ,required=true )  @Valid @RequestBody InlineObject1 login) {
        JWTToken jwtToken = new JWTToken();

        UserEntity userInDB = userRepository.findById(login.getEmail()).orElse(null);

        if (userInDB != null) {
            if (userInDB.isBlocked()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            jwtToken = jwtAuthService.checkUserAndPass(login.getEmail(), login.getPassword(), userInDB);
        }

        return ResponseEntity.ok(jwtToken.getToken());
    }
}