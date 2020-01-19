package ch.heigvd.amt.project.two.api.endpoints;

import ch.heigvd.amt.project.two.api.MoviesApi;
import ch.heigvd.amt.project.two.configuration.JwtTokenUtil;
import ch.heigvd.amt.project.two.entities.MovieEntity;
import ch.heigvd.amt.project.two.model.Movie;
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
public class MovieApiController implements MoviesApi {

    @Autowired
    MovieRepository movieRepository;

    @Override
    public ResponseEntity<Object> createMovie(String authorization, @Valid Movie movie) {
        MovieEntity newMovieEntity = toMovieEntity(movie);
        newMovieEntity.setOwner(JwtTokenUtil.getEmailFromToken(authorization));
        movieRepository.save(newMovieEntity);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(newMovieEntity.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @Override
    public ResponseEntity<Void> deleteMovie(String authorization, Long id) {
        if (!checkAuth(authorization, movieRepository.findById(id).orElse(null))) {
            return new ResponseEntity<>(HttpStatus.valueOf(401));
        }
        movieRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.valueOf(200));
    }

    @Override
    public ResponseEntity<List<Movie>> getMovie(String authorization, Long id) {
        MovieEntity movieEntity = movieRepository.findById(id).orElse(null);

        if (!checkAuth(authorization, movieEntity)) {
            return new ResponseEntity<>(HttpStatus.valueOf(401));
        }

        List<Movie> movies = new ArrayList<>();
        movies.add(toMovie(movieEntity));

        return ResponseEntity.ok(movies);
    }

    @Override
    public ResponseEntity<List<Movie>> getMovies() {
        List<Movie> movies = new ArrayList<>();
        for (MovieEntity movieEntity : movieRepository.findAll()) {
            movies.add(toMovie(movieEntity));
        }

        return ResponseEntity.ok(movies);
    }

    @Override
    public ResponseEntity<Void> putMovie(String authorization, Long id, @Valid Movie movie) {
        MovieEntity movieEntity = toMovieEntity(movie);

        if (!checkAuth(authorization, movieEntity)) {
            return new ResponseEntity<>(HttpStatus.valueOf(401));
        }

        if(movieRepository.findById(id).isPresent()) {
            movieEntity.setId(id);
            movieRepository.save(movieEntity);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private boolean checkAuth(String token, MovieEntity movie) {
        String emailOwnerFromToken = JwtTokenUtil.getEmailFromToken(token);

        if (movie == null)
            return false;

        return emailOwnerFromToken.equals(movie.getOwner());
    }

    private MovieEntity toMovieEntity(Movie movie) {
        MovieEntity entity = new MovieEntity();
        entity.setOwner(movie.getOwner());
        entity.setTitle(movie.getTitle());
        return entity;
    }

    private Movie toMovie(MovieEntity entity) {
        Movie movie = new Movie();
        entity.setOwner(movie.getOwner());
        entity.setTitle(movie.getTitle());
        return movie;
    }
}
