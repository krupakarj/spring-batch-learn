package com.example.demo;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

@Component
public class TestComponet {

    @Value("${myurl}")
    private String myurl;

    public String getMyurl() {
        return myurl;
    }
}
