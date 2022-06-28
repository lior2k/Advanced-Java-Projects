

import java.io.File;
import java.io.FileWriter;
import java.util.Objects;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

public class MyDictionary {

    private SortedMap<String, String> dict;

    public SortedMap<String, String> getMap() {
        return dict;
    }

    public MyDictionary() {
        dict = new TreeMap<>(new MyComparator());
    }

    public void add(String term, String translation) {
        if (dict.containsKey(term)) {
            dict.put(term, dict.get(term) + ", " + translation);
        } else {
            dict.put(term, translation);
        }
    }

    public void delete(String term) {
        dict.remove(term);
    }


    public void update(String term, String translation) {
        if (dict.containsKey(term)) {
            dict.put(term, translation);
        }
    }

    public boolean save(String filename) {
        try {
            FileWriter fw = new FileWriter(filename);
            for (String term : dict.keySet()) {
                fw.write(term + ":" + dict.get(term) + "\n");
            }
            fw.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean load(String filename) {
        try {
            dict = new TreeMap<>(new MyComparator());
            Scanner sc = new Scanner(new File(filename));
            while (sc.hasNextLine()) {
                String row = sc.nextLine();
                if (Objects.equals(row, "\n")) {
                    break;
                }
                String[] arr = row.split(":");
                dict.put(arr[0], arr[1]);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getTerm(String key) {
        return dict.get(key);
    }

    public String toString() {
        return dict.toString();
    }

    public String toShow() {
        StringBuilder st = new StringBuilder();
        for (String key : dict.keySet()) {
            st.append(key).append(" : ").append(dict.get(key)).append("\n");
        }
        return st.toString();
    }

}
