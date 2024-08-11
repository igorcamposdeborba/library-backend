package br.edu.infnet.libraryigor.model.entities.dto;


import br.edu.infnet.libraryigor.model.entities.Loan;
import br.edu.infnet.libraryigor.model.entities.LoanRecord;
import br.edu.infnet.libraryigor.model.entities.client.Users;

import java.io.Serializable;
import java.util.Set;

public class LoanDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private LoanRecord loanKey;

    public LoanDTO(LoanRecord loan) {
        this.loanKey = loan;
    }

    public LoanRecord getLoanKey() {
        return loanKey;
    }

}
