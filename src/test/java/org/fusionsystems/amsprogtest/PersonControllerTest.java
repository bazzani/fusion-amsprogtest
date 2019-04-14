package org.fusionsystems.amsprogtest;


import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * Unit test which proves that person controller's methods are working
 *
 * @author edward.yung
 */
public class PersonControllerTest {

    private PersonDatabase db;
    private PersonController controller;

    @Before
    public void setup() {
        db = new PersonDatabase.Builder().setDao(new ArrayList<>()).build();
        controller = PersonController.create(db);
    }

    /**
     * Tests the data access layer PersonService:insertPerson()
     * is inserted when age = 0, name is "First Last" and gender = MAN
     */
    @Test
    public void test_addPerson_validCase() {
        // given
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
        assertArrayEquals(db.getData().toArray(), new Person[]{person});
        assertEquals(result, true);
    }

    /**
     * Tests the data access layer PersonService:insertPerson()
     * is not inserted when name is not in format “First Last”
     */
    @Test
    public void test_addPerson_wrongName() {
        //given
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
        assertEquals(result, false);
    }

    /**
     * Tests the data access layer PersonService:insertPerson()
     * is not inserted when age is -1
     */
    @Test
    public void test_addPerson_wrongAge() {
        // given
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
        assertEquals(result, false);
    }

    /**
     * Tests the data access layer PersonService:insertPerson()
     * is not inserted when age is 51 and first name is John
     * is inserted when age is 51 and first name is not John
     */
    @Test
    public void test_addPerson_specialAge() {
        // given
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
        assertEquals(result, false);

        // given
        String testName2 = "NotJohn Last";
        Person person =
                new Person.Builder()
                        .setAge(testAge)
                        .setGender(gender)
                        .setName(testName2)
                        .build();

        // when
        result = controller.addPerson(person);

        // then
        assertArrayEquals(new Person[]{person}, db.getData().toArray());
        assertEquals(result, true);
    }

    /**
     * Tests the data access layer PersonService:insertPerson()
     * is not inserted when gender is WOMAN, age is 50 and first name not ends with "ko",
     * and is inserted when gender is WOMAN, age is 50 and first name is ended with "ko"
     * and is inserted when gender is WOMAN, age is not 50 and first name is ended with "ko"
     */
    @Test
    public void test_addPerson_woman_name() {
        // given
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
        assertEquals(result, false);

        // given
        String firstNameEndsWithKo = "_ko Last";
        Person person = new Person.Builder()
                .setAge(testAge)
                .setGender(gender)
                .setName(firstNameEndsWithKo)
                .build();

        // when
        result = controller.addPerson(person);

        // then
        assertArrayEquals(new Person[]{person}, db.getData().toArray());
        assertEquals(result, true);

        // given
        int nonExcludedAge = 51;
        Person womanWithNonExcludedAge = new Person.Builder()
                .setAge(nonExcludedAge)
                .setGender(gender)
                .setName(firstNameNotEndsWithKo)
                .build();

        // when
        result = controller.addPerson(womanWithNonExcludedAge);

        // then
        assertArrayEquals(new Person[]{person, womanWithNonExcludedAge}, db.getData().toArray());
        assertEquals(result, true);
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

        db = new PersonDatabase.Builder().setDao(personDatas).build();
        controller = PersonController.create(db);

        // when
        int averageAge = controller.calculateAverageAge(subName);

        // then
        assertEquals(averageAge, ageSum / personDatas.size());
    }
}
