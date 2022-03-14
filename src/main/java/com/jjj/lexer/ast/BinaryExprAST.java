package com.jjj.lexer.ast;

/**
 * 二元操作表达式
 */
public class BinaryExprAST extends ExprAST {
    private char op;
    private ExprAST lhs, rhs;

    public BinaryExprAST(char op, ExprAST lhs, ExprAST rhs) {
        this.op = op;
        this.lhs = lhs;
        this.rhs = rhs;
    }
}
