package ch.heigvd.amt.project.two.api.endpoints;

import ch.heigvd.amt.project.two.api.ActorsApi;
import ch.heigvd.amt.project.two.api.exceptions.NotFoundException;
import ch.heigvd.amt.project.two.configuration.JwtTokenUtil;
import ch.heigvd.amt.project.two.entities.ActorEntity;
import ch.heigvd.amt.project.two.model.Actor;
import ch.heigvd.amt.project.two.repositories.ActorRepository;
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
import java.util.Optional;

@Controller
public class ActorsApiController implements ActorsApi {

    @Autowired
    ActorRepository actorRepository;

    public ResponseEntity<Object> createActor(@ApiParam(value = "" ,required=true) @RequestHeader(value="Authorization", required=true) String authorization,@ApiParam(value = "" ,required=true )  @Valid @RequestBody Actor actor) {
        ActorEntity newActorEntity = toActorEntity(actor);
        newActorEntity.setOwner(JwtTokenUtil.getEmailFromToken(authorization));
        actorRepository.save(newActorEntity);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(newActorEntity.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    public ResponseEntity<List<Actor>> getActors() {
        List<Actor> actors = new ArrayList<>();
        for (ActorEntity actorEntity : actorRepository.findAll()) {
            actors.add(toActor(actorEntity));
        }

        return ResponseEntity.ok(actors);
    }

    public ResponseEntity<Void> deleteActor(@ApiParam(value = "" ,required=true) @RequestHeader(value="Authorization", required=true) String authorization, @ApiParam(value = "The actor's id",required=true) @PathVariable("id") Long id) {
        if (!checkAuth(authorization,actorRepository.findById(id).orElse(null))) {
            return new ResponseEntity<>(HttpStatus.valueOf(401));
        }
        actorRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.valueOf(200));
    }

    public ResponseEntity<Void> modifyActor(@ApiParam(value = "" ,required=true) @RequestHeader(value="Authorization", required=true) String authorization,@ApiParam(value = "The actor's id",required=true) @PathVariable("id") Long id,@ApiParam(value = "" ,required=true )  @Valid @RequestBody Actor actor) {
        ActorEntity actorEntity = toActorEntity(actor);

        if (!checkAuth(authorization,actorEntity)) {
            return new ResponseEntity<>(HttpStatus.valueOf(401));
        }

        if(actorRepository.findById(id).isPresent()) {
            actorEntity.setId(id);
            actorRepository.save(actorEntity);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<List<Actor>> getActor(@ApiParam(value = "" ,required=true) @RequestHeader(value="Authorization", required=true) String authorization,@ApiParam(value = "The actor's id",required=true) @PathVariable("id") Long id) {
        ActorEntity actorEntity = actorRepository.findById(id).orElse(null);

        if (!checkAuth(authorization,actorEntity)) {
            return new ResponseEntity<>(HttpStatus.valueOf(401));
        }

        List<Actor> actors = new ArrayList<>();
        actors.add(toActor(actorEntity));

        return ResponseEntity.ok(actors);
    }

    private boolean checkAuth(String token, ActorEntity actor) {
        String emailOwnerFromToken = JwtTokenUtil.getEmailFromToken(token);

        if (actor == null)
            return false;

        return emailOwnerFromToken.equals(actor.getOwner());
    }

    private ActorEntity toActorEntity(Actor actor) {
        ActorEntity entity = new ActorEntity();
        entity.setEmail(actor.getEmail());
        entity.setFullname(actor.getFullname());
        entity.setOwner(actor.getOwner());
        return entity;
    }

    private Actor toActor(ActorEntity entity) {
        Actor actor = new Actor();
        entity.setEmail(actor.getEmail());
        entity.setFullname(actor.getFullname());
        entity.setOwner(actor.getOwner());
        return actor;
    }

}
