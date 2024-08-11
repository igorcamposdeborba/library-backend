package br.edu.infnet.libraryigor.model.entities;

import br.edu.infnet.libraryigor.model.entities.client.Users;
import jakarta.persistence.*;

import java.io.Serializable;
@Entity
@Table(name = "loan_record")
public class LoanRecord implements Serializable { // Serializable para trafegar em rede por bytes
    private static final long serialVersionUID = 1L; // versão para serializacao/deserializacao para dar match com o que está sendo trafegado

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;

//    @ManyToOne
//    @JoinColumn(name = "user_id", nullable = false)
//    private Users user;
//
//    @ManyToOne
//    @JoinColumn(name = "book_id", nullable = false)
//    private Book book;

    @EmbeddedId
    private Loan loanKey; // Chave composta referenciando a classe Loan

    public LoanRecord(Loan loan){
        this.loanKey = loan;
    }
//    public LoanRecord(Loan loan){
//        this.book = loan.getBook();
//        this.user = loan.getUser();
//    }

    public LoanRecord() {} // JPA precisa de construtor vazio público para persistir no banco de dados

    public Loan getLoanKey() {
        return loanKey;
    }

//    public Long getId() {
//        return id;
//    }
//
//    public Users getUser() {
//        return user;
//    }

//    public void setUser(Users user) {
//        this.user = user;
//    }
//
//    public Book getBook() {
//        return book;
//    }
//
//    public void setBook(Book book) {
//        this.book = book;
//    }
    @Override
    public String toString() {
        return "LOAN_RECORD{" +
                "loanKey: " + loanKey +
                '}';
    }

}
