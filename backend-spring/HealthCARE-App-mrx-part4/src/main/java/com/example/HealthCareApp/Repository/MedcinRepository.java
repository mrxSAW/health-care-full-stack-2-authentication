package com.example.HealthCareApp.Repository;

import com.example.HealthCareApp.Entity.Medcin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MedcinRepository extends JpaRepository<Medcin,Integer> {

    Page<Medcin> findBySpecialiteContainingIgnoreCase(String specialite, Pageable pageable);

    Optional<Medcin> findByUserEmail(String email);
}
