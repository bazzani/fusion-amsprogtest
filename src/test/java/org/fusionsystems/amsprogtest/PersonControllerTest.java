package org.fusionsystems.amsprogtest;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit test which proves that person controller's methods are working
 *
 * @author edward.yung
 */
@RunWith(MockitoJUnitRunner.class)
public class PersonControllerTest {
    @Mock
    private PersonService personService;

    /**
     * Tests the data access layer PersonService:insertPerson()
     * is inserted when age = 0, name is "First Last" and gender = MAN
     */
    @Test
    public void test_addPerson_validCase() {
        // given
        PersonDatabase db = new PersonDatabase.Builder().setDao(new ArrayList<>()).build();
//        PersonController controller = PersonController.create(db, personService);
        PersonController controller = PersonController.create(personService);

        String correctName = "First Last";
        int correctAge = 0;
        PersonGender gender = PersonGender.MAN;
        Person person = new Person.Builder()
                .setAge(correctAge)
                .setGender(gender)
                .setName(correctName)
                .build();

        when(personService.insertPerson(person))
                .then(invocation -> {
                            Person personArg = invocation.getArgument(0);
                            if (personArg.getAge() == 0 &&
                                    personArg.getName().equals("First Last") &&
                                    personArg.getGender().equals(PersonGender.MAN)) {
                                return db.addPerson(personArg);
                            } else {
                                return false;
                            }
                        }
                );

        // when
        boolean result = controller.addPerson(person);

        // then
        assertArrayEquals(new Person[]{person}, db.getData().toArray());
        assertTrue(result);
        verify(personService).insertPerson(person);
    }

    /**
     * Tests the data access layer PersonService:insertPerson()
     * is not inserted when name is not in format “First Last”
     */
    @Test
    public void test_addPerson_wrongName() {
        // given
        PersonDatabase db = new PersonDatabase.Builder().setDao(new ArrayList<>()).build();
//        PersonController controller = PersonController.create(db, personService);
        PersonController controller = PersonController.create(personService);

        String wrongName = "FirstLast";
        int correctAge = 0;
        PersonGender gender = PersonGender.MAN;
        Person person = new Person.Builder()
                .setAge(correctAge)
                .setGender(gender)
                .setName(wrongName)
                .build();

        when(personService.insertPerson(person))
                .then(invocation -> {
                            Person personArg = invocation.getArgument(0);
                            String[] nameParts = personArg.getName().split(" ");
                            return nameParts.length == 2;
                        }
                );

        // when
        boolean result = controller.addPerson(person);

        // then
        assertArrayEquals(new Person[0], db.getData().toArray());
        assertFalse(result);
        verify(personService).insertPerson(person);
    }

    /**
     * Tests the data access layer PersonService:insertPerson()
     * is not inserted when age is -1
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_addPerson_wrongAge() {
        // given
        PersonDatabase db = new PersonDatabase.Builder().setDao(new ArrayList<>()).build();
//        PersonController controller = PersonController.create(db, personService);
        PersonController controller = PersonController.create(personService);

        String correctName = "First Last";
        int wrongAge = -1;
        PersonGender gender = PersonGender.MAN;
        Person person = new Person.Builder()
                .setAge(wrongAge)
                .setGender(gender)
                .setName(correctName)
                .build();

        when(personService.insertPerson(person))
                .then(invocation -> {
                            Person personArg = invocation.getArgument(0);
                            if (personArg.getAge() < 0) {
                                throw new IllegalArgumentException("Age is invalid : " + personArg.getAge());
                            }

                            return true;
                        }
                );

        // when
        controller.addPerson(person);

        // then
        fail("Exception expected when age is invalid");
    }

    /**
     * Tests the data access layer PersonService:insertPerson()
     * is not inserted when age is 51 and first name is John
     * is not inserted when age is 51 and first name is not John
     */
    @Test
    public void test_addPerson_specialAge() {
        // given
        PersonDatabase db = new PersonDatabase.Builder().setDao(new ArrayList<>()).build();
//        PersonController controller = PersonController.create(db, personService);
        PersonController controller = PersonController.create(personService);

        String testName = "John Last";
        int testAge = 51;
        PersonGender gender = PersonGender.MAN;
        Person personJohn = new Person.Builder()
                .setAge(testAge)
                .setGender(gender)
                .setName(testName)
                .build();

        when(personService.insertPerson(personJohn))
                .then(invocation -> {
                            Person personArg = invocation.getArgument(0);
                            return personArg.getAge() != 51;
                        }
                );

        // when
        boolean result = controller.addPerson(personJohn);

        // then
        assertArrayEquals(new Person[0], db.getData().toArray());
        assertFalse(result);
        verify(personService).insertPerson(personJohn);


        // given
        String testName2 = "NotJohn Last";
        Person personNotJohn = new Person.Builder()
                .setAge(testAge)
                .setGender(gender)
                .setName(testName2)
                .build();

        // when
        result = controller.addPerson(personNotJohn);

        // then
        assertArrayEquals(new Person[0], db.getData().toArray());
        assertFalse(result);
        verify(personService).insertPerson(personNotJohn);
    }

