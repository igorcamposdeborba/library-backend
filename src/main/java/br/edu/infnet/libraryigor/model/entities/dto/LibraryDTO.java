package br.edu.infnet.libraryigor.model.entities.dto;


import br.edu.infnet.libraryigor.model.entities.Book;
import br.edu.infnet.libraryigor.model.entities.Loan;
import br.edu.infnet.libraryigor.model.entities.client.Users;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class LibraryDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private static String name;
    private static String address;
    private List<Book> books;
    private List<Users> users;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        LibraryDTO.name = name;
    }

    public static String getAddress() {
        return address;
    }

    public static void setAddress(String address) {
        LibraryDTO.address = address;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public List<Users> getUsers() {
        return users;
    }

    public void setUsers(List<Users> users) {
        this.users = users;
    }
}
