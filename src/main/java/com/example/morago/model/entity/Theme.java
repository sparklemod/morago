package com.example.morago.model.entity;

import com.example.morago.model.entity.base.Auditable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "themes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Theme extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "theme_id")
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    @NotBlank(message = "Name cannot be empty")
    @Size(max = 100, message = "Name must be less than 100 characters")
    private String name;

    @Column(nullable = false, length = 150)
    @NotBlank(message = "Title cannot be empty")
    @Size(max = 100, message = "Title must be less than 100 characters")
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    @Min(value = 0, message = "Price cannot be negative")
    private Integer price;

    @Column(nullable = false)
    @Min(value = 0, message = "Night price cannot be negative")
    private Integer nightPrice;

    @Column(nullable = false)
    private Boolean isPopular = false;

    @Column(nullable = false)
    private Boolean isActive = true;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "icon_id")
    private File icon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToMany(mappedBy = "themes")
    private Set<Translator> translators = new HashSet<>();

//    TODO не знаю почему но тесты ломаются при использовании этого
//    public void addTranslator(Translator translator) {
//        translators.add(translator);
//        translator.getThemes().add(this);
//    }
//
//    public void removeTranslator(Translator translator) {
//        translators.remove(translator);
//        translator.getThemes().remove(this);
//    }
}
