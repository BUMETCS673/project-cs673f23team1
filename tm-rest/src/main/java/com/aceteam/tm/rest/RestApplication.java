package com.aceteam.tm.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @description: RestApplicaion enabling stateless, client-server communication over HTTP, using standard methods to perform operations on server-exposed resources
 * @author: haoran
 */

@SpringBootApplication
public class RestApplication {
    public static void main(String[] args){
        SpringApplication.run(RestApplication.class, args);
    }
}
