package com.provavorp.domain;

import org.springframework.stereotype.Controller;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "banche")
public class Banca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_banca")
    private Long idBanca;

    @Column
    private String nomeBanca;


    public Banca(Long idBanca, String nomeBanca) {
        this.idBanca = idBanca;
        this.nomeBanca = nomeBanca;
    }

    public Banca() {
    }

    public Long getIdBanca() {
        return idBanca;
    }

    public void setIdBanca(Long idBanca) {
        this.idBanca = idBanca;
    }

    public String getNomeBanca() {
        return nomeBanca;
    }

    public void setNomeBanca(String nomeBanca) {
        this.nomeBanca = nomeBanca;
    }
}
