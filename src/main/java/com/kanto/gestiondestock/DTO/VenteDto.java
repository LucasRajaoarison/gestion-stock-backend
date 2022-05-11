package com.kanto.gestiondestock.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kanto.gestiondestock.entity.LigneVente;
import com.kanto.gestiondestock.entity.Vente;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VenteDto {

    private Long id;

    @NotNull(message="est obligatoire")
    @Size(min=1, message="est obligatoire")
    private String code;

    @NotNull(message="est obligatoire")
    private Instant dateVente;

    private String commentaire;

    @NotNull(message="est obligatoire")
    private List<LigneVenteDto> ligneVentes;

    public static VenteDto fromEntity(Vente vente) {

        if (vente == null) {
            return null;
        }

        return VenteDto.builder()
                .id(vente.getId())
                .code(vente.getCode())
                .dateVente(vente.getDateVente())
                .commentaire(vente.getCommentaire())
                .build() ;

    }

    public static Vente toEntity(VenteDto venteDto) {

        if (venteDto == null) {
            return null;
        }

        Vente vente = new Vente();
        vente.setId(venteDto.getId());
        vente.setCode(venteDto.getCode());
        vente.setDateVente(venteDto.getDateVente());
        vente.setCommentaire(venteDto.getCommentaire());

        return vente;

    }

}
