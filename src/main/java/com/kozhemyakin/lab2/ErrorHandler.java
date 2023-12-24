package com.kozhemyakin.lab2;

public class ErrorHandler extends RuntimeException {

    public ErrorHandler(String message) {
        super(message);
    }
}