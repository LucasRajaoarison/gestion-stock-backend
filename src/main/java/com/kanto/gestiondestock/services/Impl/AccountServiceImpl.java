package com.kanto.gestiondestock.services.Impl;

import com.kanto.gestiondestock.DTO.RoleDto;
import com.kanto.gestiondestock.DTO.UtilisateurDto;
import com.kanto.gestiondestock.entity.Role;
import com.kanto.gestiondestock.entity.Utilisateur;
import com.kanto.gestiondestock.repository.RoleRepository;
import com.kanto.gestiondestock.repository.UtilisateurRepository;
import com.kanto.gestiondestock.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;


    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String mail, String roleName) {

        UtilisateurDto utilisateur = UtilisateurDto.fromEntity(utilisateurRepository.findByMail(mail));
        Role role = roleRepository.findByRoleName(roleName);

        utilisateur.getRoles().add(RoleDto.fromEntity(role)) ;

    }
}
