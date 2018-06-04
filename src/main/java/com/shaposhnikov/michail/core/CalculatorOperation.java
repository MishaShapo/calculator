package com.shaposhnikov.michail.core;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public enum CalculatorOperation {
    ADD     ( new CalculatorOperationBuilder()
                    .withVerificationFunction(nums -> nums.size() >= 2)
                    .withVerificationError("Add requires 2 or more operands please")
                    .withOperationFunction( (a,b) -> a + b )
                    .withOperationError("Sorry, could not add those numbers")),
    SUB     ( new CalculatorOperationBuilder()
                    .withVerificationFunction(nums -> nums.size() >= 2)
                    .withVerificationError("Sub requires 2 or more operands please")
                    .withOperationFunction( (a,b) -> a - b )
                    .withOperationError("Sorry, could not subtract those numbers")),
    MUL     ( new CalculatorOperationBuilder()
                    .withVerificationFunction(nums -> nums.size() >= 2)
                    .withVerificationError("Mul requires 2 or more operands please")
                    .withOperationFunction( (a,b) -> a * b )
                    .withOperationError("Sorry, could not multiply those numbers")),
    DIV     ( new CalculatorOperationBuilder()
                    .withVerificationFunction(nums -> nums.size() >= 2 && nums.stream().noneMatch(a -> a == 0))
                    .withVerificationError("Div requires 2 or more non-zero operands please")
                    .withOperationFunction( (a,b) -> a / b )
                    .withOperationError("Sorry, could not divide those numbers")),
    POW     ( new CalculatorOperationBuilder()
                    .withVerificationFunction(nums -> nums.size() == 2)
                    .withVerificationError("Pow requires exactly 2 operands (base and exponent) please")
                    .withOperationFunction( (base,exp) -> Math.pow(base,exp) )
                    .withOperationError("Sorry, could not take the power those numbers")),
    SQRT    ( new CalculatorOperationBuilder()
                    .withVerificationFunction(nums -> nums.size() == 1 && nums.stream().noneMatch(a -> a < 0))
                    .withVerificationError("Sqrt requires exactly 1 non-zero operand please")
                    .withOperationFunction( a -> Math.sqrt(a) )
                    .withOperationError("Sorry, could not square root that number"));



    private final Function<List<Double>,Boolean> verificationFunction;
    private final Function<List<Double>,Double> operationFunction;
    private String verificationError;
    private String operationError;


    private CalculatorOperation(CalculatorOperationBuilder calculatorOperationBuilder){
        this.verificationFunction = calculatorOperationBuilder.verificationFunction;
        this.operationFunction = calculatorOperationBuilder.operationFunction;
        this.verificationError = calculatorOperationBuilder.verificationError;
        this.operationError = calculatorOperationBuilder.operationError;
    }

    public boolean verify(List<Double> nums){
        return this.verificationFunction.apply(nums);
    }

    public double operate(List<Double> nums){
        double result = this.operationFunction.apply(nums);
        if (Double.isNaN(result)){
            throw new UnsupportedOperationException();
        }
        else
            return result;
    }

    public String getVerificationError(){
        return verificationError;
    }

    public String getOperationError() {
        return operationError;
    }


    private static final class CalculatorOperationBuilder {
        private Function<List<Double>,Boolean> verificationFunction;
        private Function<List<Double>,Double> operationFunction;
        private String verificationError;
        private String operationError;
        private String key;

        public CalculatorOperationBuilder(){

        }

        public CalculatorOperationBuilder withVerificationFunction(Function<List<Double>,Boolean> verificationFunction){
            this.verificationFunction = verificationFunction;
            return this;
        }

        public CalculatorOperationBuilder withOperationFunction(BinaryOperator<Double> operationFunction){
            this.operationFunction = (nums)->nums.stream().reduce(nums.remove(0),operationFunction);
            return this;
        }

        public CalculatorOperationBuilder withOperationFunction(UnaryOperator<Double> operationFunction){
            this.operationFunction = (nums)->operationFunction.apply(nums.get(0));
            return this;
        }

        public CalculatorOperationBuilder withVerificationError(String err){
            this.verificationError = err;
            return this;
        }

        public CalculatorOperationBuilder withOperationError(String err){
            this.operationError = err;
            return this;
        }

        public CalculatorOperationBuilder withKey(String key){
            this.key = key;
            return this;
        }
    }
}
