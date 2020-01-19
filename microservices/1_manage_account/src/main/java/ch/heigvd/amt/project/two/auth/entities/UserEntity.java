package ch.heigvd.amt.project.two.auth.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "user")
@Getter
@Setter
public class UserEntity implements Serializable {

    @Id
    @Setter
    @Getter
    @Column(nullable = false, unique = true)
    private String email;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private String password;
    @Column
    private boolean isBlocked;
    @Column
    private boolean isAdmin;
    @Column
    private boolean isValidated;

}
