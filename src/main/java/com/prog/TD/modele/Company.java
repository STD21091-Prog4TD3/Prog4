package com.prog.TD.modele;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@Data
@NoArgsConstructor
public class Company {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;
    private String slogan;
    private String address;
    private String email;
    @Column(unique = true)
    private String phoneNumber;
    private String nif;
    private String stat;
    private String rcs;
}
