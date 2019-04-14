package org.fusionsystems.amsprogtest;

class Person {
    private Person(int age, PersonGender gender, String name) {
        this.age = age;
        this.gender = gender;
        this.name = name;
    }


    private int age;
    private PersonGender gender;
    private final String name;

    boolean nameFormatIsCorrect() {
        String[] nameParts = getNameParts();
        return nameParts.length == 2;
    }

    private String[] getNameParts() {
        return this.name.split(" ");
    }

    boolean ageIsValid() {
        return this.age > -1;
    }

    boolean isNotASpecialAgedJohn() {
        return !(this.age == 51 && isNamedJohn());
    }

    private boolean isNamedJohn() {
        String[] nameParts = getNameParts();
        return nameParts[0].equals("John");
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
