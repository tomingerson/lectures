package reflection;

import annotations.PembrokeWelshCorgi;
import basics.Animal;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Created by tom on 02.10.2016.
 */
public class Reflections {
    private static final Animal dog = new PembrokeWelshCorgi("monty");

    private static Collection<Object> getAllPublicMemberValues() throws IllegalAccessException {

        Collection<Object> memberValues = new ArrayList<>();
        Class<? extends Animal> clazz = dog.getClass();
        for (Field f : clazz.getFields()) {
            memberValues.add(f.get(dog));
        }
        return memberValues;
    }

    private static Object getPrivateName() throws IllegalAccessException, NoSuchFieldException {
        Field f = dog.getClass().getField("name"); // throws NoSuchFieldException - field is private
        return f.get(dog);
    }

    private static Object getPrivateNameAnyway() throws IllegalAccessException, NoSuchFieldException {
        Class clazz = dog.getClass();
        while (clazz != null) {
            try {
                Field f = clazz.getDeclaredField("name");
                if (!Modifier.isPublic(f.getModifiers())) {
                    f.setAccessible(true);
                }
                return f.get(dog);
            } catch (NoSuchFieldException ex) {
                // may happen, we ignore it at this point and keep iterating through the class hierarchy
            }
            clazz = clazz.getSuperclass();
        }

        // if we did not find the field anywhere in class hierarchy, it may be in an interface; we do not search interfaces at this point
        throw new NoSuchFieldException();
    }

    private static Object resultOfMethodCall() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        return org.apache.commons.lang3.reflect.MethodUtils.invokeExactMethod(dog, "getName");
    }

    public static void main(String... args) {

        System.out.print("reading all member values: ");
        try {
            for (Object val : getAllPublicMemberValues()) {
                System.out.println(val.toString());
            }
        } catch (IllegalAccessException e) {
            System.out.println("Exception thrown: " + e.getClass() + " | " + e.getMessage());
        }
        System.out.println();

        System.out.print("bad things you can do - read private fields: ");
        try {
            System.out.println(getPrivateName());
        } catch (IllegalAccessException | NoSuchFieldException e) {
            System.out.println("Exception thrown: " + e.getClass() + " | " + e.getMessage());
        }
        System.out.println();


        System.out.print("really bad things you can do - read private fields iterating through class hierarchy: ");
        try {
            System.out.println(getPrivateNameAnyway());
            //System.out.println(FieldUtils.readField(dog, "name", true));
        } catch (IllegalAccessException | NoSuchFieldException e) {
            System.out.println("Exception thrown: " + e.getClass() + " | " + e.getMessage());
        }
        System.out.println();


        System.out.print("invoke a method: ");
        try {
            System.out.println(resultOfMethodCall());
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            System.out.println("Exception thrown: " + e.getClass() + " | " + e.getMessage());
        }
        System.out.println();

    }
}
