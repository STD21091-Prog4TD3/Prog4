package com.prog.TD.service;

import com.prog.TD.modele.Company;
import com.prog.TD.repository.CompanyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CompanyService {
    private final CompanyRepository repository;

    public Company getCompany() {
        return repository.findAll().get(0);
    }

}