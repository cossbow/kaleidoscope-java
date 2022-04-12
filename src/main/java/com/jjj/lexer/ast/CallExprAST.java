package com.jjj.lexer.ast;

import com.jjj.lexer.ExprContext;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 函数调用表达式
 */
public class CallExprAST extends ExprAST {
    private String callee;
    private List<ExprAST> argExprList;

    public CallExprAST(String callee, List<ExprAST> args) {
        this.callee = callee;
        this.argExprList = args;
    }

    @Override
    public BigDecimal exec(ExprContext ctx) {
        var arguments = new ArrayList<BigDecimal>(argExprList.size());
        for (var ast : argExprList) {
            BigDecimal exec = ast.exec(ctx);
            arguments.add(exec);
        }
        return ctx.getFunction(callee).apply(arguments);
    }

}
