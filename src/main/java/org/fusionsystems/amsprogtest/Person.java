package org.fusionsystems.amsprogtest;

class Person {
    private final int age;
    private final PersonGender gender;
    private final String name;

    private Person(int age, PersonGender gender, String name) {
        this.age = age;
        this.gender = gender;
        this.name = name;
    }

    int getAge() {
        return age;
    }

    PersonGender getGender() {
        return gender;
    }

    String getName() {
        return name;
    }

    static class Builder {
        private int age;
        private PersonGender gender;
        private String name;

        Builder setAge(int age) {
            this.age = age;
            return this;
        }

        Builder setGender(PersonGender gender) {
            this.gender = gender;
            return this;
        }

        Builder setName(String name) {
            this.name = name;
            return this;
        }

        Person build() {
            return new Person(age, gender, name);
        }
    }
}
