package br.edu.infnet.libraryigor.model.entities.client;

import br.edu.infnet.libraryigor.model.entities.Library;
import br.edu.infnet.libraryigor.model.entities.dto.UsersDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED) // Cada subclasse tem sua propria tabela no banco de dados
public abstract class Users implements Serializable { // Classe abstrata para que ela não possa ser intanciada a agrega atributos em comum para as classes implementadoras
                                                     // Serializable para trafegar em rede por bytes
    private static final long serialVersionUID = 1L; // versão para serializacao/deserializacao para dar match com o que está sendo trafegado

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // JPA: Banco de dados gera id sequencial
    private Integer id;
    @NotBlank // nao permite vazio ou null
    private String name;
    @Email // valida formatos validos de email
    @NotBlank // nao permite vazio ou null
    private String email;
    private boolean active;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "library_id")
    @JsonBackReference // lado gerenciado no relacionamento de duas vias. Evita recursao infinita
    private Library library;

    public Users(String id, String name, String email, boolean active) {
        this.id = Objects.nonNull(id) ? Integer.parseInt(id) : null;
        this.name = name;
        this.email = email;
        this.active = active;
    }

    public Users(UsersDTO usersDTO) {
        this.id = Objects.nonNull(usersDTO.getId()) ? usersDTO.getId() : null;
        this.name = usersDTO.getName();
        this.email = usersDTO.getEmail();
        this.active = usersDTO.isActive();
    }

    public Users() {} // JPA precisa de construtor vazio público para persistir no banco de dados

    public Integer getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public boolean isActive() {
        return active;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setActive(boolean active) {
        this.active = active;
    }

    public Library getLibrary() {
        return library;
    }

    public void setLibrary(Library library) {
        this.library = library;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "USER{" +
                "id: " + id +
                ", name: " + name +
                ", email: " + email +
                ", active: " + active +
                '}';
    }
}
