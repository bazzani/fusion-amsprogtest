package org.fusionsystems.amsprogtest;

import java.util.ArrayList;
import java.util.List;

public class PersonDatabase {
    private ArrayList<Person> dao;

    private PersonDatabase(ArrayList<Person> dao) {
        this.dao = dao;
    }

    List<Person> getData() {
        return new ArrayList<>(dao);
    }

    boolean addPerson(Person person) {
        return dao.add(person);
    }

    static class Builder {
        private ArrayList<Person> dao;

        Builder setDao(ArrayList<Person> dao) {
            this.dao = dao;
            return this;
        }

        PersonDatabase build() {
            return new PersonDatabase(dao);
        }
    }
}
