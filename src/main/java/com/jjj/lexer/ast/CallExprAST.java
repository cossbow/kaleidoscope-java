package com.jjj.lexer.ast;

import java.util.List;

/**
 * 函数调用表达式
 */
public class CallExprAST extends ExprAST {
    private String callee;
    private List<ExprAST> args;

    public CallExprAST(String callee, List<ExprAST> args) {
        this.callee = callee;
        this.args = args;
    }
}
