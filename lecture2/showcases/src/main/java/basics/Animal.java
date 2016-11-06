package basics;

import annotations.Feeding;
import annotations.FoodType;

/**
 * Describes an animal.
 *
 * @author Created by tom on 02.10.2016.
 */
@Feeding(feedingTime = "never", foodType = FoodType.MEALWORM)
public interface Animal {
    /**
     * sound the animal makes
     */
    void talk();

    String getName();
}
