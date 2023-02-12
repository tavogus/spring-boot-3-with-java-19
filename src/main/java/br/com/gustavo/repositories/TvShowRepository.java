package br.com.gustavo.repositories;

import br.com.gustavo.model.TvShow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TvShowRepository extends JpaRepository<TvShow, Long> {
    Optional<TvShow> findByTitle(String title);

    @Query("select t " +
            "from TvShow t " +
            "join t.actors a " +
            "where " +
            "(:filter is null or t.title like %:filter% or t.category.name like %:filter% or a.name like %:filter%)")
    Page<TvShow> findByFilter(@Param("filter") String filter, Pageable page);
}
