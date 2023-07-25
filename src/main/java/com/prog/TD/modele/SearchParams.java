package com.prog.TD.modele;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SearchParams {
        private String firstName;
        private String lastName;
        private String gender;
        private String jobFunction;
        private LocalDate hiringDateFrom;
        private LocalDate hiringDateTo;
        private LocalDate departureDateFrom;
        private LocalDate departureDateTo;

}
