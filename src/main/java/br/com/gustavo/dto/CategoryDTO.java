package br.com.gustavo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.util.List;

public class CategoryDTO extends RepresentationModel<CategoryDTO> implements Serializable {
    private static final long serialVersionUID = 1L;
    @JsonProperty("id")
    private Long id;
    private String name;
    private List<CategoryMovieModel> movies;
    private List<CategoryTvShowModel> tvShows;

    public CategoryDTO() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CategoryMovieModel> getMovies() {
        return movies;
    }

    public void setMovies(List<CategoryMovieModel> movies) {
        this.movies = movies;
    }

    public List<CategoryTvShowModel> getTvShows() {
        return tvShows;
    }

    public void setTvShows(List<CategoryTvShowModel> tvShows) {
        this.tvShows = tvShows;
    }
}
