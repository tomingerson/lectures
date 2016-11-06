package reflection;

import java.time.LocalDateTime;

/**
 * @author Created by tom on 05.10.2016.
 */
public class Classes {

    void getClasses() {
        Class<?> clazz1 = java.time.LocalDateTime.class;
        Class<?> clazz2 = java.time.LocalDateTime.now().getClass();
        try {
            Class<?> clazz3 = Class.forName("java.time.LocalDateTime");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Class<?> clazzInt = int.class;
    }

    void createObject() {
        try {
            LocalDateTime sometime = LocalDateTime.class.newInstance();
        } catch (InstantiationException e) {
            // if the class is abstract or an interface
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // if the class does not have a constructor without parameters (nullary constructor)
            e.printStackTrace();
        }
    }
}
