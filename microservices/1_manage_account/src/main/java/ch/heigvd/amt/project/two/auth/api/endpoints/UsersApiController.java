package ch.heigvd.amt.project.two.auth.api.endpoints;

import ch.heigvd.amt.project.two.auth.api.UsersApi;
import ch.heigvd.amt.project.two.auth.configuration.JwtTokenUtil;
import ch.heigvd.amt.project.two.auth.entities.UserEntity;
import ch.heigvd.amt.project.two.auth.model.InlineObject;
import ch.heigvd.amt.project.two.auth.model.User;
import ch.heigvd.amt.project.two.auth.repositories.UserRepository;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UsersApiController implements UsersApi {

    @Autowired
    UserRepository userRepository;

    public ResponseEntity<Object> createUser(@ApiParam(value = "", required = true) @Valid @RequestBody User user) {
        UserEntity newUserEntity = toUserEntity(user);
        userRepository.save(newUserEntity);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(newUserEntity.getEmail()).toUri();

        return ResponseEntity.created(location).build();
    }

    public ResponseEntity<List<User>> getUsers(@ApiParam(value = "" ,required=true) @RequestHeader(value="Authorization", required=true) String authorization) {
        List<User> users = new ArrayList<>();
        for (UserEntity userEntity : userRepository.findAll()) {
            users.add(toUser(userEntity));
        }

        return ResponseEntity.ok(users);
    }

    public ResponseEntity<Void> modifyData(@ApiParam(value = "" ,required=true) @RequestHeader(value="Authorization", required=true) String authorization,@ApiParam(value = "The user's id",required=true) @PathVariable("id") String id,@ApiParam(value = ""  )  @Valid @RequestBody InlineObject patchUser) {
        String emailFromToken = JwtTokenUtil.getEmailFromToken(authorization);
        Boolean isAdminFromToken = JwtTokenUtil.getIsAdminFromToken(authorization);

        if (emailFromToken.equals(id) || isAdminFromToken) {
            if (userRepository.findById(id).isPresent()) {
                UserEntity userEntity = userRepository.findById(id).get();
                userEntity.setEmail(id);
                if (patchUser.getNewPass() != null)
                    userEntity.setPassword(patchUser.getNewPass());
                if (patchUser.getIsBlocked() != null) {
                    if (isAdminFromToken) {
                        userEntity.setBlocked(patchUser.getIsBlocked());
                    } else {
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
                    }
                }
                if (patchUser.getIsValidated() != null)
                    userEntity.setValidated(patchUser.getIsValidated());
                userRepository.save(userEntity);
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    private UserEntity toUserEntity(User user) {
        UserEntity entity = new UserEntity();
        entity.setEmail(user.getEmail());
        entity.setAdmin(user.getIsAdmin());
        entity.setBlocked(user.getIsBlocked());
        entity.setValidated(user.getIsValidated());
        entity.setFirstName(user.getFirstName());
        entity.setLastName(user.getLastName());
        entity.setPassword(user.getPassword());
        return entity;
    }

    private User toUser(UserEntity entity) {
        User user = new User();
        user.setEmail(entity.getEmail());
        user.setIsAdmin(entity.isAdmin());
        user.setIsBlocked(entity.isBlocked());
        user.setIsValidated(entity.isValidated());
        user.setFirstName(entity.getFirstName());
        user.setLastName(entity.getLastName());
        user.setPassword(entity.getPassword());
        return user;
    }

}
