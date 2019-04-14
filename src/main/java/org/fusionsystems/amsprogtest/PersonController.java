package org.fusionsystems.amsprogtest;

class PersonController {
    private final PersonDatabase db;

    private PersonController(PersonDatabase db) {
        this.db = db;
    }

    static PersonController create(PersonDatabase db) {
        return new PersonController(db);
    }

    boolean addPerson(Person person) {
        if (isAValidPerson(person)) {
            return this.db.insertPerson(person);
        } else {
            return false;
        }
    }

    private boolean isAValidPerson(Person person) {
        return person.nameFormatIsCorrect() &&
                person.ageIsValid() &&
                person.isNotASpecialAgedJohn();
    }

    int calculateAverageAge(String subName) {
        return 0;
    }
}
