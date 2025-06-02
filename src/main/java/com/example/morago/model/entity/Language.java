package com.example.morago.model.entity;

import com.example.morago.model.entity.base.Auditable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "languages")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Language extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "language_id")
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    @NotBlank(message = "Name cannot be empty")
    @Size(max = 100, message = "Name must be less than 100 characters")
    private String name;

    @Column(nullable = false)
    private Boolean isActive = true;

    //    TODO не знаю почему но тесты ломаются при использовании этого
//    public void addTranslator(Translator translator) {
//        languageTranslators.add(translator);
//        translator.getLanguages().add(this);
//    }
//
//    public void removeTranslator(Translator translator) {
//        languageTranslators.remove(translator);
//        translator.getLanguages().remove(this);
//    }
}