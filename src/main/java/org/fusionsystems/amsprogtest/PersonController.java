package org.fusionsystems.amsprogtest;

public class PersonController {
    private final PersonDatabase personDatabase;
    private PersonService personService;

    private PersonController(PersonDatabase personDatabase, PersonService personService) {
        this.personDatabase = personDatabase;
        this.personService = personService;
    }

    static PersonController create(PersonDatabase db, PersonService personService) {
        return new PersonController(db, personService);
    }

    boolean addPerson(Person person) {
        if (personService.insertPerson(person)) {
            return personDatabase.addPerson(person);
        } else {
            return false;
        }
    }

    int calculateAverageAge(String subName) {
        double average = personDatabase.getData().stream()
                .filter(person -> person.getName().contains(subName))
                .mapToInt(Person::getAge)
                .summaryStatistics()
                .getAverage();

        return Double.valueOf(average)
                .intValue();
    }
}
