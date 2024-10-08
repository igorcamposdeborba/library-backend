package br.edu.infnet.libraryigor.model.services;

import br.edu.infnet.libraryigor.Constants;
import br.edu.infnet.libraryigor.model.entities.Book;
import br.edu.infnet.libraryigor.model.entities.Loan;
import br.edu.infnet.libraryigor.model.entities.LoanRecord;
import br.edu.infnet.libraryigor.model.entities.client.Associate;
import br.edu.infnet.libraryigor.model.entities.client.Student;
import br.edu.infnet.libraryigor.model.entities.client.Users;
import br.edu.infnet.libraryigor.model.entities.dto.LoanDTO;
import br.edu.infnet.libraryigor.model.entities.dto.LoanDeliverDTO;
import br.edu.infnet.libraryigor.model.entities.dto.UsersDTO;
import br.edu.infnet.libraryigor.model.repositories.LoanRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LoanService {
    @Autowired
    private LoanRepository loanRepository; // injetar instancia do repository para buscar do banco de dados via JPA
    @Autowired
    private BookService bookService;
    @Autowired
    private UsersService usersService;

    public List<LoanDTO> findAll(){
        List<Loan> loanList = loanRepository.findAll(); // buscar no banco de dados
        // converter a lista de classe para DTO
        return loanList.stream().filter(Objects::nonNull).map((Loan loan) -> new LoanDTO(loan)).collect(Collectors.toList());
    }

    @Transactional() // Transação sempre executa esta operação no banco de dados se for 100% de sucesso.
    public LoanDTO findById(Integer bookId, Integer userId) {

        // Buscar no banco de dados
        Optional<Loan> loanDTO = loanRepository.findById(new LoanRecord(bookId, userId));

        // Exception de validação
        Loan entity = loanDTO.orElseThrow(() -> new ObjectNotFoundException(Constants.NOT_FOUND_BOOK, bookId));

        return new LoanDTO(entity); // retornar somente dados permitidos (mapeados) pelo DTO
    }

    @Transactional
    public LoanDTO insert(@Valid LoanDTO loanDTO) { // @Valid: validar objeto (annotations e atributos)

        // Validar se já existe o empréstimo do livro para o usuário no banco de dados
        if (Objects.nonNull(loanDTO.getBookId()) && Objects.nonNull(loanDTO.getUserId())) {
            LoanRecord loanRecordId = new LoanRecord(loanDTO.getBookId(), loanDTO.getUserId());

            Optional<Loan> loanDatabase = loanRepository.findById(loanRecordId);
            if (loanDatabase.isPresent()) {
                throw new DataIntegrityViolationException("Já existe um emprestimo cadastrado para este usuário");
            }
        }
        // Validar se já existe o empréstimo do livro para o usuário no banco de dados
        if (Objects.nonNull(loanDTO.getBookId()) && Objects.nonNull(loanDTO.getUserId())) {
            List<Loan> existingLoans = loanRepository.findBookByIdAndPeriod(
                    loanDTO.getBookId(),
                    loanDTO.getEffectiveTo(),
                    loanDTO.getEffectiveFrom());

            if (!existingLoans.isEmpty()) {
                throw new DataIntegrityViolationException("Já existe um empréstimo nesse período para este livro.");
            }
        }

        // Preencher o DTO com as informacoes faltantes (Book, User) baseado no id deles
        Book bookDatabase = new Book(bookService.findById(loanDTO.getBookId()));
        UsersDTO usersDTO = usersService.findById(loanDTO.getUserId());
        Users usersDatabase = null;
        if (usersDTO.getType().equals(Student.class.getSimpleName())){
            usersDatabase = new Student(usersDTO);
        } else if (usersDTO.getType().equals(Associate.class.getSimpleName())){
            usersDatabase = new Associate(usersDTO);
        }
        loanDTO.setBook(bookDatabase);
        loanDTO.setUsers(usersDatabase);

        // Mapear DTO para classe
        Loan entity = new Loan(loanDTO);
        entity = loanRepository.save(entity); // salvar no banco de dados
        return new LoanDTO(entity); // retornar o que foi salvo no banco de dados
    }

    public void deliverBook(LoanDeliverDTO loanDTO){
        Loan loan = loanRepository.findById(new LoanRecord(loanDTO.getBookId(), loanDTO.getUserId())).get();
        loan.setDelivered();
        loanRepository.save(loan);
    }
}
