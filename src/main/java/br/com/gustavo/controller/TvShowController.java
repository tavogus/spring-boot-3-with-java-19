package br.com.gustavo.controller;

import br.com.gustavo.dto.TvShowDTO;
import br.com.gustavo.model.TvShow;
import br.com.gustavo.services.TvShowService;
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
@RequestMapping(value = "/api/tvshow",  produces = MediaType.APPLICATION_JSON_VALUE )
public class TvShowController {

    @Autowired
    private TvShowService service;

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<TvShowDTO>>> findAll(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                                     @RequestParam(value = "size", defaultValue = "12") Integer size,
                                                                     @RequestParam(value = "direction", defaultValue = "asc") String direction) {
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "title"));
        return ResponseEntity.ok(service.findAll(pageable));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<TvShowDTO> findById(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<TvShowDTO> addMovie(@Valid @RequestBody TvShow tvShow) {
        return ResponseEntity.ok(service.save(tvShow));
    }
}
