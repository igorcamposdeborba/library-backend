package br.edu.infnet.libraryigor.model.entities;

import br.edu.infnet.libraryigor.model.entities.client.Users;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Embeddable // Chave composta
public class Loan implements Serializable { // Serializable para trafegar em rede por bytes
    private static final long serialVersionUID = 1L; // versão para serializacao/deserializacao para dar match com o que está sendo trafegado

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // name na tabela do banco de dados. E não permite nulo
    private Users users;
    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false) // name na tabela do banco de dados. E não permite nulo
    private Book book;

    @NotBlank // nao permite vazio ou null
    private LocalDate effectiveFrom;
    private LocalDate effectiveTo;

    public Loan(Users users, Book book, LocalDate effectiveFrom, LocalDate effectiveTo) {
        this.users = users;
        this.book = book;
        this.effectiveFrom = effectiveFrom;
        this.effectiveTo = effectiveTo;
    }
    public Loan() {}

    public LocalDate getEffectiveFrom() {
        return effectiveFrom;
    }
    public LocalDate getEffectiveTo() {
        return effectiveTo;
    }
    public Users getUser() {
        return users;
    }
    public Book getBook() {
        return book;
    }
    public void setEffectiveFrom(LocalDate effectiveFrom) {
        this.effectiveFrom = effectiveFrom;
    }
    public void setEffectiveTo(LocalDate effectiveTo) {
        this.effectiveTo = effectiveTo;
    }
    public void setUser(Users users) {
        this.users = users;
    }
    public void setBook(Book book) {
        this.book = book;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Loan loan)) return false;
        return Objects.equals(users, loan.users) && Objects.equals(getBook(), loan.getBook());
    }

    @Override
    public int hashCode() {
        return Objects.hash(users, getBook());
    }

    @Override
    public String toString() {
        return "Loan{" +
                ", user: " + users +
                ", book: " + book +
                ", effectiveFrom: " + effectiveFrom +
                ", effectiveTo: " + effectiveTo +
                '}';
    }
}
