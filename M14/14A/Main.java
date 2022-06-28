

import java.util.Iterator;
import java.util.Scanner;

public class Main {

    public static String generateRandomID() {
        StringBuilder id = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            int digit = (int) (Math.random() * 9);
            id.append((char) (digit + '0'));
        }
        return id.toString();
    }

    public static void main(String[] args) {
        System.out.println("------------------------sub section 1------------------------------\n");
        MySet<Integer> set_A = new MySet<>();
        MySet<Integer> set_B = new MySet<>();
        MySet<Integer> set_C = new MySet<>();
        for (int i = 0; i < 10; i ++) {
            set_A.insert((int) (Math.random() * 100));
            set_B.insert((int) (Math.random() * 100));
            set_C.insert((int) (Math.random() * 100));
        }
        System.out.print("Set A: ");
        System.out.println(set_A);
        System.out.print("Set B: ");
        System.out.println(set_B);
        System.out.print("Set C: ");
        System.out.println(set_C);
        System.out.print("[A union B]: ");
        System.out.println(set_A.union(set_B));
        System.out.print("[(A union B) intersection C]: ");
        System.out.println(set_A.intersect(set_C));

        Scanner sc = new Scanner(System.in);
        System.out.println("Insert two numbers in range 0,...,100 to create set D.");
        System.out.print("Enter the first number: ");
        int num1 = Integer.parseInt(sc.next());
        System.out.print("Enter the second number: ");
        int num2 = Integer.parseInt(sc.next());
        Integer[] arr = {num1, num2};
        MySet<Integer> set_D = new MySet<>(arr);
        if (set_A.isSubset(set_D)) {
            System.out.println("D is a subset of A.");
        } else {
            System.out.println("D is not a subset of A.");
        }
        if (set_B.isSubset(set_D)) {
            System.out.println("D is a subset of B.");
        } else {
            System.out.println("D is not a subset of B.");
        }
        if (set_C.isSubset(set_D)) {
            System.out.println("D is a subset of C.");
        } else {
            System.out.println("D is not a subset of C.");
        }

        System.out.print("Enter a number in range 0,...,100: ");
        int num3 = Integer.parseInt(sc.next());
        if (set_A.isMember(num3)) {
            System.out.println(num3 + " is a member of A.");
        } else {
            System.out.println(num3 + " is not a member of set A.");
        }
        set_B.insert(num3);
        System.out.println("B after inserting " + num3 + ": " + set_B);
        set_C.delete(num3);
        System.out.println("C after deleting " + num3 + ": " + set_C);

        System.out.println("\n---------------------end sub section 1------------------------------");

        System.out.println("\n-----------------------sub section 2---------------------------------\n");

        System.out.println("Generating 5 persons with random ids:");
        Person p1 = new Person(generateRandomID(), "Mike", "Ross", 1983);
        Person p2 = new Person(generateRandomID(), "Lionel", "Messi", 1988);
        Person p3 = new Person(generateRandomID(), "Rahel", "Furman", 2000);
        Person p4 = new Person(generateRandomID(), "Genius", "Brain", 2014);
        Person p5 = new Person(generateRandomID(), "Made", "Up", 2022);
        MySet<Person> people = new MySet<>(new Person[]{p1, p2, p3, p4, p5});
        Iterator<Person> iter = people.iterator();
        while (iter.hasNext()) {
            System.out.println("Person: " + iter.next());
        }
        System.out.println("Minimum person found: " + GenericFunction.getMin(people));
        System.out.println("\n-------------------end sub section 2----------------------------------");
    }

}
