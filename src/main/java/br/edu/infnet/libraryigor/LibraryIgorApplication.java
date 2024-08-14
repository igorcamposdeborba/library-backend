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
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
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
import java.util.stream.Collectors;

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
    private Set<LoanRecord> loansKey = new HashSet<>();
    Set<Loan> loans = new HashSet<>();
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
            for (LoanRecord loan : loansKey) {
//                Users user = loan.getLoanKey().getUser();
//                Book book = loan.getLoanKey().getBook();
                Integer userId = loan.getUserId();
                Integer bookId = loan.getBookId();

                if (userId != null && bookId != null) {
                    Users user = userMap.values().stream().filter(userActual -> userActual.getId().equals(userId)).findFirst().get();
                    Book book = bookMap.values().stream().filter(bookActual -> bookActual.getId().equals(bookId)).findFirst().get();
//                    user.addBooksLoan(new Loan(user, book, LocalDate.now(), LocalDate.now().plusDays(7)));
                    // !todo: pegar id do loan do banco de dados. Criar findById
//                    loans = user.getLoans().stream().filter(actualLoan -> actualLoan.getBook().getId().equals(bookId)
//                                                                                 && actualLoan.getUser().getId().equals(userId))
//                                                              .collect(Collectors.toSet());
//                    loanRepository.saveAll(loans);
//                    userRepository.save(user);
//                    loanRepository.saveAll(loans);
//                    bookRepository.save(book); // salvar no banco de dados ao iniciar a aplicacao
//                    loanRepository.save(loan);
//                    userRepository.save(user);
                }
            }
            loanRepository.saveAll(loans);
            bookRepository.saveAll(bookMap.values());
            userRepository.saveAll(userMap.values());
            System.out.println(new ObjectMapper().writeValueAsString(library.toString()));

            Unirest.setTimeouts(0, 0);
            HttpResponse<String> bookResponse = Unirest.get("http://localhost:8080/book").asString();
            HttpResponse<String> userResponse = Unirest.get("http://localhost:8080/user").asString();
            HttpResponse<String> bookInsertResponse = Unirest.post("http://localhost:8080/book/single")
                    .header("Content-Type", "application/json")
                    .body("{\r\n    \"title\": \"O Senhor dos Anéis\",\r\n    \"author\": \"J.R.R. Tolkien\",\r\n    \"yearPublication\": \"1954-07-29\",\r\n    \"price\": 4.00\r\n}").asString();
            HttpResponse<String> loanResponse = Unirest.get("http://localhost:8080/loan").asString();

            System.out.println("BOOK findAll: " + bookResponse.getBody() + ". status " + bookResponse.getStatus());
            System.out.println("USER findAll: " + userResponse.getBody() + ". status " + userResponse.getStatus());
            System.out.println("BOOK insert: " + bookInsertResponse.getStatus());
            System.out.println("LOAN findAll: " + loanResponse.getBody() + ". status " + loanResponse.getStatus());


        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnirestException e) {
            throw new RuntimeException(e);
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
                            price
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

                if (user == null || book == null) {
                    // Log an error or handle the case where user or book is not found
                    continue;
                }

                LocalDate effectiveFrom = LocalDate.parse(fields[2]);
                LocalDate effectiveTo = LocalDate.parse(fields[3]);

                Loan loan = new Loan(
                        user,
                        book,
                        effectiveFrom,
                        effectiveTo
                );
                LoanRecord loanRecord = new LoanRecord(book, user);
                loansKey.add(loanRecord);

                loanRepository.save(loan);
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