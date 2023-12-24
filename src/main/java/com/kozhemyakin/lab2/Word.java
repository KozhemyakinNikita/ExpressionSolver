package com.kozhemyakin.lab2;

/**
 * Word implementation
 * @author n.s.kozhemyakin
 */
public class Word {
    WordType type;
    String value;
    /**
     * Constructs a new Word object with the specified type and value.
     *
     * @param type  The type of the token.
     * @param value The value associated with the token.
     */
    public Word(WordType type, String value) {
        this.type = type;
        this.value = value;
    }
    /**
     * Constructs a new Word object with the specified type and character value.
     * This constructor is useful when dealing with single-character tokens.
     *
     * @param type  The type of the token.
     * @param value The character value associated with the token.
     */
    public Word(WordType type, Character value) {
        this.type = type;
        this.value = value.toString();
    }
    /**
     * Gets the type of the token.
     *
     * @return The type of the token (e.g., NUMBER, PLUS, MINUS).
     */
    public WordType getType() {
        return type;
    }

    /**
     * Gets the value associated with the token.
     *
     * @return The string value associated with the token.
     */
    public String getValue() {
        return value;
    }
}
