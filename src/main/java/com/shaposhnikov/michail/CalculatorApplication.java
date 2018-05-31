package com.shaposhnikov.michail;

import com.google.inject.Guice;
import com.google.inject.Injector;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import com.shaposhnikov.michail.core.CalculatorResource;
import com.shaposhnikov.michail.health.StreamHealthCheck;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CalculatorApplication extends Application<CalculatorConfiguration> {


    private Injector injector = Guice.createInjector(new CalculatorModule());

    public static void main(String[] args) throws Exception {
        new CalculatorApplication().run(args);
    }

    @Override
    public String getName() {
        return "calculator";
    }

    @Override
    public void initialize(Bootstrap<CalculatorConfiguration> bootstrap) {
        // nothing to do yet
    }

    @Override
    public void run(CalculatorConfiguration configuration,
                    Environment environment) {
        final CalculatorResource resource = injector.getInstance(CalculatorResource.class);
        environment.jersey().register(resource);
        environment.healthChecks().register("stream",injector.getInstance(StreamHealthCheck.class));

    }

}