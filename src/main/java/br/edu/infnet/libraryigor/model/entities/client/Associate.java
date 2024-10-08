package br.edu.infnet.libraryigor.model.entities.client;
import br.edu.infnet.libraryigor.model.entities.dto.UsersDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "associate")
@JsonIgnoreProperties(ignoreUnknown = true) // jackson ignora campos que ele não encontra na serializacao/deserializacao do json: serve para trocar o tipo de Student para Associate
public class Associate extends Users {
    private String department;
    private String specialty;

    public Associate(String id, String name, String email, boolean active, String department, String specialty) {
        super(id, name, email, active);
        this.department = department;
        this.specialty = specialty;
    }
    public Associate(UsersDTO associate) {
        super(Objects.nonNull(associate.getId()) ? associate.getId().toString() : null,
              associate.getName(),
              associate.getEmail(),
              associate.isActive());
        this.department = associate.getDepartment();
        this.specialty = associate.getSpecialty();
    }

    public Associate() { super(); } // JPA precisa de construtor vazio público para persistir no banco de dados

    public String getDepartment() {
        return department;
    }
    public String getSpecialty() {
        return specialty;
    }
    public void setDepartment(String department) {
        this.department = department;
    }
    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    @Override
    public String toString() {
        return  "ASSOCIATE{" +
                "id: " + this.getId() +
                ", name: "+ this.getName() +
                ", email: " + this.getEmail() +
                ", active: " + this.isActive() +
                ", department:'" + department + '\'' +
                ", specialty:'" + specialty + '\'' +
                '}';
    }
}
