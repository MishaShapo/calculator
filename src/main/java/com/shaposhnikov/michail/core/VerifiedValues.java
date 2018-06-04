package com.shaposhnikov.michail.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class VerifiedValues {

    private List<String> errors;
    private List<Double> nums;

    private VerifiedValues(List<Double> nums){
        this.nums = nums;
        this.errors = Collections.emptyList();
    }

    private VerifiedValues(String error){
        this.errors = new ArrayList<>(Collections.singleton(error));
    }

    public boolean hasErrors(){
        return !this.errors.isEmpty();
    }

    public List<String> getErrors(){
        return this.errors;
    }

    public String getCondenscedError(){
        return String.join("\n",this.errors);
    }

    public static VerifiedValues create(List<String> values) {
        try{
            return new VerifiedValues(values.stream().map(Double::parseDouble).collect(Collectors.toList()));
        } catch (Exception e){
            return new VerifiedValues("Error converting values to numbers. Please enter only decimal values.");
        }
    }

    public List<Double> getNumbers() {
        return nums;
    }
}
