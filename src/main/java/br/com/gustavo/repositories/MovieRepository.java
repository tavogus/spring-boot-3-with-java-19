package br.com.gustavo.repositories;

import br.com.gustavo.model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Long>, PagingAndSortingRepository<Movie, Long> {
    Optional<Movie> findByTitle(String title);

    @Query("select m " +
            "from Movie m " +
            "join m.actors a " +
            "where " +
            "(:filter is null or m.title like %:filter% or m.category.name like %:filter% or a.name like %:filter%)")
    Page<Movie> findByFilter(@Param("filter") String filter, Pageable page);
}
