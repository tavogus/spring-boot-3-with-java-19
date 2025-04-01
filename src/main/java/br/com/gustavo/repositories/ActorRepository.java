package br.com.gustavo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gustavo.model.Actor;

public interface ActorRepository extends JpaRepository<Actor, Long> {
}
