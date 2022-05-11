package com.kanto.gestiondestock.security;

import com.kanto.gestiondestock.entity.Utilisateur;
import com.kanto.gestiondestock.entity.auth.ExtendedUser;
import com.kanto.gestiondestock.exception.EntityNotFoundException;
import com.kanto.gestiondestock.exception.ErrorCodes;
import com.kanto.gestiondestock.repository.UtilisateurRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
@Slf4j
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Utilisateur utilisateur = utilisateurRepository.findByMail(username);
        if (utilisateur == null) {
            log.error("utilisateur introuvable");
            throw new EntityNotFoundException("Utilisateur introuvable", ErrorCodes.UTILISATEUR_NOT_FOUND);
            //throw new UsernameNotFoundException(username) ;
        }

        System.out.println("Utilisateur dans la BDD " + utilisateur.getMail());

        Collection<GrantedAuthority> authorities = new ArrayList<>() ;
        utilisateur.getRoles().forEach(r-> {
            authorities.add(new SimpleGrantedAuthority(r.getRoleName())) ;
        });


        return new ExtendedUser(utilisateur.getMail(), utilisateur.getPassword(), utilisateur.getEntreprise().getId(), authorities);
    }
}
