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
        return this.db.insertPerson(person);
    }

    int calculateAverageAge(String subName) {
        return 0;
    }
}
