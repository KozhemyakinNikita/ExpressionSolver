package com.kozhemyakin.lab2;

import java.util.ArrayList;
import java.util.List;

/**
 * WordAnalyze implementation
 * @author n.s.kozhemyakin
 */
public class WordAnalyze {
    /**
     * Analyzes the given text expression and generates a list of Word objects.
     *
     * @param textExpression The input text expression to be analyzed.
     * @return A list of Word objects representing tokens in the expression.
     */
    public static List<Word> analyze(String textExpression) {
        ArrayList<Word> words = new ArrayList<>();
        int position = 0;
        while (position < textExpression.length()) {
            char symbol = textExpression.charAt(position);
            switch (symbol) {
                case '(':
                    words.add(new Word(WordType.L_BRACKET, symbol));
                    position++;
                    break;
                case ')':
                    words.add(new Word(WordType.R_BRACKET, symbol));
                    position++;
                    break;
                case '+':
                    words.add(new Word(WordType.PLUS, symbol));
                    position++;
                    break;
                case '-':
                    words.add(new Word(WordType.MINUS, symbol));
                    position++;
                    break;
                case '*':
                    words.add(new Word(WordType.MULTIPLICATION, symbol));
                    position++;
                    break;
                case '/':
                    words.add(new Word(WordType.DIVISION, symbol));
                    position++;
                    break;
                default:
                    position = processCharacter(textExpression, position, symbol, words);
            }
        }
        words.add(new Word(WordType.EOF, ""));
        return words;
    }
    /**
     * Processes characters that are not operators or parentheses.
     * Determines if the character represents a variable, number, or an error.
     *
     * @param textExpression The input text expression.
     * @param position       The current position in the expression.
     * @param symbol         The current character to be processed.
     * @param words          The list of Word objects.
     * @return The updated position after processing the character.
     */
    private static int processCharacter(String textExpression, int position, char symbol, List<Word> words) {
        if (Character.isLetter(symbol)) {
            return processLetters(textExpression, position, words);
        } else if (Character.isDigit(symbol) || symbol == '.') {
            return processNumbers(textExpression, position, words);
        } else {
            if (symbol != ' ') {
                throw new ErrorHandler("Unexpected character: " + symbol);
            }
            return ++position;
        }
    }
    /**
     * Processes a sequence of letters to determine if it represents a variable or a function.
     *
     * @param textExpression The input text expression.
     * @param position       The current position in the expression.
     * @param words          The list of Word objects.
     * @return The updated position after processing the letters.
     */
    private static int processLetters(String textExpression, int position, List<Word> words) {
        StringBuilder varBuilder = new StringBuilder();
        varBuilder.append(textExpression.charAt(position));
        position++;

        while (position < textExpression.length() &&
                (Character.isLetterOrDigit(textExpression.charAt(position)) || textExpression.charAt(position) == '_')) {
            varBuilder.append(textExpression.charAt(position));
            position++;
        }

        String var = varBuilder.toString();
        if (var.equals("sin") || var.equals("cos") || var.equals("tan") || var.equals("cot")) {
            words.add(new Word(WordType.FUNCTION, var));
        } else {
            words.add(new Word(WordType.VARIABLE, var));
        }

        return position;
    }
    /**
     * Processes a sequence of digits to construct a numeric token.
     *
     * @param textExpression The input text expression.
     * @param position       The current position in the expression.
     * @param words          The list of Word objects.
     * @return The updated position after processing the numbers.
     */
    private static int processNumbers(String textExpression, int position, List<Word> words) {
        StringBuilder numBuilder = new StringBuilder();
        numBuilder.append(textExpression.charAt(position));
        position++;

        boolean hasDecimal = false;
        while (position < textExpression.length()) {
            char currentChar = textExpression.charAt(position);
            if (Character.isDigit(currentChar)) {
                numBuilder.append(currentChar);
                position++;
            } else if (currentChar == '.' && !hasDecimal) {
                numBuilder.append(currentChar);
                hasDecimal = true;
                position++;
            } else {
                break;
            }
        }

        words.add(new Word(WordType.NUMBER, numBuilder.toString()));
        return position;
    }

}


