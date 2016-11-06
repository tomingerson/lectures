package mocktests;

import basics.Animal;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Collections;

/**
 * This action uses the {@link LookupService} directly with compile-time-dependency.
 *
 * @author Created by tom on 02.10.2016.
 */
public class SomeAction {
    private final LookupService lookupService;
    private String animalName;

    public SomeAction(LookupService lookupService) {
        this.lookupService = lookupService;
    }

    public String getAnimalName() {
        return animalName;
    }

    public void setAnimalName(String animalName) {
        this.animalName = animalName;
    }

    public Collection<Animal> findAnimals() {

        if (StringUtils.isBlank(animalName))
            return Collections.emptyList();

        return this.lookupService.findAnimalByName(animalName);
    }

    /**
     * this version of the SomeAction is not testable, because i cannot mock the {@link LookupService}.
     * There are 2 ways to change this: 1. use a factory, 2. let the caller decide
     * Usually you would chose a factory, but since we are talking about CDI later this lecture, we
     * chose the second way.
     */
    private static class SomeActionHighlyCoupled {
        private final LookupService lookupService = new LookupService();
        private String animalName;

        public String getAnimalName() {
            return animalName;
        }

        public void setAnimalName(String animalName) {
            this.animalName = animalName;
        }

        public Collection<Animal> findAnimals() {

            if (StringUtils.isBlank(animalName))
                return Collections.emptyList();

            return this.lookupService.findAnimalByName(animalName);
        }
    }
}
