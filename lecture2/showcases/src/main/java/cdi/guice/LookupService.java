package cdi.guice;

import basics.Animal;

import java.util.Collection;

/**
 * @author Created by ergouser on 05.10.16.
 */
public interface LookupService {
    Collection<Animal> findAnimalByName(final String name);
}
