package com.shaposhnikov.michail;

import com.google.inject.AbstractModule;
import com.shaposhnikov.michail.core.CalculatorService;
import com.shaposhnikov.michail.core.ScientificCalculatorService;
import com.shaposhnikov.michail.health.FourFuncStreamHealthCheck;
import com.shaposhnikov.michail.health.StreamHealthCheck;

public class CalculatorModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(CalculatorService.class).to(ScientificCalculatorService.class);
        bind(StreamHealthCheck.class).to(FourFuncStreamHealthCheck.class);
    }
}
