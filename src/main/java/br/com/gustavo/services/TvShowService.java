package br.com.gustavo.services;

import br.com.gustavo.controller.MovieController;
import br.com.gustavo.controller.TvShowController;
import br.com.gustavo.dto.TvShowDTO;
import br.com.gustavo.exceptions.BusinessException;
import br.com.gustavo.exceptions.RequiredObjectIsNullException;
import br.com.gustavo.exceptions.ResourceNotFoundException;
import br.com.gustavo.mapper.ModelMapper;
import br.com.gustavo.model.TvShow;
import br.com.gustavo.repositories.TvShowRepository;
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
public class TvShowService {

    private Logger logger = Logger.getLogger(TvShowService.class.getName());
    
    @Autowired
    private TvShowRepository repository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    PagedResourcesAssembler<TvShowDTO> assembler;

    public PagedModel<EntityModel<TvShowDTO>> findAll(Pageable pageable) {
        logger.info("Finding all tv shows!");

        var tvShowsPage = repository.findAll(pageable);

        var tvShowDtos = tvShowsPage.map(p -> ModelMapper.parseObject(p, TvShowDTO.class));
        tvShowDtos.map(p -> p.add(linkTo(methodOn(TvShowController.class).findById(p.getId())).withSelfRel()));

        Link findAllLink = linkTo(
                methodOn(TvShowController.class)
                        .findAll(pageable.getPageNumber(),
                                pageable.getPageSize(),
                                "asc")).withSelfRel();

        return assembler.toModel(tvShowDtos, findAllLink);
    }

    public TvShowDTO findById(Long id) {
        logger.info("Finding one tv show!");

        var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        var dto = ModelMapper.parseObject(entity, TvShowDTO.class);
        dto.add(linkTo(methodOn(MovieController.class).findById(id)).withSelfRel());
        return dto;
    }

    public TvShowDTO save(TvShow tvShow) {
        if (tvShow == null) throw new RequiredObjectIsNullException();

        logger.info("Creating new tv show!");

        Optional<TvShow> existingTvShow = repository.findByTitle(tvShow.getTitle());
        existingTvShow.ifPresent(em -> {
            throw new BusinessException("Already Exists a tv show with this title!");
        });

        categoryService.findById(tvShow.getCategory().getId()).orElseThrow(() -> new ResourceNotFoundException("No category found for this ID!"));

        var dto = ModelMapper.parseObject(repository.save(tvShow), TvShowDTO.class);
        dto.add(linkTo(methodOn(TvShowController.class).findById(dto.getId())).withSelfRel());
        return dto;
    }
}
