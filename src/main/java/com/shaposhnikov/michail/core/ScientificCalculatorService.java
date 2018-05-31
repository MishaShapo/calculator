package com.shaposhnikov.michail.core;

import com.google.common.collect.ImmutableMap;
import com.shaposhnikov.michail.api.ArithmeticResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class ScientificCalculatorService extends CalculatorService{

    private static final Logger LOGGER = LoggerFactory.getLogger(CalculatorResource.class);
    //Map the operation to a function that returns true if the given number of arguments is valid for the operation
    static final Map<String, Function<Integer, String>> numArgumentsValidators = ImmutableMap.<String,Function<Integer,String>>builder()
            .put("add", (numArgs)->numArgs >= 2 ? "" : "add requires 2 or more arguments. " + numArgs + " given.")
            .put("sub", (numArgs)->numArgs >= 2 ? "" : "sub requires 2 or more arguments. " + numArgs + " given.")
            .put("mul", (numArgs)->numArgs >= 2 ? "" : "mul requires 2 or more arguments. " + numArgs + " given.")
            .put("div", (numArgs)->numArgs >= 2 ? "" : "div requires 2 or more arguments. " + numArgs + " given.")
            .put("pow", (numArgs)->numArgs == 2 ? "" : "power requires exactly 2 arguments. " + numArgs + " given.")
            .put("sqrt",(numArgs)->numArgs == 1 ? "" : "square root requires exactly 1 arguments. " + numArgs + " given.").build();

    //Map the operation to a function that validates the input is valid for the given operation
    static final Map<String, Function< List<Double>,String>> inputValidators = ImmutableMap.<String, Function< List<Double>,String>>builder()
            .put("add", (nums)->"")
            .put("sub", (nums)->"")
            .put("mul", (nums)->"")
            .put("div", (nums)->nums.stream().anyMatch((a)-> a == 0) ? "Please don't divide by zero" : "")
            .put("pow", (nums)->"")
            .put("sqrt", (nums)->nums.stream().anyMatch((a)-> a < 0) ? "Please don't take the square root of a negative number": "").build();

    //Map the operation to a function that computes the final result
    static final Map<String, Function< List<Double>, Double>> operationExecution = ImmutableMap.<String, Function< List<Double>, Double>>builder()
            .put("add", (nums)->nums.stream().reduce(nums.remove(0),(a,b)->a+b))
            .put("sub", (nums)->nums.stream().reduce(nums.remove(0),(a,b)->a-b))
            .put("mul", (nums)->nums.stream().reduce(nums.remove(0),(a,b)->a*b))
            .put("div", (nums)->nums.stream().reduce(nums.remove(0),(a,b)->a/b))
            .put("pow", (nums)->Math.pow(nums.remove(0),nums.remove(0)))
            .put("sqrt", (nums)->Math.sqrt(nums.remove(0))).build();

    @Override
    public ArithmeticResult applyOperationTo(long id, List<Double> numbers, String operationParam) {
        String numArgsMessage = numArgumentsValidators.get(operationParam).apply(numbers.size());
        String validInputsMessage = inputValidators.get(operationParam).apply(numbers);

        //if both error messages are empty, we are good to invoke the operation
        if(numArgsMessage == "" && validInputsMessage == ""){
            double result = operationExecution.get(operationParam).apply(numbers);
            return ArithmeticResult.createResult(id,result);
        } else {
            return ArithmeticResult.generateErroredResult(id, numArgsMessage+ "\n" + validInputsMessage);
        }

    }
}
