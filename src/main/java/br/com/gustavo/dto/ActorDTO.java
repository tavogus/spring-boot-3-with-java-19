package br.com.gustavo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;

public class ActorDTO extends RepresentationModel<ActorDTO> implements Serializable {

    private static final long serialVersionUID = 1L;
    @JsonProperty("id")
    private Long id;
    private String name;

    public ActorDTO(){}

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
}
