package com.jjj.lexer.ast;

import com.jjj.lexer.Bridge;
import com.jjj.lexer.ExprContext;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;

public class MainFunctionAST {
    private final FunctionAST main;
    private final Map<String, FunctionAST> functions;

    public MainFunctionAST(FunctionAST main, Map<String, FunctionAST> functions) {
        this.main = Objects.requireNonNull(main);
        this.functions = Map.copyOf(functions);
    }


    public BigDecimal exec(Map<String, ?> variables, Map<String, Bridge> bridges) {
        var root = new ExprContext(variables, bridges, functions);
        return main.getBody().exec(root);
    }

}
