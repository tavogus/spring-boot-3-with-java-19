package br.com.gustavo.dto;

import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;

public class ActorDTO extends RepresentationModel<ActorDTO> implements Serializable {

    private static final long serialVersionUID = 1L;
    private String name;

    public ActorDTO(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
