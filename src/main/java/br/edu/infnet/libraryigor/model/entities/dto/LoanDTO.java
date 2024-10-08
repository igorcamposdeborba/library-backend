package br.edu.infnet.libraryigor.model.entities.dto;


import br.edu.infnet.libraryigor.model.entities.Book;
import br.edu.infnet.libraryigor.model.entities.Loan;
import br.edu.infnet.libraryigor.model.entities.client.Users;
import java.io.Serializable;
import java.time.LocalDate;

public class LoanDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer bookId;
    private Integer userId;

    private String bookTitle;
    private Book book;
    private Users users;
    private LocalDate effectiveFrom;
    private LocalDate effectiveTo;
    private boolean isDelivered;

    public LoanDTO(Loan loan) {
        this.bookId = loan.getLoanId().getBookId();
        this.userId = loan.getLoanId().getUserId();

        this.effectiveFrom = loan.getEffectiveFrom();
        this.effectiveTo = loan.getEffectiveTo();
        this.bookTitle = loan.getBook().getTitle().toString();
        this.isDelivered = loan.isDelivered();
        this.book = loan.getBook();
        this.users = loan.getUser();
    }

    public LoanDTO(){} // Jackson precisa de construtor vazio para serializar/desserializar json

    public Integer getBookId() {
        return bookId;
    }
    public Integer getUserId() {
        return userId;
    }
    public LocalDate getEffectiveFrom() {
        return effectiveFrom;
    }

    public LocalDate getEffectiveTo() {
        return effectiveTo;
    }

    public boolean isDelivered() {
        return isDelivered;
    }

    public void setDelivered(boolean delivered) {
        isDelivered = delivered;
    }

    public Users getUsers() {
        return users;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
    public void setUsers(Users users) {
        this.users = users;
    }

    public String getBookTitle() { // obrigatório get de cada atributo para a serializacao do jackson identificar o campo por reflection
        return bookTitle;
    }
}
