package annotations;

import basics.Animal;

import java.lang.annotation.Annotation;

/**
 * Scans classes of Animals for annotations
 *
 * @author Created by tom on 02.10.2016.
 */
public final class AnnotationScanner {
    private static final Animal dog = new PembrokeWelshCorgi("monty");

    private static Annotation getAnnotation() {

        Class<?> clazz = dog.getClass(); // <-- clazz is a PembrokeWelshCorgi
        return clazz.getAnnotation(Deprecated.class); // <-- returns null, since the annotation is not
        // present
    }

    private static Annotation[] getAnnotations() {

        Class<?> clazz = dog.getClass();
        return clazz.getAnnotations(); // <-- returns an array with 2 elements, first an Feedings-object
        // containing 3 annotations from Dog, second a Feeding-object from the Corgi
    }

    private static Annotation[] getDeclaredAnnotations() {

        Class<?> clazz = dog.getClass();
        return clazz.getDeclaredAnnotations();
        // returns an array with 1 element, just the Feeding-object from the Corgi
    }


    public static void main(String... args) {

        System.out.print("using getAnnotation: ");
        System.out.println(getAnnotation());
        System.out.println();

        System.out.print("using getAnnotations: ");
        for (Annotation a : getAnnotations()) {
            System.out.println(a);
        }
        System.out.println();

        System.out.print("using getDeclaredAnnotations: ");
        for (Annotation a : getDeclaredAnnotations()) {
            System.out.println(a);
        }
        System.out.println();
    }
}
