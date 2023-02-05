package br.com.gustavo.repositories;

import br.com.gustavo.model.TvShow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TvShowRepository extends JpaRepository<TvShow, Long> {
    Optional<TvShow> findByTitle(String title);
}
