package com.provavorp.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Entity
@Table(name = "versamenti")
public class Versamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_versamento")
    private Long idVersamento;

    @Column
    private String iban;
    @Column
    private String nome;
    @Column
    private String cognome;
    @Column
    private Double importo;
    @Column
    private String dataOra;

    public Versamento() {
    }

    public Versamento(String iban, String nome, String cognome, Double importo, String dataOra) {
        this.iban = iban;
        this.nome = nome;
        this.cognome = cognome;
        this.importo = importo;
        this.dataOra = dataOra;
    }

    public Long getIdVersamento() {
        return idVersamento;
    }

    public void setIdVersamento(Long idVersamento) {
        this.idVersamento = idVersamento;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
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

    public Double getImporto() {
        return importo;
    }

    public void setImporto(Double importo) {
        this.importo = importo;
    }

    public String  getDataOra() {
        return dataOra;
    }

    public void setDataOra(String  dataOra) {
        this.dataOra = dataOra;
    }
}
