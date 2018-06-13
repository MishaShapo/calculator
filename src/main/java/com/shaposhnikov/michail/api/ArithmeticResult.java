package com.shaposhnikov.michail.api;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.shaposhnikov.michail.core.CalculatorOperation;
import com.shaposhnikov.michail.core.VerifiedValues;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Arrays;
import java.util.List;

@JsonInclude (JsonInclude.Include.NON_NULL)
public class ArithmeticResult {
    private long id;

    @NotBlank
    private double result;

    private List<String> errors;

    public ArithmeticResult() {
        // Jackson deserialization
    }

    private ArithmeticResult(long id, double result) {
        this.id = id;
        this.result = result;
    }

    private ArithmeticResult(long id, List<String> errors){
        this.id = id;
        this.errors = errors;
    }

    public static ArithmeticResult generateErroredResult(long id, String err) {
        List<String> errors = Arrays.asList(new String[] {err});
        return new ArithmeticResult(id,errors);
    }

    public static ArithmeticResult generateErroredResult(long id, List<String> errors) {
        return new ArithmeticResult(id,errors);
    }

    public static ArithmeticResult create(long id, VerifiedValues numbers, String operation) {
        if(numbers.hasErrors()){
            return ArithmeticResult.generateErroredResult(id, numbers.getErrors());
        }

        CalculatorOperation calculatorOperation = CalculatorOperation.valueOf(operation.toUpperCase());
        List<Double> nums = numbers.getNumbers();
        boolean verified = calculatorOperation.verify(nums);
        if(verified){
            try{
                double result = calculatorOperation.operate(nums);
                return new ArithmeticResult(id,result);
            } catch (Exception e){
                return ArithmeticResult.generateErroredResult(id, calculatorOperation.getOperationError());
            }
        }
        return ArithmeticResult.generateErroredResult(id, calculatorOperation.getVerificationError());

    }

    @JsonProperty
    public long getId() {
        return id;
    }

    @JsonProperty
    public double getResult() {
        return result;
    }

    @JsonProperty
    public List<String> getErrors() {
        return errors;
    }
}