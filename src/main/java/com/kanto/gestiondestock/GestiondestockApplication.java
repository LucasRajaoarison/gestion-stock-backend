package com.kanto.gestiondestock;

import com.kanto.gestiondestock.DTO.UtilisateurDto;
import com.kanto.gestiondestock.entity.Role;
import com.kanto.gestiondestock.entity.Utilisateur;
import com.kanto.gestiondestock.repository.UtilisateurRepository;
import com.kanto.gestiondestock.services.AccountService;
import com.kanto.gestiondestock.services.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableWebMvc
@SpringBootApplication
public class GestiondestockApplication implements CommandLineRunner {

	@Autowired
	private AccountService accountService;

	@Autowired
	private UtilisateurService utilisateurService;

	@Autowired
	private UtilisateurRepository utilisateurRepository;


	public static void main(String[] args) {
		SpringApplication.run(GestiondestockApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		/*utilisateurService.save(new UtilisateurDto((long) 1, "admin", "1234")) ;
		utilisateurService.save(new UtilisateurDto((long) 2, "user", "1234")) ;
		accountService.saveRole(new Role(1,"ADMIN" ));
		accountService.saveRole(new Role(2,"USER" ));
		accountService.addRoleToUser("admin", "ADMIN");
		accountService.addRoleToUser("admin", "USER");
		accountService.addRoleToUser("user", "USER");*/
	}
}
