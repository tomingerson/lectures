package annotations;

import java.lang.annotation.*;

/**
 * Annotation describing how to feed an Animal.
 *
 * @author Created by tom on 02.10.2016.
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(ElementType.TYPE)
@Inherited
@Repeatable(Feedings.class)
public @interface Feeding {

    String feedingTime() default "";

    FoodType foodType() default FoodType.MEAT;
}


