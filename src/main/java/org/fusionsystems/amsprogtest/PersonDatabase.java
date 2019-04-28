package org.fusionsystems.amsprogtest;

import java.util.ArrayList;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;

public class PersonDatabase implements PersonService {
    private final List<Person> data;

    private PersonDatabase(List<Person> data) {
        this.data = data;
    }

    public List<Person> getData() {
        return new ArrayList<>(data);
    }

    @Override
    public boolean insertPerson(Person person) {
        if (isValidPerson(person)) {
            return data.add(person);
        } else {
            return false;
        }
    }

    @Override
    public int calculateAverageAge(String subName) {
        IntSummaryStatistics stats = data.stream()
                .filter(person -> person.getName().contains(subName))
                .collect(Collectors.summarizingInt(Person::getAge));
        return (int) stats.getAverage();
    }

    private boolean isValidPerson(Person person) {
        boolean ageIsValid = isAgeValid(person);
        boolean nameIsInTwoParts = isNameInTwoParts(person);
        boolean isMan = isAMan(person);
        boolean isWomanNamedKo = isAWomanNamedKo(person);

        return ageIsValid &&
                nameIsInTwoParts &&
                (isMan || isWomanNamedKo);
    }

    private boolean isAgeValid(Person person) {
        int age = person.getAge();
        if (age < 0) {
            throw new IllegalArgumentException("Invalid age: " + age);
        } else {
            return age != 51;
        }
    }

    private boolean isNameInTwoParts(Person person) {
        return getNameParts(person).length == 2;
    }

    private String[] getNameParts(Person person) {
        return person.getName().split(" ");
    }

    private boolean isAMan(Person person) {
        return person.getGender().equals(PersonGender.MAN);
    }

    private boolean isAWomanNamedKo(Person person) {
        boolean isAWoman = person.getGender().equals(PersonGender.WOMAN);
        String firstName = getNameParts(person)[0];
        boolean firstNameEndsWithKo = firstName.endsWith("ko");

        return isAWoman && firstNameEndsWithKo;
    }

    public static class Builder {
        private List<Person> dao;

        public Builder setDao(ArrayList<Person> people) {
            this.dao = people;
            return this;
        }

        public PersonDatabase build() {
            return new PersonDatabase(this.dao);
        }
    }
}
