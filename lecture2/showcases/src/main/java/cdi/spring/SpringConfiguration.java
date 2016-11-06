package cdi.spring;

/**
 * @author Created by ergouser on 05.10.16.
 */
@org.springframework.context.annotation.Configuration
public class SpringConfiguration {

    @org.springframework.context.annotation.Bean
    public LookupService createLookupService() {
        return new LookupServiceImpl();
    }

    @org.springframework.context.annotation.Bean
    public AnimalService createAnimalService() {
        return new AnimalService(createLookupService());
    }
}
