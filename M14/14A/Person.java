

public class Person implements Comparable<Person> {
    public final String id;
    public final String first_name;
    public final String last_name;
    public final int date_of_birth;

    public Person(String id, String first_name, String last_name, int date_of_birth) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.date_of_birth = date_of_birth;
    }

    @Override
    public int compareTo(Person o) {
        for (int i = 0; i < id.length(); i++) {
            if (Character.getNumericValue(this.id.charAt(i)) > Character.getNumericValue(o.id.charAt(i))) {
                return 1;
            } else if (Character.getNumericValue(this.id.charAt(i)) < Character.getNumericValue(o.id.charAt(i))) {
                return -1;
            }
        }
        return 0;
    }

    public String toString() {
        return this.id + ", " + this.first_name + ", " + this.last_name + ", " + this.date_of_birth;
    }

//    public static void main(String[] args) {
//        Person p1 = new Person("847263812", "Mike", "Ross", 1983);
//        Person p2 = new Person("276499374", "Lionel", "Messi", 1988);
//        Person p3 = new Person("415294704", "Rahel", "Furman", 2000);
//        Person p4 = new Person("553655391", "Idiot", "Shamen", 2014);
//        Person p5 = new Person("209387584", "Made", "Up", 2022);
//        MySet<Person> people = new MySet<>(new Person[]{p1, p2, p3, p4, p5});
//        System.out.println(GenericFunction.getMin(people));
//    }
}