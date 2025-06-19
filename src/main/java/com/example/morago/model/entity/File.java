package com.example.morago.model.entity;

import com.example.morago.model.entity.base.Auditable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "files")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class File extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String originalTitle;

    @Column(nullable = false)
    private String path;

    @Column(nullable = false)
    private Long size;

    @Column(nullable = false)
    private String type;
}
