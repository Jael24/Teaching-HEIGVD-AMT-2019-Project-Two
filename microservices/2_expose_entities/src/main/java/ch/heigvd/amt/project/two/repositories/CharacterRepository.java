package ch.heigvd.amt.project.two.repositories;

import ch.heigvd.amt.project.two.entities.CharacterEntity;
import org.springframework.data.repository.CrudRepository;

public interface CharacterRepository extends CrudRepository<CharacterEntity, Long> {
}
