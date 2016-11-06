package basics;

import annotations.Feeding;
import annotations.FoodType;

/**
 * @author Created by tom on 02.10.2016.
 */
@Feeding(feedingTime = "08:00", foodType = FoodType.MEAT)
@Feeding(feedingTime = "13:00", foodType = FoodType.FISH)
public class Cat implements Animal {
    private final String name;

    public Cat(String name) {
        this.name = name;
    }

    @Override
    public void talk() {
        System.out.println("Meow");
    }

    @Override
    public String getName() {
        return name;
    }
}
