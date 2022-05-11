package com.kanto.gestiondestock.services.Impl;

import com.kanto.gestiondestock.DTO.CategorieDto;
import com.kanto.gestiondestock.exception.EntityNotFoundException;
import com.kanto.gestiondestock.services.CategorieService;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategorieServiceImplTest {

    @Autowired
    private CategorieService categorieService;

    @Test
    public  void shouldSaveCategoryWithSuccess() {

       CategorieDto expectedCategory = CategorieDto.builder()
                .code("Cat test")
                .designation("Designation test")
                .build() ;

      CategorieDto savedCategory = categorieService.save(expectedCategory);

      assertNotNull(savedCategory);
      assertNotNull(savedCategory.getId());

      Assertions.assertEquals(expectedCategory.getCode(), savedCategory.getCode());
      Assertions.assertEquals(expectedCategory.getDesignation(), savedCategory.getDesignation());

    }

    @Test
    public  void shouldUpdateCategoryWithSuccess() {

        CategorieDto expectedCategory = CategorieDto.builder()
                .code("Cat test")
                .designation("Designation test")
                .build() ;

        CategorieDto savedCategory = categorieService.save(expectedCategory);

        CategorieDto categoryToUpdate = savedCategory;
        categoryToUpdate.setCode("Cat update");

        savedCategory = categorieService.save(categoryToUpdate) ;

        assertNotNull(categoryToUpdate);
        assertNotNull(categoryToUpdate.getId());
        Assertions.assertEquals(categoryToUpdate.getCode(), savedCategory.getCode());
        Assertions.assertEquals(categoryToUpdate.getDesignation(), savedCategory.getDesignation());

    }

    @Test
    public  void shouldThrowEntityNotFoundException() {

        CategorieDto expectedCategory = CategorieDto.builder().build() ;

        assertThrows(EntityNotFoundException.class, ()-> categorieService.save(expectedCategory)) ;
    }

}