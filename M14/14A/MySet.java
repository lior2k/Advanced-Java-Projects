

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class MySet<T> {

    private ArrayList<T> values;


    /** MySet()
     * default constructor, initialize empty arraylist
     */
    public MySet() {
        values = new ArrayList<T>();
    }


    /** MySet(T[] arr)
     * constructor
     * @param arr - an array of values to initialize the set
     * @time_complexity O(n) - n -> arr length
     */
    public MySet(T[] arr) {
        values = new ArrayList<T>();
        for (T val : arr) {
            if (!values.contains(val)) {
                values.add(val);
            }
        }
    }


    /** size
     * @return number of elements in this set
     * @time_complexity O(1)
     */
    public int size() {
        return values.size();
    }


    /** isMember
     * @param val - any value of type T
     * @return true/false if the set contains given value
     * @time_complexity O(n)
     */
    public boolean isMember(T val) {
        return this.values.contains(val);
    }


    /** insert
     * @param val - any value of type T
     * @return true if val was inserted to the set or false if val was already a part of the set
     * @time_complexity O(n) - n -> number of elements in this set
     */
    public boolean insert(T val) {
        if (this.values.contains(val)) {
            return false;
        }
        this.values.add(val);
        return true;
    }


    /** delete
     * @param val - any value of type T
     * @return true if val was deleted or false if val was not a part of the set in the first place
     * @time_complexity O(n) - n -> number of elements in this set
     */
    public boolean delete(T val) {
        return this.values.remove(val);
    }


    /** get
     * @param index - position in the set
     * @return value at the desired position
     * @throws IndexOutOfBoundsException if index < 0 || index > size
     */
    public T get(int index) throws IndexOutOfBoundsException {
        return values.get(index);
    }


    /** union
     * @param otherSet - set to union with
     * loop over otherSet items and add any item not contained within this set
     * @time_complexity O(n*m) - n -> number of elements in this set, m -> number of elements in other set
     * @return this set as a union of both sets
     */
    public MySet<T> union(MySet<T> otherSet) {
        for (T val : otherSet.values) {
            if (!this.isMember(val)) {
                this.insert(val);
            }
        }
        return this;
    }


    /** intersect
     * @param otherSet - set to intersect with
     * @return this set after intersecting with otherSet
     * @time_complexity O(n) - n -> the number of elements of the larger set
     * @implementation - in order to compute the intersection faster than O(n^2) time we
     *     begin by creating a hashset of our main set (O(n)), that way we can check if a value is
     *     a member in O(1) time, than all that is left is to iterate over the other set (O(n)) and
     *     add to the result set any value that is contained in the hashset of the first set.
     */
    public MySet<T> intersect(MySet<T> otherSet) {
        ArrayList<T> intersectionSet = new ArrayList<>();
        HashSet<T> hashSet = new HashSet<>(this.values);
        for (T val : otherSet.values) {
            if (hashSet.contains(val)) {
                intersectionSet.add(val);
            }
        }
        this.values = intersectionSet;
        return this;
    }


    /** isSubset
     * @param isSub - set to be determined if subset of this set
     * @return true/false if param is a subset of this set
     * @time_complexity O(n*m) - n -> number of elements in this set, m -> number of elements in subset
     */
    public boolean isSubset(MySet<T> isSub) {
        for (T val : isSub.values) {
            if(!this.isMember(val)) {
                return false;
            }
        }
        return true;
    }


    /**
     * Iterator
     * @return iterator of the arraylist representing the set
     */
    public Iterator<T> iterator() {
        return this.values.iterator();
    }

    public String toString() {
        return this.values.toString();
    }

}
