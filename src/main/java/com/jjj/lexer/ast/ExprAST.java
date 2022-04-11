package com.jjj.lexer.ast;

import com.jjj.lexer.Callable;

import java.math.BigDecimal;

abstract
public class ExprAST implements Callable {
    @Override
    public BigDecimal exec() {
        throw new UnsupportedOperationException();
    }
}
