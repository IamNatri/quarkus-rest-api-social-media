package com.github.IamNatri.quarkussocial.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateUserRequest {

    @NotBlank(message = "Username is mandatory")
    private String userName;
    @NotNull(message = "Age is mandatory")
    private Integer age;


}

