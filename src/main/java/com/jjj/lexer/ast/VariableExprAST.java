package com.jjj.lexer.ast;

import com.jjj.lexer.ExprContext;

import java.math.BigDecimal;

/**
 * 变量表达式
 */
public class VariableExprAST extends ExprAST {
    private final String name;

    public VariableExprAST(String name) {
        this.name = name;
    }

    @Override
    public BigDecimal exec(ExprContext ctx) {
        return ctx.getNumber(name);
    }
}
