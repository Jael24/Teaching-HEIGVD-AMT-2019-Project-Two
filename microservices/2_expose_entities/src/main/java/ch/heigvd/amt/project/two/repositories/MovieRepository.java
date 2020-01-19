package ch.heigvd.amt.project.two.repositories;

import ch.heigvd.amt.project.two.entities.MovieEntity;
import org.springframework.data.repository.CrudRepository;

public interface MovieRepository extends CrudRepository<MovieEntity, Long> {
}
