package com.kanto.gestiondestock.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Categorie extends AbstractEntity {

    @Column(length = 76)
    private String code;

    @Column(length = 76)
    private String designation;

    @OneToMany(mappedBy = "categorie")
    private List<Article> articles = new ArrayList<>();


   /* public void add(Article article) {

        if (article != null) {
            if (articles == null) {
                articles = new ArrayList<>();
            }
            articles.add(article) ;
            article.setCategorie(this);
        }

    }*/

}
