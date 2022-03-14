package com.jjj.lexer;

import java.io.Closeable;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.util.Objects;

public class ReaderCharSource implements CharSource, Closeable {
    private final Reader reader;

    private boolean closed;

    public ReaderCharSource(Reader reader) {
        this.reader = Objects.requireNonNull(reader);
    }

    @Override
    public int next() {
        if (closed) {
            throw new IllegalStateException();
        }
        try {
            return reader.read();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public void close() throws IOException {
        if (closed) return;
        try {
            reader.close();
        } finally {
            closed = true;
        }
    }

}
