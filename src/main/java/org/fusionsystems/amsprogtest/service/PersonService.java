package org.fusionsystems.amsprogtest.service;

import org.fusionsystems.amsprogtest.Person;

import java.util.List;

public interface PersonService {
    boolean insertPerson(Person person);

    List<Person> getPeople(String subName);
}
