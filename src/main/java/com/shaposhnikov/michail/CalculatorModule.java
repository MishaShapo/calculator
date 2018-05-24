package com.shaposhnikov.michail;

import com.google.inject.AbstractModule;
import com.shaposhnikov.michail.core.CalculatorResource;
import com.shaposhnikov.michail.core.FourFuncCalculatorResource;
import com.shaposhnikov.michail.core.ScientificCalculatorResource;
import com.shaposhnikov.michail.health.FourFuncStreamHealthCheck;
import com.shaposhnikov.michail.health.StreamHealthCheck;

public class CalculatorModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(CalculatorResource.class).to(ScientificCalculatorResource.class);
        bind(StreamHealthCheck.class).to(FourFuncStreamHealthCheck.class);
    }
}
