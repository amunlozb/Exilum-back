package com.exilum.demo.model;

import lombok.Getter;

@Getter
public class CreateUserRequest {
    private String email;
    private String password;
}