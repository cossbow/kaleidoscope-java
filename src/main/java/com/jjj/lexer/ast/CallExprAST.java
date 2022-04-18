package com.jjj.lexer.ast;

import com.jjj.lexer.ExprContext;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 函数调用表达式
 */
public class CallExprAST extends ExprAST {
    private final String callee;
    private final List<ExprAST> argExprList;

    public CallExprAST(String callee, List<ExprAST> args) {
        this.callee = callee;
        this.argExprList = args;
    }

    private List<BigDecimal> makeArguments(ExprContext ctx) {
        if (null == argExprList || argExprList.isEmpty()) return List.of();

        var arguments = new ArrayList<BigDecimal>(argExprList.size());
        for (var ast : argExprList) {
            BigDecimal exec = ast.exec(ctx);
            arguments.add(exec);
        }
        return arguments;
    }

    private Map<String, Object> makeArguments(List<BigDecimal> arguments, List<String> parameters) {
        if (parameters.size() != arguments.size()) {
            throw new IllegalStateException("function " + callee + " require " + parameters.size() + " arguments");
        }
        if (arguments.isEmpty()) return Map.of();

        var argumentMap = new HashMap<String, Object>(parameters.size());
        for (int i = 0; i < parameters.size(); i++) {
            var name = parameters.get(i);
            var value = arguments.get(i);
            argumentMap.put(name, value);
        }
        return argumentMap;
    }

    @Override
    public BigDecimal exec(ExprContext ctx) {
        var arguments = makeArguments(ctx);

        var fun = ctx.getFunction(callee);
        if (null != fun) {
            var argumentMap = makeArguments(arguments, fun.getProto().getParameters());
            return fun.getBody().exec(ctx.subContext(argumentMap));
        }

        var inner = ctx.getInnerFunction(callee);
        if (null == inner) {
            throw new IllegalStateException("function " + callee + " not register");
        }

        return inner.apply(arguments);
    }

}
