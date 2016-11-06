package cdi.spring;

import basics.Animal;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Created by ergouser on 05.10.16.
 */
@org.springframework.stereotype.Service
@org.springframework.context.annotation.Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AnimalService {

    private final LookupService lookupService;

    @javax.inject.Inject
    // or @org.springframework.beans.factory.annotation.Autowired
    public AnimalService(LookupService lookupService) {
        this.lookupService = lookupService;
    }

    /**
     * takes care of all the known animals with the given name.
     *
     * @param name the animal-identifier
     * @return all animals taken care of
     */
    public Collection<Animal> takeCareOfAnimal(final String name) {
        Collection<Animal> result = new ArrayList<>();

        Collection<Animal> animals = lookupService.findAnimalByName(name);

        for (Animal animal : animals) {
            result.add(animal);
            System.out.println("Taking care of " + animal.getClass().getSimpleName() + " " + animal
                    .getName());
        }

        return result;
    }
}
