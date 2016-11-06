package annotations;

import basics.Dog;

/**
 * @author Created by tom on 02.10.2016.
 */
@Feeding(feedingTime = "noon", foodType = FoodType.MEAT)
public class PembrokeWelshCorgi extends Dog {

    public PembrokeWelshCorgi(String name) {
        super(name);
    }
}
