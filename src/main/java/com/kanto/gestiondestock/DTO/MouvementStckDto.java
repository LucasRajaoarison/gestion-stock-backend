package com.kanto.gestiondestock.DTO;

import com.kanto.gestiondestock.entity.Article;
import com.kanto.gestiondestock.entity.MouvementStck;
import com.kanto.gestiondestock.entity.SourceMvtStock;
import com.kanto.gestiondestock.entity.TypeMvtStck;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Instant;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MouvementStckDto {

    private Long id;

    @NotNull(message="est obligatoire")
    private Instant dateMouvement;

    @NotNull(message="est obligatoire")
    private BigDecimal quantite;

    @NotNull(message="est obligatoire")
    private TypeMvtStck typeMvtStck;

    private SourceMvtStock sourceMvtStock;

    @NotNull(message="est obligatoire")
    private ArticleDto article;


    public static MouvementStckDto fromEntity(MouvementStck mouvementStck) {

        if (mouvementStck == null){
            return null;
        }

        return MouvementStckDto.builder()
                .id(mouvementStck.getId())
                .dateMouvement(mouvementStck.getDateMouvement())
                .quantite(mouvementStck.getQuantite())
                .typeMvtStck(mouvementStck.getTypeMvtStck())
                .sourceMvtStock(mouvementStck.getSourceMvtStock())
                .article(ArticleDto.fromEntity(mouvementStck.getArticle()))
                .build();

    }

    public static MouvementStck toEntity(MouvementStckDto mouvementStckDto) {

        if (mouvementStckDto == null) {
            return null;
        }

        MouvementStck mouvementStck = new MouvementStck();
        mouvementStck.setId(mouvementStckDto.getId());
        mouvementStck.setDateMouvement(mouvementStckDto.getDateMouvement());
        mouvementStck.setTypeMvtStck(mouvementStckDto.getTypeMvtStck());
        mouvementStck.setSourceMvtStock(mouvementStckDto.getSourceMvtStock());
        mouvementStck.setQuantite(mouvementStckDto.getQuantite());
        mouvementStck.setArticle(ArticleDto.toEntity(mouvementStckDto.getArticle()));

        return mouvementStck;

    }


}
