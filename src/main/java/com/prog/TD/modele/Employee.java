package com.prog.TD.modele;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
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
    private String lastName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthdate;
    private String gender;
    private String address;
    private String personalEmail;
    private String workEmail;
    private String cinNumber;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate cinDeliveryDate;
    private String cinDeliveryPlace;
    private String jobFunction;
    private int numberOfChildren;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate hiringDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate departureDate;
    private String socioProfessionalCategory;
    private String cnapsNumber;
    @Column(unique = true)
    private String phoneNumber;

    @Lob
    @Type(type = "org.hibernate.type.BinaryType")
    @Column(name = "photo", columnDefinition = "bytea")
    private byte[] photo;

    @Transient
    private MultipartFile photoFile;

    public boolean hasPhoto() {
        return photo != null && photo.length > 0;
    }

}
