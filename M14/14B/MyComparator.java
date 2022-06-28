

import java.util.Comparator;

public class MyComparator implements Comparator<String> {

    @Override
    public int compare(String o1, String o2) {
        int size = Math.min(o1.length(), o2.length());
        for (int i = 0; i < size; i++) {
            if (o1.charAt(i) > o2.charAt(i)) {
                return 1;
            } else if (o1.charAt(i) < o2.charAt(i)) {
                return -1;
            }
        }
        return Integer.compare(o1.length(), o2.length());
    }
}
