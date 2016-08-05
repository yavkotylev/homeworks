package homeworks.HW2;

/**
 * Created by Yaroslav on 26.07.16.
 */
public class LifeSimulator {
    public static void main(String[] args) {
        Person man1 = new Person(true, "Bob");
        Person man2 = new Person(true, "Tommy");
        Person man3 = new Person(true, "Sem");

        Person woman1 = new Person(false, "Sandra");
        Person woman2 = new Person(false, "Kate");
        Person woman3 = new Person(false, "Elly");


        tryMarry(man1, woman1, true);
        tryMarry(man1, woman1, false);

        tryDivorce(man1, true);
        tryDivorce(man1, false);
        tryDivorce(woman1, false);

        tryMarry(man1, woman1, true);
        tryMarry(man2, man3, false);

        tryMarry(woman2, woman3, false);




    }

    public static void tryMarry(Person p1, Person p2, boolean result){
        if (p1.marry(p2) == result){
            System.out.println("OK");
        }else{
            System.out.println("Error");
        }
    }

    public static void tryDivorce(Person p1, boolean result){
        if (p1.divorce() == result){
            System.out.println("OK");
        }else{
            System.out.println("Error");
        }
    }



}