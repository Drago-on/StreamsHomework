package co.inventorsoft;

import co.inventorsoft.model.Person;
import co.inventorsoft.model.User;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class StreamHomeworkTest {

    private StreamHomework homework;
    private List<Person> people;
    private List<String> emails;

    @Before
    public void initializeTest() {
        homework = new StreamHomework();
        people = Arrays.asList(
                new Person("Harry", 15),
                new Person("Luke", 24),
                new Person("Donald", 71),
                new Person("Roma", 24),
                new Person("Luke", 35)
        );
        emails = Arrays.asList("a@gmail.com", "b@gmail.com", "c@gmail.com", "a@gmail.com", "d@gmail.com", null);
    }

    @Test
    public void extractTeenagersTest() throws Exception {
        final List<Person> teenagers = homework.extractTeenagers(people);
        final boolean isFilterCorrect = teenagers.stream()
                .map(Person::getAge)
                .allMatch(age -> age >= 13 && age <= 19);
        assertTrue(isFilterCorrect);
    }

    @Test
    public void createUsersTest() throws Exception {
        final List<User> users = homework.createUsers(emails);
        final Set<String> distinctEmails = new HashSet<>(emails);
        distinctEmails.remove(null);
        final List<String> userEmails = users.stream().map(User::getEmail).collect(toList());
        assertEquals(distinctEmails.size(), userEmails.size());
    }

    @Test
    public void groupByEmailTest() throws Exception {
        final List<User> users = homework.createUsers(emails);
        final Map<String, User> usersByEmail = homework.groupByEmail(users);

        for (User user : users) {
            assertTrue(usersByEmail.containsKey(user.getEmail()));
        }
    }

    @Test
    public void groupByAgeTest() throws Exception {
        final Map<Integer, List<Person>> peopleByAge = homework.groupByAge(people);
        assertEquals(4, peopleByAge.size());
        people.stream().map(Person::getAge).forEach(age -> assertTrue(peopleByAge.containsKey(age)));
        assertEquals(2, peopleByAge.get(24).size());
    }

    @Test
    public void collectDistinctNamesTest() throws Exception {
        final String distinctNames = homework.collectDistinctNames(people);
        assertEquals("Distinct names: Harry, Luke, Donald, Roma!", distinctNames);
    }
}