package com.jjj.lexer.ast;

import java.util.List;

/**
 * 函数接口
 */
public class PrototypeAST {
    private final String name;
    private final List<String> args;

    public PrototypeAST(String name, List<String> args) {
        this.name = name;
        this.args = args;
    }

    public String name() {
        return name;
    }
}
