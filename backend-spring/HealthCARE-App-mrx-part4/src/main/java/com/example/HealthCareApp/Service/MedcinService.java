package com.example.HealthCareApp.Service;

import com.example.HealthCareApp.DTO.Medcin.MedcinGetDTO;
import com.example.HealthCareApp.DTO.Medcin.MedcinPostDTO;
import com.example.HealthCareApp.DTO.Medcin.MedcinUpdateDTO;
import com.example.HealthCareApp.Entity.Medcin;
import com.example.HealthCareApp.Entity.Role;
import com.example.HealthCareApp.Entity.User;
import com.example.HealthCareApp.Mapper.MedcinMapper;
import com.example.HealthCareApp.Repository.MedcinRepository;
import com.example.HealthCareApp.Repository.UserRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MedcinService {

    private final MedcinRepository repo;
    private final UserRepository userRepository;
    private final MedcinMapper mapper;

    public MedcinService(MedcinRepository repo, UserRepository userRepository, MedcinMapper mapper) {
        this.repo = repo;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @CacheEvict(value = {"medcins", "medcin"}, allEntries = true)
    public MedcinGetDTO save(MedcinPostDTO dto) {
        Medcin medcin = mapper.toEntity(dto);

        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new RuntimeException("user introuvable"));

            if (user.getRole() != Role.MEDECIN) {
                throw new RuntimeException("ce user n'est pas un medcin");
            }

            medcin.setUser(user);
        }

        Medcin savedMedcin = repo.save(medcin);
        return mapper.toGetDTO(savedMedcin);
    }

    public MedcinGetDTO getMyProfile(String email) {
        Medcin medcin = repo.findByUserEmail(email)
                .orElseThrow(() -> new RuntimeException("Médecin introuvable pour cet utilisateur"));

        return mapper.toGetDTO(medcin);
    }

    @Cacheable(value = "medcins", key = "#pageable.pageNumber + '-' + #pageable.pageSize + '-' + #pageable.sort"
    )
    public Page<MedcinGetDTO> getAll(Pageable pageable) {
        return repo.findAll(pageable).map(medcin -> mapper.toGetDTO(medcin));
    }

    public Page<MedcinGetDTO> searchBySpecialite(String specialite, Pageable pageable) {
        return repo.findBySpecialiteContainingIgnoreCase(specialite, pageable)
                .map(medcin -> mapper.toGetDTO(medcin));
    }

    @Cacheable(value = "medcin", key = "#id")
    public MedcinGetDTO getById(int id) {
        Medcin medcin = repo.findById(id).orElse(null);

        if (medcin == null) {
            return null;
        }

        return mapper.toGetDTO(medcin);
    }

    @CacheEvict(value = {"medcins", "medcin"}, allEntries = true)
    public MedcinGetDTO update(int id, MedcinUpdateDTO dto) {
        Medcin medcin = repo.findById(id).orElse(null);

        if (medcin == null) {
            return null;
        }

        mapper.updateMedcinFromDTO(dto, medcin);

        Medcin updatedMedcin = repo.save(medcin);
        return mapper.toGetDTO(updatedMedcin);
    }

    @CacheEvict(value = {"medcins", "medcin"}, allEntries = true)
    public void delete(int id) {
        repo.deleteById(id);
    }
}