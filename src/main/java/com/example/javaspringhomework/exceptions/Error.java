package com.example.javaspringhomework.exceptions;

public class Error extends Exception{
    public Error(String message) {
        System.err.println(message);
    }
}
