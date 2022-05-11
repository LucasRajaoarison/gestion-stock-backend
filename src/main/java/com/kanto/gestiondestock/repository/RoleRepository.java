package com.kanto.gestiondestock.repository;

import com.kanto.gestiondestock.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    public Role findByRoleName(String roleName) ;
}
