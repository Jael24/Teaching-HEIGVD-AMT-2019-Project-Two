package ch.heigvd.amt.project.two.entities;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "chars")
public class CharacterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_actor")
    private ActorEntity actor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_movie")
    private MovieEntity movie;

    @Column
    private String charName;
}
