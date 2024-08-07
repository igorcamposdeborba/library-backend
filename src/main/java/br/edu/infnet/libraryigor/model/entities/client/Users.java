package br.edu.infnet.libraryigor.model.entities.client;

import br.edu.infnet.libraryigor.model.entities.Library;
import br.edu.infnet.libraryigor.model.entities.LoanRecord;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Set;
@Entity
@Inheritance(strategy = InheritanceType.JOINED) // Cada subclasse tem sua propria tabela no banco de dados
public abstract class Users implements Serializable { // Classe abstrata para que ela não possa ser intanciada a agrega atributos em comum para as classes implementadoras
                                                     // Serializable para trafegar em rede por bytes
    private static final long serialVersionUID = 1L; // versão para serializacao/deserializacao para dar match com o que está sendo trafegado

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // JPA: Banco de dados gera id sequencial
    private Integer id;
    @NotBlank // nao permite vazio ou null
    private String name;
    @Email // valida formatos validos de email
    private String email;
    private boolean active;

    @OneToMany (mappedBy = "loanKey.users")
    private Set<LoanRecord> loans;

    @ManyToOne
    @JoinColumn(name = "library_id")
    private Library library;

    public Users(String id, String name, String email, boolean active, Set<LoanRecord> loans) {
        this.id = Integer.parseInt(id);
        this.name = name;
        this.email = email;
        this.active = active;
        this.loans = loans;
    }

    public Users() {} // JPA precisa de construtor vazio público para persistir no banco de dados

    public Integer getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public boolean isActive() {
        return active;
    }
    public Set<LoanRecord> getLoans() {
        return loans;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setActive(boolean active) {
        this.active = active;
    }
    public void addBooksLoan(LoanRecord bookLoan) {
        this.loans.add(bookLoan);
    }

    @Override
    public String toString() {
        return "USER{" +
                "id: " + id +
                ", name: " + name +
                ", email: " + email +
                ", active: " + active +
                ", loans: " + loans +
                '}';
    }
}
