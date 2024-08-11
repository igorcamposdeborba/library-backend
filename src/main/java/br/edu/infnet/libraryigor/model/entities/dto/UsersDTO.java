package br.edu.infnet.libraryigor.model.entities.dto;


import br.edu.infnet.libraryigor.model.entities.LoanRecord;
import br.edu.infnet.libraryigor.model.entities.client.Users;

import java.io.Serializable;
import java.util.Set;

public class UsersDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String name;
    private String email;
    private boolean active;
    private Set<LoanRecord> loans;

    public UsersDTO(Users user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.active = user.isActive();
        this.loans = user.getLoans();
    }
    public UsersDTO(String id, String name, String email, boolean active, Set<LoanRecord> loans) {
        this.id = Integer.parseInt(id);
        this.name = name;
        this.email = email;
        this.active = active;
        this.loans = loans;
    }

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

}
