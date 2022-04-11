package com.jjj.lexer;

import java.math.BigDecimal;
import java.util.Objects;

import static com.jjj.lexer.Constants.*;

public class TokenParser {
    private final CharSource source;

    public TokenParser(CharSource source) {
        this.source = Objects.requireNonNull(source);
    }

    private String identifier;
    private BigDecimal number;
    private int cursor = ' ';

    private final StringBuilder strBuilder = new StringBuilder();
    private transient String _lastChar;

    private void resetBuilder() {
        strBuilder.setLength(0);
    }

    private void appendBuilder() {
        strBuilder.append((char) cursor);
    }

    String identifier() {
        return identifier;
    }

    BigDecimal number() {
        return number;
    }

    int nextChar() {
        try {
            return cursor = source.next();
        } finally {
            _lastChar = String.valueOf((char) cursor);
        }
    }


    public int getToken() {
        // 忽略空白字符
        while (Character.isWhitespace(cursor)) {
            nextChar();
        }
        // 识别字符串
        if (Character.isLetter(cursor)) {
            resetBuilder();
            appendBuilder();
            while (Character.isLetterOrDigit(nextChar())) {
                appendBuilder();
            }
            if ("def".contentEquals(strBuilder)) {
                return TOKEN_DEF;
            } else if ("extern".contentEquals(strBuilder)) {
                return TOKEN_EXTERN;
            } else {
                identifier = strBuilder.toString();
                return TOKEN_IDENTIFIER;
            }
        }
        // 识别数值
        if (Character.isDigit(cursor) || cursor == '.') {
            resetBuilder();
            do {
                appendBuilder();
                nextChar();
            } while (Character.isDigit(cursor) || cursor == '.');
            number = new BigDecimal(strBuilder.toString());
            return TOKEN_NUMBER;
        }
        // 忽略注释
        if (cursor == '#') {
            do {
                nextChar();
            } while (cursor != EOF && cursor != '\r' && cursor != '\n');
            if (cursor != EOF) {
                return getToken();
            }
        }
        // 识别文件结束
        if (cursor == EOF) {
            return TOKEN_EOF;
        }
        // 直接返回ASCII
        int prev = cursor;
        nextChar();
        return prev;
    }

    @Override
    public String toString() {
        return _lastChar;
    }
}
