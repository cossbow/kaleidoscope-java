package com.jjj.lexer.ast;

/**
 * 字面值表达式
 */
public class NumberExprAST extends ExprAST {
    private double val;

    public NumberExprAST(double val) {
        this.val = val;
    }
}
