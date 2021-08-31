package org.example.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TodoVariables {

    public static String baseUrl;

    @Value("${base.url}")
    public void setBaseUrl(String url) {
        baseUrl = url;
    }
}
