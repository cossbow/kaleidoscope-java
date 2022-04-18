package com.jjj.lexer;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

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
    public void testArithmetic() {
        var parser = new ExprParser(new StringCharSource("b+pow(a+4*6.3/(b+1.3),b)"));
        var call = parser.parseCallable();
        var ctx = new ExprContext(Map.of(
                "a", 4.8,
                "b", 2.6
        ), Map.of("pow", new PowFunction()), Map.of());
        System.out.println(call.exec(ctx).doubleValue());
    }

    @Test
    public void testArithmetic2() throws IOException {
        try (var is = ParserTest.class.getResourceAsStream("/arithmetic-expr.txt")) {
            assert is != null;
            try (var r = new InputStreamReader(is)) {
                var parser = new ExprParser(new ReaderCharSource(r));
                var call = parser.parseMain();
                var rand = ThreadLocalRandom.current();
                var a = new BigDecimal(rand.nextDouble());
                var b = new BigDecimal(rand.nextDouble());
                var vars = new HashMap<String, Number>();
                vars.put("a", a);
                vars.put("b", b);
                var bridges = Map.<String, Bridge>of("pow", new PowFunction());
                var result = call.exec(vars, bridges);
                Assert.assertEquals(a.add(b), result);
            }
        }
    }

    static class PowFunction implements Bridge {
        @Override
        public BigDecimal apply(List<BigDecimal> args) {
            if (args.size() != 2) {
                throw new IllegalStateException("pow(a,b) require to arguments");
            }
            return new BigDecimal(Math.pow(args.get(0).doubleValue(), args.get(1).doubleValue()));
        }
    }

}
