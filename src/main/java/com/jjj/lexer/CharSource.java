package com.jjj.lexer;

public interface CharSource {

    /**
     * Reads a single character.
     *
     * <p> Subclasses that intend to support efficient single-character input
     * should override this method.
     *
     * @return The character read, as an integer in the range 0 to 65535
     * ({@code 0x00-0xffff}), or -1 if the end of the stream has
     * been reached
     */
    int next();

}
