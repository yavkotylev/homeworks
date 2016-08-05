package homeworks.HW4.countMap;

import java.util.Map;

/**
 * Created by Yaroslav on 05.08.16.
 */
public interface CountMap <T> {

    // добавляет элемент в этот контейнер.
    void add(T o);

    //Возвращает количество добавлений данного элемента
    int getCount(T o);

    //Удаляет элемент и контейнера и возвращает количество его добавлений(до удаления)
    int remove(T o);

    //количество разных элементов
    int size();

    //Добавить все элементы из source в текущий контейнер, при совпадении ключей,     суммировать значения
    void addAll(CountMap source);

    //Вернуть java.util.Map. ключ - добавленный элемент, значение - количество его добавлений
    Map toMap();

    //Тот же самый контракт как и toMap(), только всю информацию записать в destination
    void toMap(Map destination);
}
