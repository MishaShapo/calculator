package com.shaposhnikov.michail.health;

import com.codahale.metrics.health.HealthCheck;

public abstract class StreamHealthCheck extends HealthCheck {

    public StreamHealthCheck() {
    }

    @Override
    protected abstract Result check() throws Exception;
}