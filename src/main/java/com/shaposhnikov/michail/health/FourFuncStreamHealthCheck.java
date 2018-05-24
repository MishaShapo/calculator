package com.shaposhnikov.michail.health;

import com.shaposhnikov.michail.core.FourFuncCalculatorResource;

import java.util.Arrays;
import java.util.List;

public class FourFuncStreamHealthCheck extends StreamHealthCheck {

    public FourFuncStreamHealthCheck() {
    }

    @Override
    protected Result check() throws Exception {
        List<String> numbers = Arrays.asList(new String[] {"1", "2"});
        final int identity = Integer.parseInt(numbers.remove(0));
        final int value = numbers.stream().mapToInt(Integer::parseInt).reduce(identity,(a,b)->a+b);

        if(value == 3) {
            return Result.unhealthy("addition with stream doesn't work");
        } else{
            return Result.healthy();
        }

    }
}
