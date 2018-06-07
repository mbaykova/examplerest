package demo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by Maria on 06.06.2018.
 */
public class PersonController {


	@RequestMapping("/greeting")
	public List<Person> getPersons() {
		return new Person(counter.incrementAndGet(),
				String.format(template, name));
	}
}
