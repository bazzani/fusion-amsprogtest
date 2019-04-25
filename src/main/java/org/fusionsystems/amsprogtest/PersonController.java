package org.fusionsystems.amsprogtest;

import org.fusionsystems.amsprogtest.service.PersonService;

import java.util.List;

class PersonController {
    private final PersonService personService;

    private PersonController(PersonService personService) {
        this.personService = personService;
    }

    static PersonController create(PersonService personService) {
        return new PersonController(personService);
    }

    boolean addPerson(Person person) {
        return personService.insertPerson(person);
    }

    int calculateAverageAge(String subName) {
        List<Person> people = personService.getPeople(subName);
        double average = people.stream()
                .mapToInt(Person::getAge)
                .summaryStatistics()
                .getAverage();

        return Double.valueOf(average)
                .intValue();
    }
}
