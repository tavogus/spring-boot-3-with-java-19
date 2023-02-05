package br.com.gustavo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CategoryMovieModel {
    @JsonProperty("id")
    private Long id;
    private String title;

    public CategoryMovieModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
