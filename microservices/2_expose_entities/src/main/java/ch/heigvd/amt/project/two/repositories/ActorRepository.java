package ch.heigvd.amt.project.two.repositories;

import ch.heigvd.amt.project.two.entities.ActorEntity;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface ActorRepository extends CrudRepository<ActorEntity, Long>{

    List<ActorEntity> findAllById(int id);

    // used to get data in a pageable fashion
    @Cacheable("actorListPaged")
    List<ActorEntity> findById(int id, Pageable pageable);

    @Cacheable("actorCount")
    long countById(int userId);
}
