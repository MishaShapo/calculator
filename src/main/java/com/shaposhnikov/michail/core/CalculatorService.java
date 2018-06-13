package com.shaposhnikov.michail.core;

import com.shaposhnikov.michail.api.ArithmeticResult;

import java.util.List;

public abstract class CalculatorService {
    public abstract ArithmeticResult applyOperationTo(long id, List<String> numbers, String operationParam);
}
