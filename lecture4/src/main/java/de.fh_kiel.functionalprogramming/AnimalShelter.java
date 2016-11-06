package de.fh_kiel.functionalprogramming;

import java.util.ArrayList;
import java.util.List;

/**
 * The AnimalShelter takes care of animals. One of its purposes is feeding.
 *
 * @author Created by tom on 15.10.2016.
 */
public class AnimalShelter{

    private List<Animal> animals = new ArrayList<>();

    /**
     * contstucts a new Shelter with some animals
     */
    public AnimalShelter(List<Animal> animals) {
        this.animals.addAll(animals);
    }

    /**
     * feed all the animals
     */
    private void feedAnimals() {
        for (Animal animal : animals) {
            animal.feed();
        }
    }
}
