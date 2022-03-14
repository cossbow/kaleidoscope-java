package com.jjj.lexer.ast;

/**
 * 变量表达式
 */
public class VariableExprAST extends ExprAST{
    private String name;

    public VariableExprAST(String name) {
        this.name = name;
    }
}
