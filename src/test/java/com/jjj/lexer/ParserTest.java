package com.jjj.lexer;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public class ParserTest {

    private Reader getResource(String resourceName) {
        return new InputStreamReader(Objects.requireNonNull(
                ParserTest.class.getResourceAsStream(resourceName)));
    }

    @Test
    public void testToken() throws IOException {
        try (var reader = getResource("/sample-tokens.txt");
             var stream = new ReaderCharSource(reader);) {
            var parser = new TokenParser(stream);
            WHILE:
            while (true) {
                var token = parser.getToken();
                switch (token) {
                    case Constants.TOKEN_DEF:
                        System.out.println("定义函数：");
                        break;
                    case Constants.TOKEN_EXTERN:
                        System.out.println("导出函数：");
                        break;
                    case Constants.TOKEN_IDENTIFIER:
                        System.out.println("identifier: " + parser.identifier());
                        break;
                    case Constants.TOKEN_NUMBER:
                        System.out.println("number: " + parser.number());
                        break;
                    case Constants.TOKEN_EOF:
                        System.out.println("结束");
                        break WHILE;
                }
            }
        }
    }


    @Test
    public void testExpr() throws IOException {
        try (var reader = getResource("/sample-expr.txt");
             var stream = new ReaderCharSource(reader)) {
            var parser = new ExprParser(stream);
            parser.testParse();
        }
    }

    @Test
    public void testArithmetic() {
        var parser = new ExprParser(new StringCharSource("b+pow(a+4*6.3/(b+1.3),b)"));
        var call = parser.parseCallable();
        var ctx = new ExprContext(Map.of(
                "a", 4.8,
                "b", 2.6
        ), Map.of("pow", new PowFunction()));
        System.out.println(call.exec(ctx).doubleValue());
    }

    static class PowFunction implements Function<List<BigDecimal>, BigDecimal> {
        @Override
        public BigDecimal apply(List<BigDecimal> args) {
            if (args.size() != 2) {
                throw new IllegalStateException("pow(a,b) require to arguments");
            }
            return new BigDecimal(Math.pow(args.get(0).doubleValue(), args.get(1).doubleValue()));
        }
    }
}
