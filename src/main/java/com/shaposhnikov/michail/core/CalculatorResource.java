package com.shaposhnikov.michail.core;

import com.shaposhnikov.michail.api.ArithmeticResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BinaryOperator;


public abstract class CalculatorResource {

    protected final AtomicLong counter;
    private static final Logger LOGGER = LoggerFactory.getLogger(CalculatorResource.class);

    public CalculatorResource() {
        this.counter = new AtomicLong();
    }


    public abstract ArithmeticResult performOperation(BinaryOperator operator, List<String> numbers);

}