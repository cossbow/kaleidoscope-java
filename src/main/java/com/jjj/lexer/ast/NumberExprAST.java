package com.jjj.lexer.ast;

import com.jjj.lexer.ExprContext;

import java.math.BigDecimal;

/**
 * 字面值表达式
 */
public class NumberExprAST extends ExprAST {
    private final BigDecimal val;

    public NumberExprAST(BigDecimal val) {
        this.val = val;
    }

    @Override
    public BigDecimal exec(ExprContext ctx) {
        return val;
    }

}
