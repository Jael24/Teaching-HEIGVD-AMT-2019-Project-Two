package ch.heigvd.amt.project.two.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Olivier Liechti on 26/07/17.
 */
@Entity
@Table(name = "actor")
public class ActorEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private long id;

    @Getter
    @Setter
    private String owner;

    @Column(nullable = false)
    @Getter
    @Setter
    private String email;
    @Getter
    @Setter
    @Column
    private String fullname;

}
