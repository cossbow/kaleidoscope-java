package com.jjj.lexer;

import com.jjj.lexer.ast.FunctionAST;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;

import static java.util.Collections.unmodifiableMap;

public class ExprContext {
    private final Map<String, ?> variables;
    private final Map<String, Bridge> bridges;
    private final Map<String, FunctionAST> functions;
    private final ExprContext parent;

    public ExprContext(ExprContext parent,
                       Map<String, ?> variables,
                       Map<String, Bridge> bridges,
                       Map<String, FunctionAST> functions) {
        this.parent = parent;
        this.functions = Objects.requireNonNullElse(functions, Map.of());
        Objects.requireNonNull(variables);
        Objects.requireNonNull(bridges);
        this.variables = unmodifiableMap(variables);
        this.bridges = unmodifiableMap(bridges);
    }

    /**
     * root context
     *
     * @param variables
     * @param bridges
     */
    public ExprContext(
            Map<String, ?> variables,
            Map<String, Bridge> bridges,
            Map<String, FunctionAST> functions) {
        this(null, variables, bridges, functions);
    }

    public BigDecimal getNumber(String name) {
        var value = variables.get(name);
        if (null == value) {
            value = parent.getNumber(name);
            if (null == value) {
                throw new IllegalStateException("variable " + name + " not initialize");
            }
        }
        if (value instanceof BigDecimal) {
            return (BigDecimal) value;
        }
        return new BigDecimal(value.toString());
    }

    public FunctionAST getFunction(String name) {
        var expr = functions.get(name);
        if (null == expr && null != parent) {
            expr = parent.getFunction(name);
        }
        if (null == expr) {
            return null;
        }
        assert Objects.equals(name, expr.getProto().getName());
        return expr;
    }

    public Bridge getInnerFunction(String name) {
        var fun = bridges.get(name);
        if (null == fun && null != parent) {
            fun = parent.getInnerFunction(name);
        }
        return fun;
    }

    public ExprContext subContext(Map<String, ?> variables) {
        return new ExprContext(this, unmodifiableMap(variables), Map.of(), functions);
    }
}
