package br.com.gustavo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class CategoryTvShowModel implements Serializable {

    private static final long serialVersionUID = 1L;
    @JsonProperty("id")
    private Long id;
    private String title;
}
