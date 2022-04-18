package com.jjj.lexer;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Function;

public interface Bridge extends Function<List<BigDecimal>, BigDecimal> {
}
