package annotations;

import java.lang.annotation.*;

/**
 * Annotation to repeat {@link Feeding feeding times}.
 *
 * @author Created by tom on 02.10.2016.
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(ElementType.TYPE)
@Inherited
public @interface Feedings {
    Feeding[] value();
}