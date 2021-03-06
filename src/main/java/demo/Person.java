package demo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by Maria on 06.06.2018.
 */

@Entity
public class Person {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private long id;

        private String firstName;

        private String lastName;

        private Person() { } // JPA only

        public Person(String firstName, String lastName){
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public long getId() {
            return id;
        }
}
