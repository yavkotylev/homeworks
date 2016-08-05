package homeworks.HW4.countMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Yaroslav on 05.08.16.
 */
public class CountMapImp<T> implements CountMap<T> {

    public static void main(String[] args) {
        CountMap<Integer> map = new CountMapImp<>();

        map.add(10);
        map.add(10);
        map.add(5);
        map.add(6);
        map.add(5);
        map.add(10);

        System.out.println(map.getCount(5)); // 2
        System.out.println(map.getCount(6)); // 1
        System.out.println(map.getCount(10)); // 3
    }
    final private Map<T, Integer> container;

    public CountMapImp(){
        container = new HashMap<T, Integer>();
    }

    @Override
    public void add(T o) {
        if (container.containsKey(o) == true) {
            container.replace(o, container.get(o) + 1);
        } else{
            container.put(o, 1);
        }
    }

    @Override
    public int getCount(T o) {
        if (container.containsKey(o) == true){
            return container.get(o);
        }
        return -1;
    }

    @Override
    public int remove(T o) {
        if (container.keySet().contains(o) == true){
            int res = container.get(o);

            if (res == 1){
                container.remove(o);
            } else{
                container.replace(o, container.get(o) - 1);
            }

            return res;
        }
        return -1;
    }

    @Override
    public int size() {
        return container.size();
    }

    @Override
    public void addAll(CountMap source) {

        for (Object i : source.toMap().keySet()) {

            if (container.containsKey(i) == true) {
                container.replace((T) i, container.get(i) + source.getCount(i));
            } else{
                container.put((T)i, source.getCount(i));
            }

        }
    }

    @Override
    public Map toMap() {
        Map<T, Integer> newHashMap = new HashMap<T, Integer>();
        for (T i : container.keySet()){
            newHashMap.put(i, container.get(i));
        }
        return newHashMap;
    }

    @Override
    public void toMap(Map destination) {
        for (T i : container.keySet()){
            destination.put(i, container.get(i));
        }
    }
}
