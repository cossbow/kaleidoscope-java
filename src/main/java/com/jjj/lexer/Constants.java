package com.jjj.lexer;

import java.util.Arrays;

final
public class Constants {
    private Constants() {
    }
    //

    static final int EOF = -1;

    public static final int TOKEN_EOF = -1;        // 文件结束标识符
    public static final int TOKEN_DEF = -2;        // 关键字def
    public static final int TOKEN_EXTERN = -3;     // 关键字extern
    public static final int TOKEN_IDENTIFIER = -4; // 名字
    public static final int TOKEN_NUMBER = -5;      // 数值


    //

    private static final short[] BINARY_OPERATOR_PRECEDENCE = new short[256];

    static {
        Arrays.fill(BINARY_OPERATOR_PRECEDENCE, (short) -1);
        BINARY_OPERATOR_PRECEDENCE['*'] = 40;
        BINARY_OPERATOR_PRECEDENCE['-'] = 20;
        BINARY_OPERATOR_PRECEDENCE['+'] = 20;
        BINARY_OPERATOR_PRECEDENCE['<'] = 10;
    }

    public static short binaryOperatorPrecedence(char operator) {
        if (operator < 256) {
            return BINARY_OPERATOR_PRECEDENCE[operator];
        }
        return -1;
    }

}
