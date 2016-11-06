package cdi.guice;

import basics.Animal;
import basics.Dog;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.hamcrest.CoreMatchers;
import org.hamcrest.core.Is;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.Collections;

/**
 * @author Created by ergouser on 05.10.16.
 */
public class AnimalServiceTest {

    private Injector injector;
    private AnimalService animalService;

    @Before
    public void setUp() throws Exception {
        this.injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(LookupService.class).to(MockLookupService.class);
            }
        });
        this.animalService = injector.getInstance(AnimalService.class);
    }

    @After
    public void tearDown() throws Exception {
        this.animalService = null;
        this.injector = null;
    }

    @Test
    public void takeCareOfAnimal() throws Exception {
        Collection<Animal> animals = this.animalService.takeCareOfAnimal("molly");
        Assert.assertThat("there should be one Dog", animals, Is.is(CoreMatchers.notNullValue()));
        Assert.assertThat("wrong animal", animals.iterator().next().getName(), Is.is(CoreMatchers.equalTo("alfie")));
    }

    private static class MockLookupService implements LookupService {

        @Override
        public Collection<Animal> findAnimalByName(String name) {
            return Collections.singletonList(new Dog("alfie"));
        }
    }
}