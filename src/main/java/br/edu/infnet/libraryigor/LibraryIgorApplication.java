package br.edu.infnet.libraryigor;

import br.edu.infnet.libraryigor.model.entities.Book;
import br.edu.infnet.libraryigor.model.entities.Library;
import br.edu.infnet.libraryigor.model.entities.Loan;
import br.edu.infnet.libraryigor.model.entities.LoanRecord;
import br.edu.infnet.libraryigor.model.entities.client.Associate;
import br.edu.infnet.libraryigor.model.entities.client.Student;
import br.edu.infnet.libraryigor.model.entities.client.Users;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@SpringBootApplication
public class LibraryIgorApplication implements ApplicationRunner {
    public static void main(String[] args) {
        SpringApplication.run(LibraryIgorApplication.class, args);
    }

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
                Users user = loan.getLoanKey().getUser();
                Book book = loan.getLoanKey().getBook();

                if (user != null && book != null) {
                    user.addBooksLoan(loan);
                    book.getLoans().add(loan);
                }
            }
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
                    LoanRecord loanRecord = new LoanRecord(
                            loan
                    );
                    loans.add(loanRecord);
            }
        }
    }

    public Map<Integer, Book> getBookMap(){
        return bookMap;
    }
}