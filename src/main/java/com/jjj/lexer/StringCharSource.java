package com.jjj.lexer;

public class StringCharSource implements CharSource {
    private final String source;
    private final int len;

    private int index = 0;

    public StringCharSource(String source) {
        this.source = source;
        this.len = source.length();
    }

    @Override
    public int next() {
        return index < len ? source.charAt(index++) : -1;
    }

}
