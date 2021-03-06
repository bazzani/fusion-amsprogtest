package org.fusionsystems.amsprogtest;


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Unit test which proves that person controller's methods are working
 *
 * @author edward.yung
 */
public class PersonControllerTest {
    @Rule
    public ExpectedException expectedException;

    @Before
    public void setUp() {
        expectedException = ExpectedException.none();
    }

    /**
     * Tests the data access layer PersonService:insertPerson()
     * is inserted when age = 0, name is "First Last" and gender = MAN
     */
    @Test
    public void test_addPerson_validCase() {
        // given
        PersonDatabase db = new PersonDatabase.Builder().setDao(new ArrayList<>()).build();
        PersonController controller = PersonController.create(db);

        String correctName = "First Last";
        int correctAge = 0;
        PersonGender gender = PersonGender.MAN;
        Person person =
                new Person.Builder()
                        .setAge(correctAge)
                        .setGender(gender)
                        .setName(correctName)
                        .build();
        // when
        boolean result = controller.addPerson(person);

        // then
        assertArrayEquals(new Person[]{person}, db.getData().toArray());
        assertTrue(result);
    }

    /**
     * Tests the data access layer PersonService:insertPerson()
     * is not inserted when name is not in format “First Last”
     */
    @Test
    public void test_addPerson_wrongName() {
        // given
        PersonDatabase db = new PersonDatabase.Builder().setDao(new ArrayList<>()).build();
        PersonController controller = PersonController.create(db);

        String wrongName = "FirstLast";
        int correctAge = 0;
        PersonGender gender = PersonGender.MAN;

        // when
        boolean result =
                controller.addPerson(
                        new Person.Builder()
                                .setAge(correctAge)
                                .setGender(gender)
                                .setName(wrongName)
                                .build());

        // then
        assertArrayEquals(new Person[0], db.getData().toArray());
        assertFalse(result);
    }

    /**
     * Tests the data access layer PersonService:insertPerson()
     * is not inserted when age is -1
     */
    @Test(expected = Exception.class)
    public void test_addPerson_wrongAge() {
        // given
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Invalid age : -1");

        PersonDatabase db = new PersonDatabase.Builder().setDao(new ArrayList<>()).build();
        PersonController controller = PersonController.create(db);
        String correctName = "First Last";
        int wrongAge = -1;
        PersonGender gender = PersonGender.MAN;

        // when
        boolean result =
                controller.addPerson(
                        new Person.Builder()
                                .setAge(wrongAge)
                                .setGender(gender)
                                .setName(correctName)
                                .build());

        // then
        assertArrayEquals(new Person[0], db.getData().toArray());
        assertFalse(result);
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
        PersonController controller = PersonController.create(db);

        String testName = "John Last";
        int testAge = 51;
        PersonGender gender = PersonGender.MAN;

        // when
        boolean result =
                controller.addPerson(
                        new Person.Builder()
                                .setAge(testAge)
                                .setGender(gender)
                                .setName(testName)
                                .build());

        // then
        assertArrayEquals(new Person[0], db.getData().toArray());
        assertFalse(result);

        // given
        String testName2 = "NotJohn Last";

        Person person =
                new Person.Builder()
                        .setAge(testAge)
                        .setGender(gender)
                        .setName(testName2)
                        .build();
        // when
        result =
                controller.addPerson(person);

        // then
        assertArrayEquals(new Person[0], db.getData().toArray());
        assertFalse(result);
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
        PersonController controller = PersonController.create(db);

        String firstNameNotEndsWithKo = "ko_ Last";
        int testAge = 50;
        PersonGender gender = PersonGender.WOMAN;

        // when
        boolean result =
                controller.addPerson(
                        new Person.Builder()
                                .setAge(testAge)
                                .setGender(gender)
                                .setName(firstNameNotEndsWithKo)
                                .build());

        // then
        assertArrayEquals(new Person[0], db.getData().toArray());
        assertFalse(result);

        // given
        String firstNameEndsWithKo = "_ko Last";
        Person person = new Person.Builder()
                .setAge(testAge)
                .setGender(gender)
                .setName(firstNameEndsWithKo)
                .build();

        // when
        result = controller.addPerson(
                person);

        // then
        assertArrayEquals(new Person[]{person}, db.getData().toArray());
        assertTrue(result);
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
        int expectedAge = ageSum / personDatas.size();

        PersonService service =
                new PersonDatabase.Builder().setDao(personDatas).build();

        // when
        PersonController controller = PersonController.create(service);

        // then
        int averageAge = controller.calculateAverageAge(subName);
        assertEquals(expectedAge, averageAge);
    }
}
