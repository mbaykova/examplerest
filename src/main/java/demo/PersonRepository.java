package demo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by mbaykova on 07.06.2018
 */
public interface PersonRepository extends JpaRepository<Person, Long> {
    Optional<Person> findByFirstName(String firstName);
}