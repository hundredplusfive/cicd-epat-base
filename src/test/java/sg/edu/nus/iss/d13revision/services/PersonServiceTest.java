package sg.edu.nus.iss.d13revision.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sg.edu.nus.iss.d13revision.models.Person;

class PersonServiceTest {
    private PersonService service;

    @BeforeEach
    void setUp() {
        service = new PersonService();
    }

    @Test
    void shouldInitializeWithTwoDefaultPersons() {
        List<Person> persons = service.getPersons();

        assertEquals(2, persons.size());
        assertTrue(persons.stream().anyMatch(p -> "Mark".equals(p.getFirstName()) && "Zuckerberg".equals(p.getLastName())));
        assertTrue(persons.stream().anyMatch(p -> "Elon".equals(p.getFirstName()) && "Musk".equals(p.getLastName())));
    }

    @Test
    void shouldAddPersonToTheList() {
        Person person = new Person("Ada", "Lovelace");

        service.addPerson(person);

        List<Person> persons = service.getPersons();
        assertEquals(3, persons.size());
        assertTrue(persons.stream().anyMatch(p -> "Ada".equals(p.getFirstName()) && "Lovelace".equals(p.getLastName())));
    }

    @Test
    void shouldUpdateExistingPersonById() {
        Person original = service.getPersons().get(0);
        Person updated = new Person(original.getId(), "Markus", "Zuckerberg Jr");

        service.updatePerson(updated);

        Person result = service.getPersons().stream()
                .filter(p -> original.getId().equals(p.getId()))
                .findFirst()
                .orElseThrow();

        assertEquals("Markus", result.getFirstName());
        assertEquals("Zuckerberg Jr", result.getLastName());
        assertEquals(2, service.getPersons().size());
    }

    @Test
    void shouldRemoveExistingPersonById() {
        Person personToRemove = service.getPersons().get(0);

        service.removePerson(personToRemove);

        assertEquals(1, service.getPersons().size());
        assertFalse(service.getPersons().stream().anyMatch(p -> p.getId().equals(personToRemove.getId())));
    }
}
