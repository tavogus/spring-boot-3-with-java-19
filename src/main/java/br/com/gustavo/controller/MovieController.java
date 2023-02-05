package br.com.gustavo.controller;

import br.com.gustavo.dto.MovieDTO;
import br.com.gustavo.model.Movie;
import br.com.gustavo.services.MovieService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/movie",  produces = MediaType.APPLICATION_JSON_VALUE )
public class MovieController {

    @Autowired
    private MovieService service;

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<MovieDTO>>> findAll(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                                     @RequestParam(value = "size", defaultValue = "12") Integer size,
                                                                     @RequestParam(value = "direction", defaultValue = "asc") String direction) {
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "title"));
        return ResponseEntity.ok(service.findAll(pageable));
    }

    @GetMapping(value = "/filter")
    public ResponseEntity<PagedModel<EntityModel<MovieDTO>>> findByFilter(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                                          @RequestParam(value = "size", defaultValue = "12") Integer size,
                                                                          @RequestParam(value = "direction", defaultValue = "asc") String direction,
                                                                          @RequestParam(value = "filter") String filter) {
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "title"));
        return ResponseEntity.ok(service.findByFilter(filter, pageable));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<MovieDTO> findById(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<MovieDTO> addMovie(@Valid @RequestBody Movie movie) {
        return ResponseEntity.ok(service.save(movie));
    }
}
