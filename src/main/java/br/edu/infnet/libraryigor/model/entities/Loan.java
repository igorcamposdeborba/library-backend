package br.edu.infnet.libraryigor.model.entities;

import br.edu.infnet.libraryigor.model.entities.client.Users;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
@Entity
@Table(name = "loan")
public class Loan implements Serializable { // Serializable para trafegar em rede por bytes
    private static final long serialVersionUID = 1L; // versão para serializacao/deserializacao para dar match com o que está sendo trafegado

    @EmbeddedId
    private LoanRecord loanId; // Chave composta como objeto
    @ManyToOne
    @MapsId("userId") // Mapeia a chave primária composta
    @JoinColumn(name = "user_id", nullable = false) // name na tabela do banco de dados. E não permite nulo
    private Users users;

    @OneToOne // muitos empréstimos (Loan) a um livro
    @MapsId("bookId") // Mapeia a chave primária composta
    @JoinColumn(name = "book_id", nullable = false) // name na tabela do banco de dados. E não permite nulo
    private Book book;

//    @NotBlank // nao permite vazio ou null
    private LocalDate effectiveFrom;
    private LocalDate effectiveTo;

    public Loan(Users user, Book book, LocalDate effectiveFrom, LocalDate effectiveTo) {
        this.users = user;
        this.book = book;
        this.effectiveFrom = effectiveFrom;
        this.effectiveTo = effectiveTo;

        this.loanId = new LoanRecord(book, user);
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
    public void setUser(Users user) {
        this.users = user;
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
        return "LOAN{" +
                "loanId: " + loanId +
                "user: " + users.getName() +
                ", book: " + book.getTitle() +
                ", effectiveFrom: " + effectiveFrom +
                ", effectiveTo: " + effectiveTo +
                '}';
    }
}
