package br.com.gustavo.services;

import br.com.gustavo.exceptions.ResourceNotFoundException;
import br.com.gustavo.model.Category;
import br.com.gustavo.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    public Category findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No category found for this ID!"));
    }
}
