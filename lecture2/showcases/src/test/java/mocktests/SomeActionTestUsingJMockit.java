package mocktests;

import basics.Animal;
import basics.Dog;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.Verifications;
import mockit.integration.junit4.JMockit;
import org.hamcrest.CoreMatchers;
import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collection;
import java.util.Collections;

/**
 * @author Created by tom on 02.10.2016.
 */
@RunWith(JMockit.class)
public class SomeActionTestUsingJMockit {

    @Tested
    private SomeAction someAction;

    @Injectable
    private LookupService mockLookupService;

    @Test
    public void findAnimals() throws Exception {

        // prepare
        someAction.setAnimalName("molly");
        new Expectations() {{
            mockLookupService.findAnimalByName(anyString);
            result = Collections.singletonList(new Dog("alfie"));
            maxTimes = 1;
        }};

        // test
        Collection<Animal> animals = someAction.findAnimals();

        //verify
        new Verifications() {{
            mockLookupService.findAnimalByName(withEqual("molly"));
        }};
        Assert.assertThat("no animals found", animals, Is.is(CoreMatchers.notNullValue()));
        Assert.assertThat("wrong animal", animals.iterator().next().getName(), Is.is(CoreMatchers.equalTo("alfie")));

    }
}
