

import java.util.Iterator;

public class GenericFunction {

    public static <T extends Comparable<T>> T getMin(MySet<T> values) {
        if (values.size() == 0) {
            return null;
        }
        T min = values.get(0);
        Iterator<T> iter = values.iterator();
        while (iter.hasNext()) {
            T temp = iter.next();
            if (temp.compareTo(min) < 0) {
                min = temp;
            }
        }
        return min;
    }

}
