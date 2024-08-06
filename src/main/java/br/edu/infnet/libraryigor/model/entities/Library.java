package br.edu.infnet.libraryigor.model.entities;

import br.edu.infnet.libraryigor.Constants;
import br.edu.infnet.libraryigor.model.entities.client.Users;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "tb_libray")
public class Library implements Serializable { // Serializable para trafegar em rede por bytes
    private static final long serialVersionUID = 1L; // versão para serializacao/deserializacao para dar match com o que está sendo trafegado

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // JPA: Banco de dados gera id sequencial
    private Integer id;
    @NotBlank // nao permite vazio ou null
    private static String name;
    private static String address;

    @OneToMany(mappedBy = "library", cascade = CascadeType.ALL) // cacade: aplicar tambem para as classes filhas
    private Set<Book> books;                                    // mappedBy: quem gerencia a chave estrangeira é Library

    @OneToMany(mappedBy = "library", cascade = CascadeType.ALL) // cacade: aplicar tambem para as classes filhas
    private Set<Users> users;                                    // mappedBy: quem gerencia a chave estrangeira é Library


    public Library(){} // JPA precisa de construtor vazio público para persistir no banco de dados

    public Library(Integer id, Set<Book> books, Set<Users> users) {
        this.id = id;
        this.name = Constants.NAME;
        this.address = Constants.ADDRESS;
        this.books = books;
        this.users = users;
    }
    public Integer getId() {
        return id;
    }

    public Set<Book> getBooks() {
        return books;
    }
    public Set<Users> getUsers() {
        return users;
    }
    public void addBooks(Set<Book> booksInput) {
        booksInput.forEach(book -> this.books.add(book));
    }
    public void setUsers(Set<Users> users) {
        this.users = users;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "Library{" +
                "id: " + id +
                ", name: " + name +
                ", address: " + address +
                ", books: " + books +
                ", users: " + users +
                ", users: " + users +
                '}';
    }
    // Nota: Empresa não precisa trocar o nome, endereco e id neste momento do projeto. Por isso não tem alguns set.
}
