package com.exilum.demo.model;

import lombok.Getter;

@Getter
public class UpdateUserRequest {

    private String uid;
    private String email;
    private String password;
}
