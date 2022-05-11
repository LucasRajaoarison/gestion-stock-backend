package com.kanto.gestiondestock.services;

import com.kanto.gestiondestock.entity.Role;

public interface AccountService {
    public Role saveRole(Role role)  ;
    public void addRoleToUser(String mail, String roleName);
}
