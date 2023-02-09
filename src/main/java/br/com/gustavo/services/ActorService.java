package br.com.gustavo.services;

import br.com.gustavo.exceptions.ResourceNotFoundException;
import br.com.gustavo.model.Actor;
import br.com.gustavo.repositories.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActorService {

    @Autowired
    private ActorRepository repository;

    public Actor findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No actor found for this ID!"));
    }
}
