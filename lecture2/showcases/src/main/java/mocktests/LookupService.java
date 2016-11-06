package mocktests;

import basics.Animal;
import basics.Cat;

import java.util.Collection;
import java.util.Collections;

/**
 * Service to find Animals
 *
 * @author Created by tom on 02.10.2016.
 */
public class LookupService {

    public Collection<Animal> findAnimalByName(String name) {
        return Collections.singletonList(new Cat("poppy"));
    }
}
