package com.shaposhnikov.michail.api;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotBlank;

public class ArithmeticResult<T> {
    private long id;

    @NotBlank
    private T result;

    public ArithmeticResult() {
        // Jackson deserialization
    }

    public ArithmeticResult(long id, T result) {
        this.id = id;
        this.result = result;
    }

    @JsonProperty
    public long getId() {
        return id;
    }

    @JsonProperty
    public T getResult() {
        return result;
    }
}