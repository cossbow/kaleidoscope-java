package com.jjj.lexer;

import java.math.BigDecimal;

public interface Callable {

    BigDecimal exec(ExprContext ctx);

}
