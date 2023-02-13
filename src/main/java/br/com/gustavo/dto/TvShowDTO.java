package br.com.gustavo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.hateoas.RepresentationModel;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.List;

@JsonPropertyOrder({"id", "title", "seasons", "categoryName", "author", "releaseDate", "actors", "synopsis", "tags", "url"})
public class TvShowDTO extends RepresentationModel<TvShowDTO> implements Serializable  {
    private static final long serialVersionUID = 1L;

    @JsonProperty("id")
    private Long id;
    private String title;
    private String categoryName;
    private OffsetDateTime insertionDate;
    private List<ActorDTO> actors;
    private String author;
    private String synopsis;
    private Integer seasons;
    private Integer releaseDate;
    private List<String> tags;
    private String url;

    public TvShowDTO(){}

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

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public OffsetDateTime getInsertionDate() {
        return insertionDate;
    }

    public void setInsertionDate(OffsetDateTime insertionDate) {
        this.insertionDate = insertionDate;
    }

    public List<ActorDTO> getActors() {
        return actors;
    }

    public void setActors(List<ActorDTO> actors) {
        this.actors = actors;
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

    public Integer getSeasons() {
        return seasons;
    }

    public void setSeasons(Integer seasons) {
        this.seasons = seasons;
    }

    public Integer getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Integer releaseDate) {
        this.releaseDate = releaseDate;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
