package com.shaposhnikov.michail.core;

import com.shaposhnikov.michail.api.ArithmeticResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ScientificCalculatorService extends CalculatorService{

    private static final Logger LOGGER = LoggerFactory.getLogger(CalculatorResource.class);

    @Override
    public ArithmeticResult applyOperationTo(long id, List<String> values, String operationParam) {

        VerifiedValues numbers = VerifiedValues.create(values);

        ArithmeticResult result = ArithmeticResult.create(id,numbers,operationParam);

        return result;
    }
}
