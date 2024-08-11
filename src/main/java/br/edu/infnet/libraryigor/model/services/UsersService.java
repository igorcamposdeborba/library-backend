package br.edu.infnet.libraryigor.model.services;

import br.edu.infnet.libraryigor.model.entities.client.Users;
import br.edu.infnet.libraryigor.model.entities.dto.UsersDTO;
import br.edu.infnet.libraryigor.model.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UsersService {
    @Autowired
    private UserRepository userRepository; // injetar instancia do repository para buscar do banco de dados via JPA

    public List<UsersDTO> findAll(){
        List<Users> usersList = userRepository.findAll(Sort.by("name")); // buscar no banco de dados e ordenar por nome
        // converter a lista de classe para DTO
        return usersList.stream().filter(Objects::nonNull).map((Users user) -> new UsersDTO(user)).collect(Collectors.toList());
    }
}
