package br.edu.infnet.libraryigor.model.entities;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import java.io.Serializable;

@Entity
public class LoanRecord implements Serializable { // Serializable para trafegar em rede por bytes
    private static final long serialVersionUID = 1L; // versão para serializacao/deserializacao para dar match com o que está sendo trafegado

    @EmbeddedId
    private Loan loanKey; // Chave composta referenciando a classe Loan

    public LoanRecord(Loan loan){
        this.loanKey = loan;
    }

    public LoanRecord() {} // JPA precisa de construtor vazio público para persistir no banco de dados

    public Loan getLoanKey() {
        return loanKey;
    }

    @Override
    public String toString() {
        return "LoanRecord{" +
                "loanKey: " + loanKey +
                '}';
    }
}
