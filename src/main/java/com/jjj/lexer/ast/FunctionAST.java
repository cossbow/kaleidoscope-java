package com.jjj.lexer.ast;

/**
 * 函数
 */
public class FunctionAST {
    private final PrototypeAST proto;
    private final ExprAST body;

    public FunctionAST(PrototypeAST proto, ExprAST body) {
        this.proto = proto;
        this.body = body;
    }
}
