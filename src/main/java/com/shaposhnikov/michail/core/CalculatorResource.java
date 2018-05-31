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
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Path("/calculator")
@Produces (MediaType.APPLICATION_JSON)
public class CalculatorResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(CalculatorResource.class);
    private static final String SUPPORTED_OPERATIONS = "add|sub|mul|div|sqrt|pow";


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

        List<Double> numbers = verify(values);

        if(this.errors.size() == 0){
            return calculatorService.applyOperationTo(this.counter.getAndIncrement(),numbers,operationParam);
        } else {
            String err = this.errors.remove(0);
            return ArithmeticResult.generateErroredResult(this.counter.getAndIncrement(),err);
        }
    }

//    @GET
//    @Path("{default: .*}")
//    public ArithmeticResult defaultMethod() {
//        // Return a helpful error if user does not know which operations to try
//        return ArithmeticResult.generateErroredResult(this.counter.get(),"Could not find that operation. Try /" + SUPPORTED_OPERATIONS);
//    }

    private List<Double> verify(List<String> values){
        List<Double> numbers = new ArrayList<>();
        try{
            for (String value: values){
                double number = Double.parseDouble(value);
                numbers.add(number);
            }
        } catch (Exception e){
            LOGGER.error("error: " + e.getMessage());
            this.errors.add(e.getMessage());
        }
        if(numbers == null){
            this.errors.add("No values to operate on. Try adding values like /calculator/add?val=1&val=2");
        }
        return numbers;
    }


}