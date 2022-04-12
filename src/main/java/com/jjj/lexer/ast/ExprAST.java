package com.jjj.lexer.ast;

import com.jjj.lexer.Callable;
import com.jjj.lexer.ExprContext;

import java.math.BigDecimal;

abstract
public class ExprAST implements Callable {

    @Override
    public BigDecimal exec(ExprContext ctx) {
        throw new UnsupportedOperationException();
    }
}
