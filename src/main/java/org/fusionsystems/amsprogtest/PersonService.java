package org.fusionsystems.amsprogtest;

import java.util.List;

interface PersonService {
    boolean insertPerson(Person person);

    List<Person> getPeople(String subName);
}
