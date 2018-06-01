package com.shaposhnikov.michail.core;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import com.shaposhnikov.michail.api.ArithmeticResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.DoubleBinaryOperator;
import java.util.function.Function;

public class ScientificCalculatorService extends CalculatorService{

    private static final Logger LOGGER = LoggerFactory.getLogger(CalculatorResource.class);
    //Map the operation to a function that returns null if the given number of arguments is valid for the operation or the appropriate error message
    static final Map<String, Function<Integer, String>> numArgumentsValidators = ImmutableMap.<String,Function<Integer,String>>builder()
            .put("add", (numArgs)->numArgs >= 2 ? null : "add requires 2 or more arguments. " + numArgs + " given.")
            .put("sub", (numArgs)->numArgs >= 2 ? null : "sub requires 2 or more arguments. " + numArgs + " given.")
            .put("mul", (numArgs)->numArgs >= 2 ? null : "mul requires 2 or more arguments. " + numArgs + " given.")
            .put("div", (numArgs)->numArgs >= 2 ? null : "div requires 2 or more arguments. " + numArgs + " given.")
            .put("pow", (numArgs)->numArgs == 2 ? null : "power requires exactly 2 arguments. " + numArgs + " given.")
            .put("sqrt",(numArgs)->numArgs == 1 ? null : "square root requires exactly 1 arguments. " + numArgs + " given.").build();

    //Map the operation to a function that validates the input is valid for the given operation
    static final Map<String, Function< List<Double>,String>> inputValidators = ImmutableMap.<String, Function< List<Double>,String>>builder()
            .put("add", (nums)->null)
            .put("sub", (nums)->null)
            .put("mul", (nums)->null)
            .put("div", (nums)->nums.stream().anyMatch((a)-> a == 0) ? "Please don't divide by zero" : null)
            .put("pow", (nums)->null)
            .put("sqrt", (nums)->nums.stream().anyMatch((a)-> a < 0) ? "Please don't take the square root of a negative number": null).build();

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
        if(numArgsMessage == null && validInputsMessage == null ){
            double result = operationExecution.get(operationParam).apply(numbers);
            if (Double.isNaN(result)){
                return ArithmeticResult.generateErroredResult(id, "This operation is invalid and resulted in NaN.");
            }
            return ArithmeticResult.createResult(id,result);
        } else {
            //Create a list of valid errors ie. errors that are non-null
            ImmutableList errors = ImmutableList.copyOf(Iterables.filter(Arrays.asList(numArgsMessage, validInputsMessage), Predicates.notNull()));
            return ArithmeticResult.generateErroredResult(id, errors);
        }

    }
}
