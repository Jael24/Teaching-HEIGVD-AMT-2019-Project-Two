package ch.heigvd.amt.project.two.repositories;

import ch.heigvd.amt.project.two.entities.ActorEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;


public interface ActorRepository extends CrudRepository<ActorEntity, Long>{
}
