package org.fusionsystems.amsprogtest;

import java.util.List;

class PersonController {
    //    private final PersonDatabase personDatabase;
    private final PersonService personService;

//    private PersonController(PersonDatabase personDatabase, PersonService personService) {
//        this.personDatabase = personDatabase;
//        this.personService = personService;
//    }

    private PersonController(PersonService personService) {
        this.personService = personService;
    }

    static PersonController create(PersonService personService) {
        return new PersonController(personService);
    }

//    static PersonController create(PersonDatabase db, PersonService personService) {
//        return new PersonController(db, personService);
//    }

    boolean addPerson(Person person) {
        return personService.insertPerson(person);
//        if (personService.insertPerson(person)) {
//            return personDatabase.addPerson(person);
//        } else {
//            return false;
//        }
    }

    int calculateAverageAge(String subName) {
        List<Person> people = personService.getPeople(subName);
//        double average = personDatabase.getData().stream()
        double average = people.stream()
                .mapToInt(Person::getAge)
                .summaryStatistics()
                .getAverage();

        return Double.valueOf(average)
                .intValue();
    }
}
