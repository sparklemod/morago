package com.example.morago.model.entity;

import com.example.morago.model.entity.base.Auditable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
    @Column(name = "file_id")
    private Long id;

    @Column(nullable = false, length = 255)
    @NotBlank(message = "Original title cannot be empty")
    @Size(max = 255, message = "Original title must be less than 255 characters")
    private String originalTitle;

    @Column(nullable = false, length = 500)
    @NotBlank(message = "Path cannot be empty")
    @Size(max = 500, message = "Path must be less than 500 characters")
    private String path;

    @Column(nullable = false, length = 50)
    @NotBlank(message = "Type cannot be empty")
    @Size(max = 50, message = "Type must be less than 50 characters")
    private String type;
}
