package org.fusionsystems.amsprogtest;


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
    /**
     * Creates new instance.
     */
    public PersonControllerTest() {

    }

    /**
     * Tests the data access layer PersonService:insertPerson()
     * is inserted when age = 0, name is "First Last" and gender = MAN
     */
    @Test
    public void test_addPerson_validCase() {
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
        boolean result = controller.addPerson(person);

        assertArrayEquals(db.getData().toArray(), new Person[]{person});
        assertEquals(result, true);
    }

    /**
     * Tests the data access layer PersonService:insertPerson()
     * is not inserted when name is not in format “First Last”
     */
    @Test
    public void test_addPerson_wrongName() {
        PersonDatabase db = new PersonDatabase.Builder().setDao(new ArrayList<>()).build();
        PersonController controller = PersonController.create(db);

        String wrongName = "FirstLast";
        int correctAge = 0;
        PersonGender gender = PersonGender.MAN;

        boolean result =
                controller.addPerson(
                        new Person.Builder()
                                .setAge(correctAge)
                                .setGender(gender)
                                .setName(wrongName)
                                .build());

        assertArrayEquals(new Person[0], db.getData().toArray());
        assertEquals(result, false);
    }

    /**
     * Tests the data access layer PersonService:insertPerson()
     * is not inserted when age is -1
     */
    @Test(expected = Exception.class)
    public void test_addPerson_wrongAge() {
        PersonDatabase db = new PersonDatabase.Builder().setDao(new ArrayList<Person>()).build();
        PersonController controller = PersonController.create(db);
        String correctName = "First Last";
        int wrongAge = -1;
        PersonGender gender = PersonGender.MAN;

        boolean result =
                controller.addPerson(
                        new Person.Builder()
                                .setAge(wrongAge)
                                .setGender(gender)
                                .setName(correctName)
                                .build());

        assertArrayEquals(new Person[0], db.getData().toArray());
        assertEquals(result, false);
    }

    /**
     * Tests the data access layer PersonService:insertPerson()
     * is not inserted when age is 51 and first name is John
     * is not inserted when age is 51 and first name is not John
     */
    @Test
    public void test_addPerson_specialAge() {
        PersonDatabase db = new PersonDatabase.Builder().setDao(new ArrayList<Person>()).build();
        PersonController controller = PersonController.create(db);

        String testName = "John Last";
        int testAge = 51;
        PersonGender gender = PersonGender.MAN;

        boolean result =
                controller.addPerson(
                        new Person.Builder()
                                .setAge(testAge)
                                .setGender(gender)
                                .setName(testName)
                                .build());

        assertArrayEquals(new Person[0], db.getData().toArray());
        assertEquals(result, false);

        String testName2 = "NotJohn Last";

        Person person =
                new Person.Builder()
                        .setAge(testAge)
                        .setGender(gender)
                        .setName(testName2)
                        .build();
        result =
                controller.addPerson(person);

        assertArrayEquals(new Person[]{person}, db.getData().toArray());
        assertEquals(result, true);

    }

    /**
     * Tests the data access layer PersonService:insertPerson()
     * is not inserted when gender is WOMAN, age is 50 and first name not ends with "ko",
     * and is inserted when gender is WOMAN, age is 50 and first name is ended with "ko"
     */
    @Test
    public void test_addPerson_womon_name() {
        PersonDatabase db = new PersonDatabase.Builder().setDao(new ArrayList<Person>()).build();
        PersonController controller = PersonController.create(db);

        String firstNameNotEndsWithKo = "ko_ Last";
        int testAge = 51;
        PersonGender gender = PersonGender.WOMAN;

        boolean result =
                controller.addPerson(
                        new Person.Builder()
                                .setAge(testAge)
                                .setGender(gender)
                                .setName(firstNameNotEndsWithKo)
                                .build());


        assertArrayEquals(new Person[0], db.getData().toArray());
        assertEquals(result, false);

        String firstNameEndsWithKo = "_ko Last";
        Person person = new Person.Builder()
                .setAge(testAge)
                .setGender(gender)
                .setName(firstNameEndsWithKo)
                .build();
        result = controller.addPerson(
                person);


        assertArrayEquals(new Person[]{person}, db.getData().toArray());
        assertEquals(result, true);
    }

    /**
     * Test for method: calculateAverageAge() by generating 10 random Person data with subName = "Fir"
     */
    @Test
    public void test_calculateAverageAge() {
        String defaultName = "First Last";
        String subName = "Last";

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

        PersonDatabase service =
                new PersonDatabase.Builder().setDao(personDatas).build();

        PersonController controller = PersonController.create(service);
        int averageAge = controller.calculateAverageAge(subName);
        assertEquals(averageAge, ageSum / personDatas.size());
    }

}
