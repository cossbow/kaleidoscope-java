package com.jjj.lexer.ast;

import java.util.List;
import java.util.Objects;

/**
 * 函数接口
 */
public class PrototypeAST {
    private final String name;
    private final List<String> parameters;

    public PrototypeAST(String name, List<String> parameters) {
        this.name = Objects.requireNonNull(name);
        for (String parameter : Objects.requireNonNull(parameters)) {
            Objects.requireNonNull(parameter);
        }
        this.parameters = List.copyOf(parameters);
    }

    public String getName() {
        return name;
    }

    public List<String> getParameters() {
        return parameters;
    }
}
