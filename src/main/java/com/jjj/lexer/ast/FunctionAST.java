package com.jjj.lexer.ast;

import java.util.Objects;

/**
 * 函数
 */
public class FunctionAST {
    protected final PrototypeAST proto;
    protected final ExprAST body;

    public FunctionAST(PrototypeAST proto, ExprAST body) {
        this.proto = Objects.requireNonNull(proto);
        this.body = Objects.requireNonNull(body);
    }

    public PrototypeAST getProto() {
        return proto;
    }

    public ExprAST getBody() {
        return body;
    }
}
