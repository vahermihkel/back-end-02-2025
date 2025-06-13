package ee.mihkel.veebipood.controller;

import ee.mihkel.veebipood.dto.EmailPassword;
import ee.mihkel.veebipood.dto.PersonDTO;
import ee.mihkel.veebipood.entity.Person;
import ee.mihkel.veebipood.entity.PersonRole;
import ee.mihkel.veebipood.model.AuthToken;
import ee.mihkel.veebipood.repository.PersonRepository;
import ee.mihkel.veebipood.security.JwtUtil;
import ee.mihkel.veebipood.service.EmailService;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@RestController
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PersonRepository personRepository;

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    EmailService emailService;

    @PostMapping("login")
    public AuthToken login(@RequestBody EmailPassword emailPassword) {
//        emailService.sendEmail();
        log.info("Logging in... {}", emailPassword.getEmail());
        Person person = personRepository.findByEmail(emailPassword.getEmail());
        if (person != null) {
            if (encoder.matches(emailPassword.getPassword(), person.getPassword())) {
                log.info("Logged in successfully {}", emailPassword.getEmail());
                return jwtUtil.generateToken(person);
            } else {
                log.error("Password is not correct {}", emailPassword.getEmail());
                throw new RuntimeException("Vale parool!");
            }
        } else {
            log.error("Email is not correct {}", emailPassword.getEmail());
            throw new RuntimeException("Vale email!");
        }
    }

    @PostMapping("signup")
    public Person signup(@RequestBody Person person) {
        person.setPassword(encoder.encode(person.getPassword()));
        person.setRole(PersonRole.USER);
        return personRepository.save(person);
    }

    @GetMapping("person")
    public Person getPerson() {
        Long personId = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        return personRepository.findById(personId).orElseThrow();
    }

    @PutMapping("person")
    public Person editPerson(@RequestBody Person person) {
        Long personId = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        person.setId(personId);
        return personRepository.save(person);
    }

    @GetMapping("persons")
    public List<PersonDTO> getPersons() {
//        List<Person> persons = personRepository.findAll();
//        List<PersonDTO> personDTOs = new ArrayList<>();
//        for (Person p: persons) {
//            PersonDTO personDTO = new PersonDTO();
//            personDTO.setFirstName(p.getFirstName());
//            personDTO.setLastName(p.getLastName());
//        }
//        return personDTOs;
//        System.out.println("Fetching persons...");
        log.info("Fetching persons...");
        System.out.println(modelMapper);
        return List.of(modelMapper.map(personRepository.findAll(), PersonDTO[].class));
    }

    @PatchMapping("change-admin")
    public List<Person> changeAdmin(@PathVariable Long id, boolean isAdmin) {
        Person person = personRepository.findById(id).orElseThrow();
        if (isAdmin) {
            person.setRole(PersonRole.ADMIN);
        } else {
            person.setRole(PersonRole.USER);
        }
        personRepository.save(person);
        return personRepository.findAll();
    }

    @GetMapping("admin-persons")
    public List<Person> getAdminPersons() {
        return personRepository.findAll();
    }
}
