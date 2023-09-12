package com.moraes.gabriel.msusers.model.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAuthenticateRequest {

    private String email;
    private String password;
}
