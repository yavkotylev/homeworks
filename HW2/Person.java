package homeworks.HW2;

/**
 * Created by Yaroslav on 23.07.16.
 */


public class Person {
    private final boolean man;
    private final String name;
    private Person spouse;

    public Person(boolean man, String name) {
        this.man = man;
        this.name = name;

    }

    public boolean marry(Person person) {

        if ((man == person.man) || this == person.spouse) {
            return false;
        }
        if (spouse != null) {
            spouse.divorce();
        }

        if (person.spouse != null) {
            person.spouse.divorce();
        }

        person.spouse = this;
        spouse = person;
        return true;
    }


    public boolean divorce() {
        if (spouse != null) {
            spouse.spouse = null;
            this.spouse = null;
            return true;
        }
        return false;
    }
}
