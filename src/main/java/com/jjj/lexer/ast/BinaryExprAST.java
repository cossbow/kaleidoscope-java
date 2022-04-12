package com.jjj.lexer.ast;

import com.jjj.lexer.ExprContext;

import java.math.BigDecimal;
import java.math.RoundingMode;

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

    @Override
    public BigDecimal exec(ExprContext ctx) {
        var lv = lhs.exec(ctx);
        var rv = rhs.exec(ctx);
        switch (op) {
            case '+':
                return lv.add(rv);
            case '-':
                return lv .subtract(rv);
            case '*':
                return lv .multiply(rv);
            case '/':
                return lv .divide(rv, 32, RoundingMode.CEILING);
            default:
                throw new UnsupportedOperationException();
        }
    }

}
