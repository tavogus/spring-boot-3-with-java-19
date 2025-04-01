package br.com.gustavo.services;

import br.com.gustavo.exceptions.ResourceNotFoundException;
import br.com.gustavo.model.Category;
import br.com.gustavo.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    public Category findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No category found for this ID!"));
    }
}
