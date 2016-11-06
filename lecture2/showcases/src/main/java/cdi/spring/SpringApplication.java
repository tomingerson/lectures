package cdi.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author Created by ergouser on 05.10.16.
 */
public class SpringApplication {

    public static void main(String... args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(SpringConfiguration.class);
        ctx.refresh();

        AnimalService animalService = ctx.getBean(AnimalService.class);
        animalService.takeCareOfAnimal("molly");
    }
}
