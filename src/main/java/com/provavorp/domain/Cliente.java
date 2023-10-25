package com.provavorp.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clienti")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCliente")
    private Long idCliente;
    @Column
    private String nome;
    @Column
    private String cognome;

    @OneToMany(mappedBy = "cliente", cascade = {CascadeType.PERSIST, CascadeType.REFRESH,CascadeType.REMOVE})
    private List<Conto> conti = new ArrayList<>();



    public Cliente(){}

    public Cliente(Long idCliente, String nome, String cognome, List<Conto> conti) {
        this.idCliente = idCliente;
        this.nome = nome;
        this.cognome = cognome;
        this.conti = conti;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public List<Conto> getConti() {
        return conti;
    }

    public void setConti(List<Conto> conti) {
        this.conti = conti;
    }
}
