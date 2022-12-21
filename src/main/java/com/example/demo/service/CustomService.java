package com.example.demo.service;

public class CustomService {

    public void serviceMethod() {
        System.out.println("Service method was called");
    }

    public void serviceMethod(String message) {
        System.out.println("Service method was called with params, "+ message);
    }
}