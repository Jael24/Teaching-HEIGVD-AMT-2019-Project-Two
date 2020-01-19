package ch.heigvd.amt.project.two.api.endpoints;

import ch.heigvd.amt.project.two.api.CharacterApi;
import ch.heigvd.amt.project.two.configuration.JwtTokenUtil;
import ch.heigvd.amt.project.two.entities.CharacterEntity;
import ch.heigvd.amt.project.two.model.Character;
import ch.heigvd.amt.project.two.repositories.ActorRepository;
import ch.heigvd.amt.project.two.repositories.CharacterRepository;
import ch.heigvd.amt.project.two.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CharactersApiController implements CharacterApi {

    @Autowired
    CharacterRepository characterRepository;
    @Autowired
    MovieRepository movieRepository;
    @Autowired
    ActorRepository actorRepository;

    @Override
    public ResponseEntity<Object> createCharacter(String authorization, @Valid Character character) {
        CharacterEntity newCharacterEntity = toCharacterEntity(character);
        newCharacterEntity.setOwner(JwtTokenUtil.getEmailFromToken(authorization));
        characterRepository.save(newCharacterEntity);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(newCharacterEntity.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @Override
    public ResponseEntity<Void> deleteCharacter(String authorization, Long id) {
        if (!checkAuth(authorization, characterRepository.findById(id).orElse(null))) {
            return new ResponseEntity<>(HttpStatus.valueOf(401));
        }
        characterRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.valueOf(200));
    }

    @Override
    public ResponseEntity<List<Character>> getCharacters() {
        List<Character> characters = new ArrayList<>();
        for (CharacterEntity actorEntity : characterRepository.findAll()) {
            characters.add(toCharacter(actorEntity));
        }
        return ResponseEntity.ok(characters);
    }

    private boolean checkAuth(String token, CharacterEntity character) {
        String emailOwnerFromToken = JwtTokenUtil.getEmailFromToken(token);

        if (character == null)
            return false;

        return emailOwnerFromToken.equals(character.getOwner());
    }

    private CharacterEntity toCharacterEntity(Character character) {
        CharacterEntity entity = new CharacterEntity();
        entity.setMovie(movieRepository.findById(character.getMovie()).get());
        entity.setActor(actorRepository.findById(character.getActor()).get());
        entity.setOwner(character.getOwner());
        entity.setCharName(character.getCharName());
        return entity;
    }

    private Character toCharacter(CharacterEntity entity) {
        Character character = new Character();
        character.setActor(entity.getActor().getId());
        character.setMovie(entity.getMovie().getId());
        character.setCharName(entity.getCharName());
        character.setOwner(entity.getOwner());
        return character;
    }
}
