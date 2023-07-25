package com.prog.TD.modele;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

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

    private String employeeNumber;
    private String firstName;
    private String LastName;
    private LocalDate birthdate;
    private String gender;
    private String address;
    private String personalEmail;
    private String workEmail;
    private String cinNumber;
    private LocalDate cinDeliveryDate;
    private String cinDeliveryPlace;
    private String jobFunction;
    private int numberOfChildren;
    private LocalDate hiringDate;
    private LocalDate departureDate;
    private String socioProfessionalCategory;
    private String cnapsNumber;
    private String phoneNumber;

    @Lob
    private byte[] photo;

    @Transient
    private MultipartFile photoFile;

    public boolean hasPhoto() {
        return photo != null && photo.length > 0;
    }

}
