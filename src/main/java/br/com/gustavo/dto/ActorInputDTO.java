package br.com.gustavo.dto;

import java.io.Serializable;

public class ActorInputDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    public ActorInputDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
