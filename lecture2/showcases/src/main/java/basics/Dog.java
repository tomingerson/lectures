package basics;

import annotations.Feeding;
import annotations.FoodType;

/**
 * @author Created by tom on 02.10.2016.
 */
@Feeding(feedingTime = "06:00", foodType = FoodType.MEAT)
@Feeding(feedingTime = "12:00", foodType = FoodType.MEAT)
@Feeding(feedingTime = "18:00", foodType = FoodType.MEAT)
public class Dog implements Animal {

    private final String name;

    public Dog(String name) {
        this.name = name;
    }

    @Override
    public void talk() {
        System.out.println("Woof");
    }

    @Override
    public String getName() {
        return name;
    }
}
