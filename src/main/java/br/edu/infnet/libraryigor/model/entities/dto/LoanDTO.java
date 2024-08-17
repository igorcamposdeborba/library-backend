package br.edu.infnet.libraryigor.model.entities.dto;


import br.edu.infnet.libraryigor.model.entities.Loan;
import br.edu.infnet.libraryigor.model.entities.LoanRecord;
import br.edu.infnet.libraryigor.model.entities.client.Users;
import java.io.Serializable;
import java.time.LocalDate;

public class LoanDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private LoanRecord id; // id do empréstimo
    private String bookTitle;
    private Users users;
    private LocalDate effectiveFrom;
    private LocalDate effectiveTo;

    public LoanDTO(Loan loan) {
        this.id = loan.getLoanId();
        this.effectiveFrom = loan.getEffectiveFrom();
        this.effectiveTo = loan.getEffectiveTo();
        this.bookTitle = loan.getBook().getTitle().toString();
        this.users = loan.getUser();
    }

    public LoanRecord getId() {
        return id;
    }

    public LocalDate getEffectiveFrom() {
        return effectiveFrom;
    }

    public LocalDate getEffectiveTo() {
        return effectiveTo;
    }

    public Users getUsers() {
        return users;
    }

    public String getBookTitle() { // obrigatório get de cada atributo para a serializacao do jackson identificar o campo por reflection
        return bookTitle;
    }
}
