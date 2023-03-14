package br.com.gustavo.services;

import br.com.gustavo.controller.MovieController;
import br.com.gustavo.controller.TvShowController;
import br.com.gustavo.dto.TvShowDTO;
import br.com.gustavo.dto.TvShowInputDTO;
import br.com.gustavo.exceptions.BusinessException;
import br.com.gustavo.exceptions.RequiredObjectIsNullException;
import br.com.gustavo.exceptions.ResourceNotFoundException;
import br.com.gustavo.mapper.ModelMapper;
import br.com.gustavo.model.Actor;
import br.com.gustavo.model.Category;
import br.com.gustavo.model.TvShow;
import br.com.gustavo.repositories.TvShowRepository;
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
public class TvShowService {

    private Logger logger = Logger.getLogger(TvShowService.class.getName());
    @Autowired
    private TvShowRepository repository;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ActorService actorService;
    @Autowired
    private S3Service s3Service;
    @Autowired
    PagedResourcesAssembler<TvShowDTO> assembler;


    public PagedModel<EntityModel<TvShowDTO>> findByFilter(String filter, Pageable pageable) {
        logger.info("Finding tv shows by filter!");

        var tvShowsPage = repository.findByFilter(filter, pageable);

        var tvShowDtos = tvShowsPage.map(p -> ModelMapper.parseObject(p, TvShowDTO.class));
        tvShowDtos.map(p -> p.add(linkTo(methodOn(MovieController.class).findById(p.getId())).withSelfRel()));

        Link findByFilterLink = linkTo(
                methodOn(TvShowController.class)
                        .findByFilter(pageable.getPageNumber(),
                                pageable.getPageSize(),
                                "asc",
                                filter)
        ).withSelfRel();

        return assembler.toModel(tvShowDtos, findByFilterLink);
    }

    public TvShowDTO findById(Long id) {
        logger.info("Finding one tv show!");

        var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        var dto = ModelMapper.parseObject(entity, TvShowDTO.class);
        dto.add(linkTo(methodOn(MovieController.class).findById(id)).withSelfRel());
        return dto;
    }

    public TvShowDTO uploadTvShow(MultipartFile file, Long tvShowId) {
        TvShow tvShow = repository.findById(tvShowId).orElseThrow(() -> new ResourceNotFoundException("No Tv Show found for this ID!"));

        String url = s3Service.uploadFile(file);
        tvShow.setUrl(url);

        var entity = repository.save(tvShow);
        var dto = ModelMapper.parseObject(entity, TvShowDTO.class);
        dto.add(linkTo(methodOn(TvShowController.class).findById(dto.getId())).withSelfRel());
        return dto;
    }

    public TvShowDTO preSave(TvShowInputDTO tvShowInput) {
        if (tvShowInput == null) throw new RequiredObjectIsNullException();

        logger.info("Creating new tv show!");

        Optional<TvShow> existingTvShow = repository.findByTitle(tvShowInput.getTitle());
        existingTvShow.ifPresent(em -> {
            throw new BusinessException("Already Exists a tv show with this title!");
        });

        var entity = save(tvShowInput);

        var dto = ModelMapper.parseObject(entity, TvShowDTO.class);
        dto.add(linkTo(methodOn(TvShowController.class).findById(dto.getId())).withSelfRel());
        return dto;
    }

    private TvShow save(TvShowInputDTO tvShowInput) {
        TvShow tvShow = new TvShow();
        tvShow.setTitle(tvShowInput.getTitle());
        tvShow.setAuthor(tvShowInput.getAuthor());
        tvShow.setSynopsis(tvShowInput.getSynopsis());
        tvShow.setReleaseDate(tvShowInput.getReleaseDate());
        tvShow.setSeasons(tvShowInput.getSeasons());
        tvShow.setTags(tvShowInput.getTags());

        List<Actor> actors = populateActor(tvShowInput);
        tvShow.setActors(actors);

        Category category = populateCategory(tvShowInput);
        tvShow.setCategory(category);

        return repository.save(tvShow);
    }

    private List<Actor> populateActor(TvShowInputDTO tvShowInput) {
        List<Actor> actors = new ArrayList<>();
        tvShowInput.getActors().forEach(dto -> {
            Actor actor = actorService.findById(dto.getId());
            actors.add(actor);
        });
        return actors;
    }

    private Category populateCategory(TvShowInputDTO tvShowInput) {
        return categoryService.findById(tvShowInput.getCategory());
    }
}
