package com.kanto.gestiondestock.DTO;

import com.kanto.gestiondestock.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto {

    private long id;

    private String roleName;


    public static RoleDto fromEntity(Role role) {

        if (role == null) {
            return null;
        }

        return RoleDto.builder()
                .id(role.getId())
                .roleName(role.getRoleName())
                .build() ;

    }

    public static Role toEntity(RoleDto  roleDto) {

        if (roleDto == null) {
            return null;
        }

        Role role =  new Role();
        role.setId(roleDto.getId());
        role.setRoleName(roleDto.getRoleName());

        return role;

    }

}