    /**
     * Tests the data access layer PersonService:insertPerson()
     * is not inserted when gender is WOMAN, age is 50 and first name not ends with "ko",
     * and is inserted when gender is WOMAN, age is 50 and first name is ended with "ko"
     */
    @Test
    public void test_addPerson_woman_name() {
        // given
        PersonDatabase db = new PersonDatabase.Builder().setDao(new ArrayList<>()).build();
//        PersonController controller = PersonController.create(db, personService);
        PersonController controller = PersonController.create(personService);

        String firstNameNotEndsWithKo = "ko_ Last";
        int testAge = 50;
        PersonGender gender = PersonGender.WOMAN;
        Person womanFirstNameNotEndsWithKo = new Person.Builder()
                .setAge(testAge)
                .setGender(gender)
                .setName(firstNameNotEndsWithKo)
                .build();

        when(personService.insertPerson(womanFirstNameNotEndsWithKo))
                .then(invocation -> {
                    Person personArg = invocation.getArgument(0);
                    if (mockInsertPersonInvocationHandler(personArg)) {
                        return db.addPerson(personArg);
                    } else {
                        return false;
                    }
                });

        // when
        boolean result = controller.addPerson(womanFirstNameNotEndsWithKo);

        // then
        assertArrayEquals(new Person[0], db.getData().toArray());
        assertFalse(result);
        verify(personService).insertPerson(womanFirstNameNotEndsWithKo);

        // given
        String firstNameEndsWithKo = "_ko Last";
        Person womanFirstNameEndsWithKo = new Person.Builder()
                .setAge(testAge)
                .setGender(gender)
                .setName(firstNameEndsWithKo)
                .build();

        when(personService.insertPerson(womanFirstNameEndsWithKo))
                .then(invocation -> {
                    Person personArg = invocation.getArgument(0);
                    if (mockInsertPersonInvocationHandler(personArg)) {
                        return db.addPerson(personArg);
                    } else {
                        return false;
                    }
                });

        // when
        result = controller.addPerson(womanFirstNameEndsWithKo);

        // then
        assertArrayEquals(new Person[]{womanFirstNameEndsWithKo}, db.getData().toArray());
        assertTrue(result);
        verify(personService).insertPerson(womanFirstNameEndsWithKo);
    }

    private boolean mockInsertPersonInvocationHandler(Person person) {
        String[] nameParts = person.getName().split(" ");
        String firstName = nameParts[0];
        return person.getGender().equals(PersonGender.WOMAN) &&
                person.getAge() == 50 &&
                firstName.endsWith("ko");
    }

    /**
     * Test for method: calculateAverageAge() by generating 10 random Person data with subName = "Fir"
     */
    @Test
    public void test_calculateAverageAge() {
        // given
        String defaultName = "First Last";
        String subName = "Fir";

        PersonGender gender = PersonGender.MAN;
        int ageSum = 0;
        ArrayList<Person> personDatas = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            int age = Math.abs(new Double(Math.random() * 100).intValue());
            ageSum += age;
            personDatas.add(
                    new Person.Builder()
                            .setAge(age)
                            .setGender(gender)
                            .setName(defaultName)
                            .build());
        }

        PersonDatabase db = new PersonDatabase.Builder().setDao(personDatas).build();
        when(personService.getPeople(subName)).then(invocation -> db.getData()
                .stream()
                .filter(person -> person.getName().contains(subName))
                .collect(Collectors.toList()));

//        PersonController controller = PersonController.create(db, personService);
        PersonController controller = PersonController.create(personService);
        int expectedAverage = ageSum / personDatas.size();

        // when
        int averageAge = controller.calculateAverageAge(subName);

        // then
        assertEquals(expectedAverage, averageAge);
    }
}
