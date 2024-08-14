package br.edu.infnet.libraryigor.model.entities.dto;


import br.edu.infnet.libraryigor.model.entities.Loan;
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

    public UsersDTO(Users user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.active = user.isActive();
    }
    public UsersDTO(String id, String name, String email, boolean active, Set<Loan> loans) {
        this.id = Integer.parseInt(id);
        this.name = name;
        this.email = email;
        this.active = active;
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
    public void setName(String name) {
        this.name = name;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setActive(boolean active) {
        this.active = active;
    }

}
