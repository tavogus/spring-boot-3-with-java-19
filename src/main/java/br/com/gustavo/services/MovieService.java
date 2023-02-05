package br.com.gustavo.services;

import br.com.gustavo.controller.MovieController;
import br.com.gustavo.dto.MovieDTO;
import br.com.gustavo.exceptions.BusinessException;
import br.com.gustavo.exceptions.RequiredObjectIsNullException;
import br.com.gustavo.exceptions.ResourceNotFoundException;
import br.com.gustavo.mapper.ModelMapper;
import br.com.gustavo.model.Movie;
import br.com.gustavo.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class MovieService {

    private Logger logger = Logger.getLogger(MovieService.class.getName());

    @Autowired
    private MovieRepository repository;

    @Autowired
    private CategoryService categoryService;
    @Autowired
    PagedResourcesAssembler<MovieDTO> assembler;
    public PagedModel<EntityModel<MovieDTO>> findAll(Pageable pageable) {
        logger.info("Finding all movies!");

        var moviesPage = repository.findAll(pageable);

        var moviesDtos = moviesPage.map(p -> ModelMapper.parseObject(p, MovieDTO.class));
        moviesDtos.map(p -> p.add(linkTo(methodOn(MovieController.class).findById(p.getId())).withSelfRel()));

        Link findAllLink = linkTo(
                methodOn(MovieController.class)
                        .findAll(pageable.getPageNumber(),
                                 pageable.getPageSize(),
                                "asc")).withSelfRel();

        return assembler.toModel(moviesDtos, findAllLink);
    }

    public PagedModel<EntityModel<MovieDTO>> findByFilter(String filter, Pageable pageable) {
        logger.info("Finding movies by filter!");

        var moviesPage = repository.findByFilter(filter, pageable);

        var moviesDtos = moviesPage.map(p -> ModelMapper.parseObject(p, MovieDTO.class));
        moviesDtos.map(p -> p.add(linkTo(methodOn(MovieController.class).findById(p.getId())).withSelfRel()));

        Link findByFilterLink = linkTo(
                methodOn(MovieController.class)
                        .findByFilter(pageable.getPageNumber(),
                                      pageable.getPageSize(),
                                      "asc",
                                      filter)
        ).withSelfRel();

        return assembler.toModel(moviesDtos, findByFilterLink);
    }

    public MovieDTO findById(Long id) {
        logger.info("Finding one movie!");

        var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        var dto = ModelMapper.parseObject(entity, MovieDTO.class);
        dto.add(linkTo(methodOn(MovieController.class).findById(id)).withSelfRel());
        return dto;
    }

    public MovieDTO save(Movie movie) {
        if (movie == null) throw new RequiredObjectIsNullException();

        logger.info("Creating new Movie!");

        Optional<Movie> existingMovie = repository.findByTitle(movie.getTitle());
        existingMovie.ifPresent(em -> {
            throw new BusinessException("Already Exists a movie with this title!");
        });

        categoryService.findById(movie.getCategory().getId()).orElseThrow(() -> new ResourceNotFoundException("No category found for this ID!"));

        var dto = ModelMapper.parseObject(repository.save(movie), MovieDTO.class);
        dto.add(linkTo(methodOn(MovieController.class).findById(dto.getId())).withSelfRel());
        return dto;
    }
}
