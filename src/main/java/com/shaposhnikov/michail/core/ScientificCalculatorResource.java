package com.shaposhnikov.michail.core;

import com.codahale.metrics.annotation.Timed;
import com.shaposhnikov.michail.api.ArithmeticResult;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.IntBinaryOperator;

@Path("/calculator")
@Produces (MediaType.APPLICATION_JSON)
public class ScientificCalculatorResource extends CalculatorResource {

    public ScientificCalculatorResource(){
        super();
    }

    @Path("/sqrt")
    @GET
    @Timed
    public ArithmeticResult div(@QueryParam ("val") String value){
        return new ArithmeticResult<>(counter.incrementAndGet(), Math.sqrt(Double.parseDouble(value)));
    }


    @Path("/pow")
    @GET
    @Timed
    public ArithmeticResult pow(@QueryParam ("base") String base, @QueryParam("exp") String exp){
        return new ArithmeticResult(counter.getAndIncrement(),Math.pow(Double.parseDouble(base), Double.parseDouble(exp)));
    }



    @Override
    public ArithmeticResult performOperation(BinaryOperator operator, List<String> numbers) {
        if(numbers.isEmpty()){
            return new ArithmeticResult(counter.incrementAndGet(),0);
        }

        final int identity = Integer.parseInt(numbers.remove(0));
        final int value = numbers.stream().mapToInt(Integer::parseInt).reduce(identity, (IntBinaryOperator) operator);
        return new ArithmeticResult(counter.incrementAndGet(), value);
    }
}
