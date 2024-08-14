package br.edu.infnet.libraryigor.model.entities.dto;


import br.edu.infnet.libraryigor.model.entities.Book;
import br.edu.infnet.libraryigor.model.entities.Library;
import br.edu.infnet.libraryigor.model.entities.Loan;
import br.edu.infnet.libraryigor.model.entities.LoanRecord;
import br.edu.infnet.libraryigor.model.entities.client.Users;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

public class BookDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String title;
    private String author;
    private LocalDate yearPublication;
    private double price;

    public BookDTO(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.yearPublication = LocalDate.parse(String.valueOf(book.getYearPublication()).replaceAll("^\"|\"$","").trim());
        this.price = book.getPrice();
    }
    public BookDTO(Integer id, String title, String author, String yearPublication, double price, Loan loan) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.yearPublication = LocalDate.parse(yearPublication.replaceAll("^\"|\"$","").trim());
        this.price = price;
//        this.loan = loan;
    }

    public Integer getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getAuthor() {
        return author;
    }
    public LocalDate getYearPublication() {
        return yearPublication;
    }
    public double getPrice() {
        return price;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public void setYearPublication(LocalDate yearPublication) {
        this.yearPublication = yearPublication;
    }
    public void setPrice(double price) {
        this.price = price;
    }

}
