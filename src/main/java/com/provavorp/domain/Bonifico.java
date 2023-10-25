package com.provavorp.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Entity
@Table(name = "bonifici")
public class Bonifico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_bonifico")
    private Long idBonifico;
    @Column
    private String ibanMittente;
    @Column
    private String ibanDestinatario;
    @Column
    private String nomeMittente;
    @Column
    private String cognomeMittente;
    @Column
    private String nomeDestinatario;
    @Column
    private String cognomeDestinatario;
    @Column
    private Double importo;
    @Column
    private String dataOra;

    public Bonifico() {
    }

    public Bonifico(String ibanMittente, String ibanDestinatario, String nomeMittente, String cognomeMittente, String nomeDestinatario, String cognomeDestinatario, Double importo, String dataOra) {
        this.ibanMittente = ibanMittente;
        this.ibanDestinatario = ibanDestinatario;
        this.nomeMittente = nomeMittente;
        this.cognomeMittente = cognomeMittente;
        this.nomeDestinatario = nomeDestinatario;
        this.cognomeDestinatario = cognomeDestinatario;
        this.importo = importo;
        this.dataOra = dataOra;
    }

    public Long getIdBonifico() {
        return idBonifico;
    }

    public void setIdBonifico(Long idBonifico) {
        this.idBonifico = idBonifico;
    }

    public String getIbanMittente() {
        return ibanMittente;
    }

    public void setIbanMittente(String ibanMittente) {
        this.ibanMittente = ibanMittente;
    }

    public String getIbanDestinatario() {
        return ibanDestinatario;
    }

    public void setIbanDestinatario(String ibanDestinatario) {
        this.ibanDestinatario = ibanDestinatario;
    }

    public String getNomeMittente() {
        return nomeMittente;
    }

    public void setNomeMittente(String nomeMittente) {
        this.nomeMittente = nomeMittente;
    }

    public String getCognomeMittente() {
        return cognomeMittente;
    }

    public void setCognomeMittente(String cognomeMittente) {
        this.cognomeMittente = cognomeMittente;
    }

    public String getNomeDestinatario() {
        return nomeDestinatario;
    }

    public void setNomeDestinatario(String nomeDestinatario) {
        this.nomeDestinatario = nomeDestinatario;
    }

    public String getCognomeDestinatario() {
        return cognomeDestinatario;
    }

    public void setCognomeDestinatario(String cognomeDestinatario) {
        this.cognomeDestinatario = cognomeDestinatario;
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
