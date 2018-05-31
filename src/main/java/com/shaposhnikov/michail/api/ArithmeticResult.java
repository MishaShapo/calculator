package com.shaposhnikov.michail.api;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    public static ArithmeticResult createResult(long id, double result) {
        return new ArithmeticResult(id,result);
    }

//    public static ArithmeticResult generateErroredResult(long id, List<String> errors) {
//        return new ArithmeticResult(id,errors);
//    }

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