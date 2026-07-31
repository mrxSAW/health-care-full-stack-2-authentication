package com.example.HealthCareApp.Controller;

import com.example.HealthCareApp.DTO.PageResponseDTO;
import com.example.HealthCareApp.DTO.DossierMedical.DossierMedicalGetDTO;
import com.example.HealthCareApp.DTO.DossierMedical.DossierMedicalPostDTO;
import com.example.HealthCareApp.DTO.DossierMedical.DossierMedicalUpdateDTO;
import com.example.HealthCareApp.Service.DossierMedicalService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dossiers")
@SecurityRequirement(name = "bearerAuth")
public class DossierMedicalController {

    private final DossierMedicalService service;

    public DossierMedicalController(DossierMedicalService service) {
        this.service = service;
    }

    @PostMapping
    public DossierMedicalGetDTO create(@Valid @RequestBody DossierMedicalPostDTO dto) {
        return service.save(dto);
    }

    @GetMapping("/me")
    public DossierMedicalGetDTO getMyDossier(Authentication authentication) {
        String email = authentication.getName();
        return service.getMyDossier(email);
    }




    @GetMapping
    public PageResponseDTO<DossierMedicalGetDTO> list(@RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "5") int size,
                                                      @RequestParam(defaultValue = "dateCreation") String sort,
                                                      @RequestParam(defaultValue = "desc") String direction) {

        Pageable pageable = createPageable(page, size, sort, direction);
        return service.getAll(pageable);
    }


    @GetMapping("/patient/{patientId}")
    public DossierMedicalGetDTO getByPatientId(@PathVariable int patientId) {
        return service.getByPatientId(patientId);
    }


    @GetMapping("/{id}")
    public DossierMedicalGetDTO get(@PathVariable int id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    public DossierMedicalGetDTO update(@PathVariable int id, @Valid @RequestBody DossierMedicalUpdateDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        service.delete(id);
    }

    private Pageable createPageable(int page, int size, String sort, String direction) {
        Sort.Direction sortDirection = direction.equalsIgnoreCase("desc")
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        return PageRequest.of(page, size, Sort.by(sortDirection, sort));
    }
}