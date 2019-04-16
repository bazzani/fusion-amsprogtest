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
        if (ageIsLowerThanMinimum(person.getAge())) {
            throw new IllegalArgumentException("Person age is lower than the minimum: " + person.getAge());
        } else {
            boolean isValidPerson = isValidMan(person) || isValidWoman(person);
            if (isValidPerson) {
                personService.insertPerson(person);
                return true;
            } else {
                return false;
            }
        }
    }

    private boolean ageIsLowerThanMinimum(int personAge) {
        return personAge < 0;
    }

    private boolean isValidMan(Person person) {
        return person.getAge() == 0 &&
                person.getGender().equals(PersonGender.MAN) &&
                person.getName().equals("First Last");
    }

    private boolean isValidWoman(Person person) {
        String[] nameParts = person.getName().split(" ");
        String firstName = nameParts[0];

        return person.getGender().equals(PersonGender.WOMAN) &&
                person.getAge() == 50 &&
                firstName.endsWith("ko");
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
