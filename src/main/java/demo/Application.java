package demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by Maria on 06.06.2018.
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


	@Bean
    CommandLineRunner init(PersonRepository personRepository) {
        HashMap<String, String> persons = new HashMap<>();
        persons.put("Иванов", "Иван");
        persons.put("Петров", "Петр");
		return (evt) -> persons.forEach((k,v) -> personRepository.save(new Person(k,v)));
	}

}
