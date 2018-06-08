import demo.Application;
import demo.Person;
import demo.PersonRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * Created by mbaykova on 08.06.2018
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class PersonRestControllerTest {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;

    private HttpMessageConverter mappingJackson2HttpMessageConverter;


    private List<Person> personkList = new ArrayList<>();

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
                .findAny()
                .orElse(null);

        assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();

        this.personRepository.deleteAllInBatch();

        this.personkList.add(personRepository.save(new Person("Сидоров", "Иван")));
        this.personkList.add(personRepository.save(new Person("Пушкин", "Петр")));
    }

    @Test
    public void getSinglePerson() throws Exception {
        mockMvc.perform(get("/persons/0"
                + this.personkList.get(0).getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.firstName", is("Сидоров")))
                .andExpect(jsonPath("$.lastName", is("Иван")));
    }

    @Test
    public void getPersons() throws Exception {
        mockMvc.perform(get("/persons"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].firstName", is("Сидоров")))
                .andExpect(jsonPath("$[0].lastName", is("Иван")))
                .andExpect(jsonPath("$[1].firstName", is("Пушкин")))
                .andExpect(jsonPath("$[1].lastName", is("Петр")));
    }

    @Test
    public void createPerson() throws Exception {
        Person person = new Person("test1", "test");
        String personJson = json(person);

        this.mockMvc.perform(post("/persons/add/" )
                .contentType(contentType)
                .content(personJson))
                .andExpect(status().isCreated());
    }

    @Test
    public void updatePerson() throws Exception {
        Person person = new Person("test1", "test");
        String personJson = json(person);

        this.mockMvc.perform(put("/persons/update?personId=1")
                .contentType(contentType)
                .content(personJson))
                .andExpect(status().isOk());

        this.mockMvc.perform(get("/persons/1"
                + this.personkList.get(0).getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.firstName", is("test1")))
                .andExpect(jsonPath("$.lastName", is("test")));
    }


    @Test
    public void deletePerson() throws Exception {
        Person person = new Person("test1", "test");
        String personJson = json(person);

        this.mockMvc.perform(delete("/persons/delete?personId=Пушкин" )
                .contentType(contentType)
                .content(personJson))
                .andExpect(status().isOk());

        mockMvc.perform(get("/1")
                .contentType(contentType))
                .andExpect(status().isNotFound());
    }

    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }

}
