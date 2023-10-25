package com.provavorp.domain;

import javax.persistence.*;
import java.util.Random;

@Entity
@Table(name = "conti")
public class Conto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idConto")
    private Long idConto;

    @Column
    private Double saldo;

    @Column
    private String iban;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "idCliente")
    private Cliente cliente;


    public Conto() {
    }

    public Conto(Long idConto, Double saldo, String iban, Cliente cliente) {
        this.idConto = idConto;
        this.saldo = saldo;
        this.iban = iban;
        this.cliente = cliente;
    }

    public Long getIdConto() {
        return idConto;
    }

    public void setIdConto(Long idConto) {
        this.idConto = idConto;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    private static final String COUNTRY_CODE = "IT";
    private static final int IBAN_LENGTH = 27;

    public static String generateRandomIBAN() {
        StringBuilder sb = new StringBuilder(IBAN_LENGTH);
        Random random = new Random();

        sb.append(COUNTRY_CODE);
        sb.append(random.nextInt(90) + 10);

        for (int i = 4; i < IBAN_LENGTH; i++) {
            sb.append(random.nextInt(10));
        }

        return sb.toString();
    }
}
