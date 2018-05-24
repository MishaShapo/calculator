package com.shaposhnikov.michail.core;

import com.codahale.metrics.annotation.Timed;
import com.shaposhnikov.michail.api.ArithmeticResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BinaryOperator;
import java.util.function.IntBinaryOperator;

@Path("/calculator")
@Produces(MediaType.APPLICATION_JSON)
public class FourFuncCalculatorResource extends CalculatorResource {

    private final AtomicLong counter;
    private static final Logger LOGGER = LoggerFactory.getLogger(FourFuncCalculatorResource.class);

    public FourFuncCalculatorResource() {
        this.counter = new AtomicLong();
    }

    @Path("/add")
    @GET
    @Timed
    public ArithmeticResult add(@QueryParam("val") List<String> values) {
        return performOperation((a,b)->(Integer)a + (Integer)b,values);
    }

    @Path("/sub")
    @GET
    @Timed
    public ArithmeticResult sub(@QueryParam("val") List<String> values){
        return performOperation((a,b)->(Integer)a - (Integer)b,values);
    }

    @Path("/mul")
    @GET
    @Timed
    public ArithmeticResult mul(@QueryParam("val") List<String> values){
        return performOperation((a,b)->(Integer)a * (Integer)b,values);
    }

    @Path("/div")
    @GET
    @Timed
    public ArithmeticResult div(@QueryParam("val") List<String>values){
        return performOperation((a,b)->(Integer)a/(Integer)b,values);
    }

    public ArithmeticResult performOperation(BinaryOperator operator, List<String> numbers){

        if(numbers.isEmpty()){
            return new ArithmeticResult(counter.incrementAndGet(),0);
        }

        final int identity = Integer.parseInt(numbers.remove(0));
        final int value = numbers.stream().mapToInt(Integer::parseInt).reduce(identity, (IntBinaryOperator) operator);
        return new ArithmeticResult<>(counter.incrementAndGet(), value);
    }


}