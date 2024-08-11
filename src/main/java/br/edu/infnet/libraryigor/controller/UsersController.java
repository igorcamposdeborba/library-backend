package br.edu.infnet.libraryigor.controller;

import br.edu.infnet.libraryigor.model.entities.dto.UsersDTO;
import br.edu.infnet.libraryigor.model.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping(value = "/user") // rota
public class UsersController {
    @Autowired
    private UsersService userService;

    @GetMapping(value = {"/", ""})
    public ResponseEntity<List<UsersDTO>> findAll(){

        List<UsersDTO> cityList = userService.findAll();

        return ResponseEntity.ok().body(cityList);
    }
}
