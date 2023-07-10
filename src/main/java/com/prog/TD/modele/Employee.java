package com.prog.TD.modele;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Table(name = "employees")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String firstName;

    private String employeeNumber;

    @Lob
    private byte[] photo;

    @Transient
    private MultipartFile photoFile;

    // Constructeur, getters et setters

    // Ajoutez une méthode pour vérifier si la photo est présente
    public boolean hasPhoto() {
        return photo != null && photo.length > 0;
    }

}
