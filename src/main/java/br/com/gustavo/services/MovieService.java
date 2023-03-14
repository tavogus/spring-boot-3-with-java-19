package br.com.gustavo.services;

import br.com.gustavo.controller.MovieController;
import br.com.gustavo.dto.MovieDTO;
import br.com.gustavo.dto.MovieInputDTO;
import br.com.gustavo.exceptions.BusinessException;
import br.com.gustavo.exceptions.RequiredObjectIsNullException;
import br.com.gustavo.exceptions.ResourceNotFoundException;
import br.com.gustavo.mapper.ModelMapper;
import br.com.gustavo.model.Actor;
import br.com.gustavo.model.Category;
import br.com.gustavo.model.Movie;
import br.com.gustavo.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
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
    private ActorService actorService;
    @Autowired
    private S3Service s3Service;
    @Autowired
    PagedResourcesAssembler<MovieDTO> assembler;

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

        var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No movie found for this ID!"));
        var dto = ModelMapper.parseObject(entity, MovieDTO.class);
        dto.add(linkTo(methodOn(MovieController.class).findById(id)).withSelfRel());
        return dto;
    }

    public MovieDTO uploadMovie(MultipartFile file, Long movieId) {
        Movie movie = repository.findById(movieId).orElseThrow(() -> new ResourceNotFoundException("No movie found for this ID!"));

        String url = s3Service.uploadFile(file);
        movie.setUrl(url);

        var entity = repository.save(movie);
        var dto = ModelMapper.parseObject(entity, MovieDTO.class);
        dto.add(linkTo(methodOn(MovieController.class).findById(dto.getId())).withSelfRel());
        return dto;
    }

    public MovieDTO preSave(MovieInputDTO movieInput) {
        if (movieInput == null) throw new RequiredObjectIsNullException();

        logger.info("Creating new Movie!");

        Optional<Movie> existingMovie = repository.findByTitle(movieInput.getTitle());
        existingMovie.ifPresent(em -> {
            throw new BusinessException("Already Exists a movie with this title!");
        });

        var entity = save(movieInput);

        var dto = ModelMapper.parseObject(entity, MovieDTO.class);
        dto.add(linkTo(methodOn(MovieController.class).findById(dto.getId())).withSelfRel());
        return dto;
    }

    private Movie save(MovieInputDTO movieInput) {
        Movie movie = new Movie();
        movie.setTitle(movieInput.getTitle());
        movie.setAuthor(movieInput.getAuthor());
        movie.setSynopsis(movieInput.getSynopsis());
        movie.setReleaseDate(movieInput.getReleaseDate());
        movie.setTags(movieInput.getTags());

        List<Actor> actors = populateActor(movieInput);
        movie.setActors(actors);

        Category category = populateCategory(movieInput);
        movie.setCategory(category);

        return repository.save(movie);
    }

    private List<Actor> populateActor(MovieInputDTO movieInput) {
        List<Actor> actors = new ArrayList<>();
        movieInput.getActors().forEach(dto -> {
            Actor actor = actorService.findById(dto.getId());
            actors.add(actor);
        });
        return actors;
    }

    private Category populateCategory(MovieInputDTO movieInput) {
        return categoryService.findById(movieInput.getCategory());
    }
}
