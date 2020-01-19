package ch.heigvd.amt.project.two.api.endpoints;

import ch.heigvd.amt.project.two.api.exceptions.NotFoundException;
import ch.heigvd.amt.project.two.configuration.JwtTokenUtil;
import ch.heigvd.amt.project.two.entities.ActorEntity;
import ch.heigvd.amt.project.two.model.Actor;
import ch.heigvd.amt.project.two.repositories.ActorRepository;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.stream.Collectors;

@Controller
public class ActorsApiController {

    @Autowired
    ActorRepository actorRepository;

    @Autowired
    HttpServletRequest httpServletRequest;

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

    public ResponseEntity<List<Actor>> getActorByUser(@Valid Integer page, @Valid Integer pageSize) throws Exception {
        int userId = (Integer) httpServletRequest.getAttribute("user_id");

        long totalActorCount = actorRepository.countByUserId(userId);
        long lastPageNumber = totalMatchCount / pageSize + ((totalMatchCount % pageSize == 0) ? 0 : 1);

        StringJoiner linkHeader = new StringJoiner(", ");

        String str = constructPageUri(httpServletRequest.getRequestURI(), "next", page + 1, pageSize, lastPageNumber);
        if (str != "") {
            linkHeader.add(str);
        }
        if (page != lastPageNumber) {
            linkHeader.add(constructPageUri(httpServletRequest.getRequestURI(), "last", lastPageNumber, pageSize, lastPageNumber));
        }
        if (page != 1) {
            linkHeader.add(constructPageUri(httpServletRequest.getRequestURI(), "first", 1, pageSize, lastPageNumber));
        }

        str = constructPageUri(httpServletRequest.getRequestURI(), "prev", page - 1, pageSize, lastPageNumber);
        if (str != "") {
            linkHeader.add(str);
        }

        List<Actor> matches = actorRepository.findByUserId((Integer) httpServletRequest.getAttribute("user_id"), PageRequest.of(page-1, pageSize)).parallelStream()
                .map(ActorsApiController::toActor).collect(Collectors.toList());
        return ResponseEntity.ok().header(HttpHeaders.LINK, linkHeader.toString()).body(matches);
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

    private static ActorEntity toActorEntity(Actor actor) {
        ActorEntity entity = new ActorEntity();
        entity.setEmail(actor.getEmail());
        entity.setFullname(actor.getFullname());
        entity.setOwner(actor.getOwner());
        return entity;
    }

    private static Actor toActor(ActorEntity entity) {
        Actor actor = new Actor();
        entity.setEmail(actor.getEmail());
        entity.setFullname(actor.getFullname());
        entity.setOwner(actor.getOwner());
        return actor;
    }

}
