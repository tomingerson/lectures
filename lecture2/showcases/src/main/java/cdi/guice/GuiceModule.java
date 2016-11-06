package cdi.guice;


/**
 * Creates the Module for guice to instantiate all the dependencies.
 *
 * @author Created by ergouser on 05.10.16.
 */
public class GuiceModule extends com.google.inject.AbstractModule {
    @Override
    protected void configure() {
        bind(LookupService.class).to(LookupServiceImpl.class);
    }
}
