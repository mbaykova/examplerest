package demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;

/**
 * Created by mbaykova on 07.06.2018
 */


@RestController
@RequestMapping("/persons")
public class PersonRestController {

    private final PersonRepository personRepository;

    @Autowired
    PersonRestController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @GetMapping
    Collection<Person> getPersons() {
        return this.personRepository.findAll();
    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public  ResponseEntity<?> test(@RequestBody Person input) {
        System.out.println("123");
        Person person = new Person(input.getFirstName(), input.getLastName());
        this.personRepository.save(person);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(person.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{personId}")
    Person getPerson(@PathVariable Long personId) {
        return this.personRepository.findById(personId)
                .orElseThrow(() -> new PersonNotFoundException(personId));
    }

}
