package com.kanto.gestiondestock.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kanto.gestiondestock.entity.Article;
import com.kanto.gestiondestock.entity.Categorie;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategorieDto {

    private Long id;

    @NotNull(message="est obligatoire")
    @Size(min=1, message="est obligatoire")
    @NotBlank(message = "est obligatoire")
    private String code;

    @NotNull(message="est obligatoire")
    @Size(min=1, message="est obligatoire")
    @NotBlank(message = "est obligatoire")
    private String designation;

    @JsonIgnore
    private List<ArticleDto> articles;

    public static CategorieDto fromEntity(Categorie categorie) {

        if (categorie == null) {
            return null;
        }

        return CategorieDto.builder()
                .id(categorie.getId())
                .code(categorie.getCode())
                .designation(categorie.getDesignation())
                .build();
    }

    public static Categorie toEntity(CategorieDto categorieDto) {

        if (categorieDto == null) {
            return null;
        }

        Categorie categorie = new Categorie();
        categorie.setId(categorieDto.getId());
        categorie.setCode(categorieDto.getCode());
        categorie.setDesignation(categorieDto.getDesignation());

        return categorie;

    }


}
