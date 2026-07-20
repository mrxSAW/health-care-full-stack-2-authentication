package com.example.HealthCareApp.Repository;

import com.example.HealthCareApp.DTO.DossierMedical.DossierMedicalGetDTO;
import com.example.HealthCareApp.Entity.DossierMedical;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DossierMedicalRepository extends JpaRepository<DossierMedical,Integer> {

    @EntityGraph(attributePaths = {"patient"})
    Optional<DossierMedical> findByPatientUserEmail(String email);

    @Override
    @EntityGraph(attributePaths = {"patient"})
    Page<DossierMedical> findAll(Pageable pageable);

    @Override
    @EntityGraph(attributePaths = {"patient"})
    Optional<DossierMedical> findById(Integer id);

}
