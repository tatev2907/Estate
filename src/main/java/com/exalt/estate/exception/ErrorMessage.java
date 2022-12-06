package com.exalt.estate.exception;

public class ErrorMessage {
    public String message;
    public int code;

    public ErrorMessage(String message, int code) {
        this.message = message;
        this.code = code;
    }
}
