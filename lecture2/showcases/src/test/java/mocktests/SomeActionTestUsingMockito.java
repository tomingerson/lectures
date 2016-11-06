package mocktests;

import basics.Animal;
import basics.Dog;
import org.hamcrest.CoreMatchers;
import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collection;
import java.util.Collections;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

/**
 * @author Created by tom on 02.10.2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class SomeActionTestUsingMockito {

    @InjectMocks
    private SomeAction someAction;

    @Mock
    private LookupService mockLookupService;


    @Test
    public void findAnimals() throws Exception {

        // prepare
        someAction.setAnimalName("molly");
        when(mockLookupService.findAnimalByName(anyString())).thenReturn(Collections.singletonList(new Dog("alfie")));

        // test
        Collection<Animal> animals = someAction.findAnimals();

        //verify
        verify(mockLookupService, times(1)).findAnimalByName(eq("molly"));
        Assert.assertThat("no animals found", animals, Is.is(CoreMatchers.notNullValue()));
        Assert.assertThat("wrong animal", animals.iterator().next().getName(), Is.is(CoreMatchers.equalTo("alfie")));
    }
}
