package org.fusionsystems.amsprogtest;

import java.util.ArrayList;
import java.util.List;

class PersonDatabase {
    private List<Person> dao;

    private PersonDatabase(List<Person> dao) {
        this.dao = dao;
    }

    List<Person> getData() {
        return new ArrayList<>(this.dao);
    }

    boolean insertPerson(Person person) {
        return this.dao.add(person);
    }

    static class Builder {
        private List<Person> dao;

        Builder setDao(List<Person> dao) {
            this.dao = dao;
            return this;
        }

        PersonDatabase build() {
            return new PersonDatabase(dao);
        }
    }
}
