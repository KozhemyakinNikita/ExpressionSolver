package com.kozhemyakin.lab2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * ExpressionSolver implementation
 * This class provides functionality to solve mathematical expressions
 * represented as a list of Word objects.
 * Supports basic arithmetic operations, variables, and mathematical functions.
 *
 * @author n.s.kozhemyakin
 */
public class ExpressionSolver {
    private WordBuff wordBuff;
    private Map<String, Double> variables;
    /**
     * Constructs a new ExpressionSolver object.
     *
     * @param words The list of Word objects representing the mathematical expression.
     */
    public ExpressionSolver(List<Word> words) {
        wordBuff = new WordBuff(words);
        variables = new HashMap<>();
    }
    /**
     * Initiates the expression solving process and returns the result.
     *
     * @return The result of the mathematical expression.
     */
    public double solve() {
        return expressions();
    }
    /**
     * Parses and evaluates the mathematical expression.
     *
     * @return The result of the mathematical expression.
     */
    private double expressions() {
        if (wordBuff.next().getType() == WordType.EOF) {
            return 0;
        } else {
            wordBuff.back();
            return plusMinus();
        }
    }
    /**
     * Handles addition and subtraction operations in the expression.
     *
     * @return The result of addition and subtraction operations.
     */
    private double plusMinus() {
        double value = multDiv();
        while (true) {
            Word word = wordBuff.next();
            switch (word.getType()) {
                case PLUS:
                    value += multDiv();
                    break;
                case MINUS:
                    value -= multDiv();
                    break;
                case EOF:
                case R_BRACKET:
                    wordBuff.back();
                    return value;
                default:
                    throw new ErrorHandler("Unexpected token: " + word.getValue()
                            + " at position: " + wordBuff.getPosition());
            }
        }
    }
    /**
     * Handles multiplication and division operations in the expression.
     *
     * @return The result of multiplication and division operations.
     */
    private double multDiv() {
        double value = factor();
        while (true) {
            Word word = wordBuff.next();
            switch (word.getType()) {
                case MULTIPLICATION:
                    value *= factor();
                    break;
                case DIVISION:
                    double divisor = factor();
                    if (divisor == 0) {
                        throw new ArithmeticException("Division by zero");
                    }
                    value /= divisor;
                    break;
                case EOF:
                case R_BRACKET:
                case PLUS:
                case MINUS:
                    wordBuff.back();
                    return value;
                default:
                    throw new ErrorHandler("Unexpected token: " + word.getValue() + " at position: " + wordBuff.getPosition());
            }
        }
    }


    private double factor() {
        Word word = wordBuff.next();
        switch (word.getType()) {
            case MINUS:
                return -factor();
            case NUMBER:
                return Double.parseDouble(word.getValue());
            case VARIABLE:
                String varName = word.getValue();
                if (!variables.containsKey(varName)) {
                    double varValue = requestVariableValue(varName);
                    variables.put(varName, varValue);
                }
                return variables.get(varName);
            case L_BRACKET:
                double value = plusMinus();
                word = wordBuff.next();
                if (word.getType() != WordType.R_BRACKET) {
                    throw new ErrorHandler("Unexpected token: " + word.getValue()
                            + " at position: " + wordBuff.getPosition());
                }
                return value;
            case FUNCTION:
                String functionName = word.getValue();
                double argument = factor();
                return executeFunction(functionName, argument);
            default:
                throw new ErrorHandler("Unexpected token: " + word.getValue()
                        + " at position: " + wordBuff.getPosition());
        }
    }

    private double requestVariableValue(String varName) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите значение для переменной " + varName + " : ");
        while (!scanner.hasNextDouble()) {
            System.out.println("Пожалуйста, введите допустимое значение.");
            scanner.next();
        }
        return scanner.nextDouble();
    }


    private double executeFunction(String functionName, double argument) {
        switch (functionName) {
            case "sin":
                return Math.sin(argument);
            case "cos":
                return Math.cos(argument);
            case "tan":
                return Math.tan(argument);
            case "cot":
                return 1.0 / Math.tan(argument);
            default:
                throw new RuntimeException("Неподдерживаемая функция: " + functionName);
        }
    }
}
