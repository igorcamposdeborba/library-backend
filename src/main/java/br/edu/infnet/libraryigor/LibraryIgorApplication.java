package br.edu.infnet.libraryigor;

import br.edu.infnet.libraryigor.model.entities.Book;
import br.edu.infnet.libraryigor.model.entities.Library;
import br.edu.infnet.libraryigor.model.entities.Loan;
import br.edu.infnet.libraryigor.model.entities.LoanRecord;
import br.edu.infnet.libraryigor.model.entities.client.Associate;
import br.edu.infnet.libraryigor.model.entities.client.Student;
import br.edu.infnet.libraryigor.model.entities.client.Users;
import br.edu.infnet.libraryigor.model.repositories.BookRepository;
import br.edu.infnet.libraryigor.model.repositories.LoanRepository;
import br.edu.infnet.libraryigor.model.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

@SpringBootApplication
public class LibraryIgorApplication implements ApplicationRunner {
    public static void main(String[] args) {
        SpringApplication.run(LibraryIgorApplication.class, args);
    }

    @Autowired
    UserRepository userRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    LoanRepository loanRepository;

    private Map<Integer, Book> bookMap = new HashMap<>();
    private Map<Integer, Users> userMap = new HashMap<>();
    private Set<LoanRecord> loans = new HashSet<>();
    private Library library;

    @Override
    public void run(ApplicationArguments args) {
        try {
            readBooks();
            readAssociate();
            readStudent();
            readLoans();

            // Atualizar a biblioteca com todos os livros e usuários
            library = new Library(0, new HashSet<>(bookMap.values()), new HashSet<>(userMap.values()));

            // Associar empréstimos aos usuários e livros
            for (LoanRecord loan : loans) {
//                Users user = loan.getLoanKey().getUser();
//                Book book = loan.getLoanKey().getBook();
                Users user = loan.getLoanKey().getUser();
                Book book = loan.getLoanKey().getBook();
//                Users user = loan.getUser();
//                Book book = loan.getUser().getLoans().stream().map(item -> item.getBook()).findFirst().orElse(null); // todo: revisar essa linha

                if (user != null && book != null) {
                    user.addBooksLoan(loan);
                    book.getLoans().add(loan);
//                    userRepository.save(user);
//                    loanRepository.save(loan);

//                    bookRepository.save(book); // salvar no banco de dados ao iniciar a aplicacao
//                    loanRepository.save(loan);
//                    userRepository.save(user);
                }
            }
//            bookRepository.saveAll(bookMap.values());
//            userRepository.saveAll(userMap.values());
//            loanRepository.saveAll(loans);
            System.out.println(new ObjectMapper().writeValueAsString(library.toString()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readBooks() throws IOException {
        String filePath = "src/main/resources/init/book.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // header
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                    Integer id = Integer.parseInt(fields[0]);
                    String title = fields[1];
                    String author = fields[2];
                    String yearPublicationStr = fields[3];
                    double price = Double.parseDouble(fields[4]);

                    Book book = new Book(
                            id,
                            title,
                            author,
                            yearPublicationStr,
                            price,
                            new HashSet<>()
                    );
                    bookMap.put(id, book);
            }
            bookRepository.saveAll(bookMap.values());
        }
    }

    private void readAssociate() throws IOException {
        String filePath = "src/main/resources/init/associate.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // header
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                    Integer id = Integer.parseInt(fields[0]);
                    Users user = new Associate(
                            fields[0], // id
                            fields[1], // name
                            fields[2], // email
                            Boolean.parseBoolean(fields[3]), // active
                            new HashSet<>(),
                            fields[5], // department
                            fields[6]  // specialty
                    );
                    userMap.put(id, user);
            }
        }
    }

    private void readStudent() throws IOException {
        String filePath = "src/main/resources/init/student.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // header
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                    Integer id = Integer.parseInt(fields[0]);
                    Users user = new Student(
                            fields[0], // id
                            fields[1], // name
                            fields[2], // email
                            Boolean.parseBoolean(fields[3]), // active
                            new HashSet<>(),
                            Double.parseDouble(fields[5]), // pendingPenaltiesAmount
                            fields[6]  // courseName
                    );
                    userMap.put(id, user);
            }
            userRepository.saveAll(userMap.values());
        }
    }
    private void readLoans() throws IOException {
        String filePath = "src/main/resources/init/loan.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // header
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                Integer userId = Integer.parseInt(fields[0]);
                Integer bookId = Integer.parseInt(fields[1]);

                Users user = userMap.get(userId);
                Book book = bookMap.get(bookId);

                LocalDate effectiveFrom = LocalDate.parse(fields[2]);
                LocalDate effectiveTo = LocalDate.parse(fields[3]);

                Loan loan = new Loan(
                        user,
                        book,
                        effectiveFrom,
                        effectiveTo
                );
                LoanRecord loanRecord = new LoanRecord(loan);
                loans.add(loanRecord);

                loanRepository.save(loanRecord);
            }
        }
    }
//    private void readLoans() throws IOException {
//        String filePath = "src/main/resources/init/loan.csv";
//        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
//            String line;
//            br.readLine(); // header
//            while ((line = br.readLine()) != null) {
//                String[] fields = line.split(",");
//                Integer userId = Integer.parseInt(fields[0]);
//                Integer bookId = Integer.parseInt(fields[1]);
//
//                // Verificar se o usuário existe antes de criar o empréstimo
//                Optional<Users> optionalUser = userRepository.findById(userId);
//                if (optionalUser.isPresent()) {
//                    Users user = optionalUser.get();
//                    Book book = bookMap.get(bookId);
//                    LocalDate effectiveFrom = LocalDate.parse(fields[2]);
//                    LocalDate effectiveTo = LocalDate.parse(fields[3]);
//
//                    Loan loan = new Loan(user, book, effectiveFrom, effectiveTo);
//                    LoanRecord loanRecord = new LoanRecord(loan);
//                    loans.add(loanRecord);
//
////                    loanRepository.save(loanRecord);
//                } else {
//                    System.out.println("Usuário com ID " + userId + " não encontrado. Ignorando registro.");
//                }
//            }
//        }
//    }

    public Map<Integer, Book> getBookMap(){
        return bookMap;
    }
}