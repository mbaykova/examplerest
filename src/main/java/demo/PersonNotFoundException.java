package demo;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by mbaykova on 07.06.2018
 */

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PersonNotFoundException extends RuntimeException {

    public PersonNotFoundException(String personId) {
        super("could not find person '" + personId + "'.");
    }

    public PersonNotFoundException(Long personId) {
        super("could not find person '" + personId + "'.");
    }
}
