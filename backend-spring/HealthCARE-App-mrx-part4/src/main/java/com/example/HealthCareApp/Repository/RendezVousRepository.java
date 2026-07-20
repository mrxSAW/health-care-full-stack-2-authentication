package com.example.HealthCareApp.Repository;

import com.example.HealthCareApp.Entity.RendezVous;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RendezVousRepository extends JpaRepository<RendezVous,Integer> {

    List<RendezVous> findByPatientId(int patientId);

    List<RendezVous> findByMedcinId(int medcinId);

    @Override
    @EntityGraph(attributePaths = {"patient", "medcin"})
    Page<RendezVous> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {"patient", "medcin"})
    Page<RendezVous> findByPatientId(int patientId, Pageable pageable);

    @EntityGraph(attributePaths = {"patient","medcin"})
    Page<RendezVous> findByMedcinId(int medcinId, Pageable pageable);

    @EntityGraph(attributePaths = {"patient", "medcin"})
    Page<RendezVous> findByStatutContainingIgnoreCase(String statut, Pageable pageable);

    @EntityGraph(attributePaths = {"patient", "medcin"})
    Page<RendezVous> findByPatientUserEmail(String email, Pageable pageable);

    @EntityGraph(attributePaths = {"patient", "medcin"})
    Page<RendezVous> findByMedcinUserEmail(String email, Pageable pageable);
}
