package br.com.gustavo.dto;

import java.io.Serializable;
import java.util.List;

public class MovieInputDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String title;
    private String author;
    private String synopsis;
    private Integer releaseDate;
    private List<ActorInputDTO> actors;
    private Long category;
    private List<String> tags;

    public MovieInputDTO() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public Integer getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Integer releaseDate) {
        this.releaseDate = releaseDate;
    }

    public List<ActorInputDTO> getActors() {
        return actors;
    }

    public void setActors(List<ActorInputDTO> actors) {
        this.actors = actors;
    }

    public Long getCategory() {
        return category;
    }

    public void setCategory(Long category) {
        this.category = category;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
