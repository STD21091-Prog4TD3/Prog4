package com.prog.TD.repository;

import com.prog.TD.modele.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // Existing methods...

    @Query(nativeQuery = true, value = "SELECT * FROM employees " +
            "WHERE (:firstName IS NULL OR first_name LIKE %:firstName%) " +
            "AND (:lastName IS NULL OR last_name LIKE %:lastName%) " +
            "AND (:gender IS NULL OR gender = :gender) " +
            "AND (:jobFunction IS NULL OR job_function LIKE %:jobFunction%) " +
            "AND (:hiringDateFrom IS NULL OR hiring_date >= :hiringDateFrom) " +
            "AND (:hiringDateTo IS NULL OR hiring_date <= :hiringDateTo) " +
            "AND (:departureDateFrom IS NULL OR departure_date >= :departureDateFrom) " +
            "AND (:departureDateTo IS NULL OR departure_date <= :departureDateTo) " +
            "ORDER BY CASE WHEN :sortField = 'firstName' AND :sortOrder = 'asc' THEN first_name END ASC, " +
            "CASE WHEN :sortField = 'firstName' AND :sortOrder = 'desc' THEN first_name END DESC, " +
            "CASE WHEN :sortField = 'lastName' AND :sortOrder = 'asc' THEN last_name END ASC, " +
            "CASE WHEN :sortField = 'lastName' AND :sortOrder = 'desc' THEN last_name END DESC, " +
            "CASE WHEN :sortField = 'gender' AND :sortOrder = 'asc' THEN gender END ASC, " +
            "CASE WHEN :sortField = 'gender' AND :sortOrder = 'desc' THEN gender END DESC, " +
            "CASE WHEN :sortField = 'jobFunction' AND :sortOrder = 'asc' THEN job_function END ASC, " +
            "CASE WHEN :sortField = 'jobFunction' AND :sortOrder = 'desc' THEN job_function END DESC, " +
            "CASE WHEN :sortField = 'hiringDate' AND :sortOrder = 'asc' THEN hiring_date END ASC, " +
            "CASE WHEN :sortField = 'hiringDate' AND :sortOrder = 'desc' THEN hiring_date END DESC, " +
            "CASE WHEN :sortField = 'departureDate' AND :sortOrder = 'asc' THEN departure_date END ASC, " +
            "CASE WHEN :sortField = 'departureDate' AND :sortOrder = 'desc' THEN departure_date END DESC")
    List<Employee> getFilteredAndSortedEmployees(@Param("firstName") String firstName,
                                                 @Param("lastName") String lastName,
                                                 @Param("gender") String gender,
                                                 @Param("jobFunction") String jobFunction,
                                                 @Param("hiringDateFrom") LocalDate hiringDateFrom,
                                                 @Param("hiringDateTo") LocalDate hiringDateTo,
                                                 @Param("departureDateFrom") LocalDate departureDateFrom,
                                                 @Param("departureDateTo") LocalDate departureDateTo,
                                                 @Param("sortField") String sortField,
                                                 @Param("sortOrder") String sortOrder);
}

