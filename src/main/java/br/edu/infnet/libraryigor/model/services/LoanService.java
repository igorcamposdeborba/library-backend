package br.edu.infnet.libraryigor.model.services;

import br.edu.infnet.libraryigor.model.entities.Book;
import br.edu.infnet.libraryigor.model.entities.Loan;
import br.edu.infnet.libraryigor.model.entities.LoanRecord;
import br.edu.infnet.libraryigor.model.entities.dto.BookDTO;
import br.edu.infnet.libraryigor.model.entities.dto.LoanDTO;
import br.edu.infnet.libraryigor.model.repositories.BookRepository;
import br.edu.infnet.libraryigor.model.repositories.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class LoanService {
    @Autowired
    private LoanRepository loanRepository; // injetar instancia do repository para buscar do banco de dados via JPA

    public List<LoanDTO> findAll(){
        List<Loan> loanList = loanRepository.findAll(); // buscar no banco de dados
        // converter a lista de classe para DTO
        return loanList.stream().filter(Objects::nonNull).map((Loan loan) -> new LoanDTO(loan)).collect(Collectors.toList());
    }
}
