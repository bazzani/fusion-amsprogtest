package org.fusionsystems.amsprogtest;

public class PersonController {
    private final PersonService personService;

    public static PersonController create(PersonService personService) {
        return new PersonController(personService);
    }

    private PersonController(PersonService personService) {
        this.personService = personService;
    }

    public boolean addPerson(Person person) {
        return personService.insertPerson(person);
    }

    public int calculateAverageAge(String subName) {
        return personService.calculateAverageAge(subName);
    }
}
