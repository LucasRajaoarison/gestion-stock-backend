package com.kanto.gestiondestock.DTO;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ChangerPasswordUtilisateurDto {

    private Long id;

    private String password;

    private String confirmPassword;

}
