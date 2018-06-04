package com.shaposhnikov.michail.core;

import com.codahale.metrics.annotation.Timed;
import com.google.common.util.concurrent.ExecutionError;
import com.shaposhnikov.michail.api.ArithmeticResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Path("/calculator")
@Produces (MediaType.APPLICATION_JSON)
public class CalculatorResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(CalculatorResource.class);
    private static final List<String> SUPPORTED_OPERATIONS = Arrays.asList(new String[]{"add","sub","mul","div","sqrt","pow"});


    protected final AtomicLong counter;
    private final CalculatorService calculatorService;
    private final List<String> errors;

    @Inject
    public CalculatorResource( CalculatorService calculatorService) {
        this.counter = new AtomicLong();
        this.errors = new ArrayList<>();
        this.calculatorService = calculatorService;
    }

    @GET
    @Path ("{operation: (add|sub|mul|div|sqrt|pow)}")
    @Timed
    public ArithmeticResult operation(@QueryParam ("val") List<String> values, @PathParam ("operation") String operationParam) {



        return this.calculatorService.applyOperationTo(counter.incrementAndGet(),values,operationParam);
    }

}