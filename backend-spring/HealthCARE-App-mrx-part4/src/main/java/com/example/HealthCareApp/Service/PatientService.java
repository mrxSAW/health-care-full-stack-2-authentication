package com.example.HealthCareApp.Service;

import com.example.HealthCareApp.DTO.Patient.PatientGetDTO;
import com.example.HealthCareApp.DTO.Patient.PatientPostDTO;
import com.example.HealthCareApp.DTO.Patient.PatientUpdateDTO;
import com.example.HealthCareApp.Entity.Patient;
import com.example.HealthCareApp.Entity.Role;
import com.example.HealthCareApp.Entity.User;
import com.example.HealthCareApp.Mapper.PatientMapper;
import com.example.HealthCareApp.Repository.PatientRepository;
import com.example.HealthCareApp.Repository.UserRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import com.example.HealthCareApp.DTO.PageResponseDTO;

@Service
public class PatientService {

    private final PatientRepository repo;
    private final UserRepository userRepository;
    private final PatientMapper mapper;

    public PatientService(PatientRepository repo,UserRepository userRepository ,PatientMapper mapper) {
        this.repo = repo;
        this.userRepository=userRepository;
        this.mapper = mapper;
    }

    @CacheEvict(value = {"patients","patient"}, allEntries = true)
    public PatientGetDTO save(PatientPostDTO dto) {
        Patient patient = mapper.toEntity(dto);
        if(dto.getUserId()!=null){
            User user=userRepository.findById(dto.getUserId()).orElseThrow(()->new RuntimeException("user introvable"));

            if(user.getRole() != Role.PATIENT) {
                throw new RuntimeException("ce user n'est pas un patient");
            }
          patient.setUser(user);
        }



        Patient savedPatient = repo.save(patient);
        return mapper.toGetDTO(savedPatient);
    }


    public PatientGetDTO getMyProfile(String email) {
        Patient patient = repo.findByUserEmail(email)
                .orElseThrow(() -> new RuntimeException("Patient introuvable pour cet utilisateur"));

        return mapper.toGetDTO(patient);
    }

    @Cacheable(value = "patients", key = "#pageable.pageNumber + '-' + #pageable.pageSize + '-' + #pageable.sort")
    public PageResponseDTO<PatientGetDTO> getAll(Pageable pageable) {
        Page<PatientGetDTO> page = repo.findAll(pageable)
                .map(patient -> mapper.toGetDTO(patient));

        return PageResponseDTO.from(page);
    }

    public Page<PatientGetDTO> searchByNom(String nom, Pageable pageable) {
        return repo.findByNomContainingIgnoreCase(nom, pageable).map(patient -> mapper.toGetDTO(patient));
    }


   @Cacheable(value = "patient",key="#id")
    public PatientGetDTO getById(int id) {
        Patient patient = repo.findById(id).orElse(null);

        if (patient == null) {
            return null;
        }

        return mapper.toGetDTO(patient);
    }

    @CacheEvict(value = {"patients", "patient"}, allEntries = true)
    public PatientGetDTO update(int id, PatientUpdateDTO dto) {
        Patient patient = repo.findById(id).orElse(null);

        if (patient == null) {
            return null;
        }

        mapper.updatePatientFromDTO(dto, patient);

        Patient updatedPatient = repo.save(patient);
        return mapper.toGetDTO(updatedPatient);
    }

    @CacheEvict(value = {"patients", "patient"}, allEntries = true)
    public void delete(int id) {
        repo.deleteById(id);
    }



 public Page<PatientGetDTO> searchByBerthDay(LocalDate date , Pageable pageable){

        return repo.findPatientByDateNaissance(date,pageable);
 }


    public Page<PatientGetDTO> searchByTelephone(String tel, Pageable pageable) {

        return repo.findPatientByTelephone(tel,pageable);
    }
}
