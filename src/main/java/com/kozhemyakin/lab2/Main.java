package com.kozhemyakin.lab2;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите математическое выражение: ");
        String expressionText = scanner.nextLine();

        WordAnalyze lexemAnalyzer = new WordAnalyze();
        List<Word> words = lexemAnalyzer.analyze(expressionText);
        ExpressionSolver expressionSolver = new ExpressionSolver(words);
        double result = expressionSolver.solve();
        System.out.println("Резульат: " + result);
    }
}
