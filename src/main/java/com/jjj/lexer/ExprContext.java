package com.jjj.lexer;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.Collections.unmodifiableMap;

public class ExprContext {
    private final Map<String, Object> environment;
    private final Map<String, Function<List<BigDecimal>, BigDecimal>> functions;

    public ExprContext(Map<String, Object> environment,
                       Map<String, Function<List<BigDecimal>, BigDecimal>> functions) {
        this.environment = unmodifiableMap(environment);
        this.functions = unmodifiableMap(functions);
    }

    public BigDecimal getNumber(String name) {
        var val = environment.get(name);
        if (null == val) {
            throw new IllegalStateException("variable " + name + " not initialize");
        }
        if (val instanceof BigDecimal) {
            return (BigDecimal) val;
        }
        return new BigDecimal(val.toString());
    }

    public Function<List<BigDecimal>, BigDecimal> getFunction(String name) {
        var f = functions.get(name);
        if (f == null) {
            throw new IllegalStateException("function " + name + " not register");
        }
        return f;
    }

}
