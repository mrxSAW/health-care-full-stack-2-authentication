package com.example.HealthCareApp.Repository;

import com.example.HealthCareApp.DTO.Patient.PatientGetDTO;
import com.example.HealthCareApp.Entity.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient,Integer> {

    @Override
    @EntityGraph(attributePaths = {"dossierMedical"})
    Page<Patient> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {"dossierMedical"})
    Optional<Patient> findById(Integer id);

    Page<Patient> findByNomContainingIgnoreCase(String nom, Pageable pageable);

    @EntityGraph(attributePaths = {"dossierMedical"})
    Optional<Patient> findByUserEmail(String email);

    Page<PatientGetDTO> findPatientByDateNaissance(LocalDate date, Pageable pageable);


    Page<PatientGetDTO> findPatientByTelephone(String tel,Pageable pageable);
}
