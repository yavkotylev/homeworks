package homeworks.HW4.collectionUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Yaroslav on 05.08.16.
 */



public class CollectionUtils {

    public static <T> void addAll(List<? extends T> source, List<? super T> destination) {

        destination.addAll(source);

    }


    public static <T> List<? super T> newArrayList() {
        List<? super T> ar = new ArrayList<>();
        return ar;
    }


    public static <T> int indexOf(List<? super T> source, T o) {
        return source.indexOf(o);
    }


    public static <T> List limit(List <T> source, int size) {
        List<T> newList = new ArrayList<>();
        for (int i = 0; i < size; i++){
            newList.add(source.get(i));
        }
        return newList;
    }


    public static <T> void add(List<? super T> source, T o) {
        source.add(o);
    }


    public static <T> void removeAll(List<? super T> removeFrom, List<? extends T> c2) {
        for (T i : c2) {
            if (removeFrom.contains(i)) {
                removeFrom.remove(i);
            }
        }
    }


    public static <T> boolean containsAll(List<? super T> c1, List<? extends T> c2) {
        for (T i : c2) {
            if (c1.contains(i) == false) {
                return false;
            }
        }
        return true;
    }


    public static <T> boolean containsAny(List<? super T> c1, List<? extends T> c2) {
        for (T i : c2) {
            if (c1.contains(i) == true) {
                return true;
            }
        }
        return false;
    }


    public static List<Number> range(List <Number> list, Integer  min, Integer max) {
        List<Number> ar = new ArrayList<>();
        for (Number i : list){
            if ((Integer)i >= min && (Integer)i <= max){
                ar.add(i);
            }
        }
        return ar;
    }


    public static <T, E extends T> List<T> range(List <T> list, E min, E max, Comparator comparator) {
        List<T> ar = new ArrayList<>();
        for (int i = 0; i < list.size(); i++){
            if (comparator.compare(list.get(i), min) > 0 && comparator.compare(list.get(i), max) < 0){
                ar.add(list.get(i));
            }
        }
        return ar;
    }

}
