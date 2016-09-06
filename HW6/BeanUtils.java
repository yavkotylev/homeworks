package homeworks.HW6;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Yaroslav on 23.08.16.
 */
public class BeanUtils {


    public static void assign(Object to, Object from) {
        Class<?> clazzTo = to.getClass();
        Class<?> clazzFrom = from.getClass();
        Map<Class<?>, List<Method>> methodsMapGet = new HashMap<>();

        for (Method method : clazzFrom.getMethods()) {
            if (checkGet(method)) fillCompatibleClasses(method, methodsMapGet);
        }

        for (Method method : clazzTo.getMethods()) {
            if (checkSet(method) && methodsMapGet.containsKey(method.getParameterTypes()[0])) {
                for (Method i : methodsMapGet.get(method.getParameterTypes()[0])) {
                    try {
                        System.out.println("\"" + method.getName() + "\""
                                + " get value from  " + "\"" + i.getName() + "\"");
                        method.invoke(to, i.invoke(from));
                        System.out.println("");
                    } catch (Exception e) {
                        System.out.println("Something go wrong ;(");
                    }
                }
            }
        }
    }


    private static boolean checkGet(Method method) {
        if (checkPattern(method.getName(), "get") == true) {
            if (method.getParameterTypes().length == 0
                    && !method.getReturnType().equals(void.class)) return true;
        }
        return false;
    }


    private static boolean checkSet(Method method) {
        if (checkPattern(method.getName(), "set") == true) {
            if (method.getParameterTypes().length == 1
                    && method.getReturnType().equals(void.class)) {
                return true;
            }
        }
        return false;
    }


    private static boolean checkPattern(String methodName, String subString) {
        if (methodName.length() >= 3 && Character.isUpperCase(methodName.charAt(3))) {
            if ((methodName.length() > 3 && methodName.substring(0, 3).equals(subString))
                    || methodName.equals(subString)) {
                return true;
            }
        }
        return false;
    }

    private static void fillCompatibleClasses(Method method, Map<Class<?>, List<Method>> map) {
        Class<?> clazz = method.getReturnType();

        while (clazz != null) {
            if (map.containsKey(clazz)) map.get(clazz).add(method);
            else {
                List<Method> methods = new ArrayList<>();
                methods.add(method);
                map.put(clazz, methods);
            }
            clazz = clazz.getSuperclass();
        }
    }
}


