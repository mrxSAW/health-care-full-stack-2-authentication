package com.example.HealthCareApp.Controller;

import com.example.HealthCareApp.DTO.PageResponseDTO;
import com.example.HealthCareApp.DTO.RendezVous.RendezVousGetDTO;
import com.example.HealthCareApp.DTO.RendezVous.RendezVousPostDTO;
import com.example.HealthCareApp.DTO.RendezVous.RendezVousUpdateDTO;
import com.example.HealthCareApp.Entity.Role;
import com.example.HealthCareApp.Repository.UserRepository;
import com.example.HealthCareApp.Service.RendezVousService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rendezvous")
@SecurityRequirement(name = "bearerAuth")
public class RendezVousController {

    private final RendezVousService service;
    private final UserRepository userRepository;

    public RendezVousController(RendezVousService service, UserRepository userRepository) {
        this.service = service;
        this.userRepository = userRepository;
    }

    @PostMapping
    public RendezVousGetDTO create(@Valid @RequestBody RendezVousPostDTO dto) {
        return service.save(dto);
    }

    @GetMapping("/me")
    public PageResponseDTO<RendezVousGetDTO> myRendezVous(Authentication authentication,
                                                          @RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "5") int size) {

        String email = authentication.getName();
        Pageable pageable = createPageable(page, size, "dateRendezVous", "asc");
        var user = userRepository.findByEmail(email);

        if (user.getRole() == Role.PATIENT) {
            return service.findMyPatientRendezVous(email, pageable);
        }

        if (user.getRole() == Role.MEDECIN) {
            return service.findMyMedcinRendezVous(email, pageable);
        }

        return service.getAll(pageable);
    }

    @GetMapping
    public PageResponseDTO<RendezVousGetDTO> list(@RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "5") int size,
                                                  @RequestParam(defaultValue = "dateRendezVous") String sort,
                                                  @RequestParam(defaultValue = "asc") String direction) {

        Pageable pageable = createPageable(page, size, sort, direction);
        return service.getAll(pageable);
    }

    @GetMapping("/search")
    public Page<RendezVousGetDTO> searchByStatut(@RequestParam String statut,
                                                 @RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "5") int size,
                                                 @RequestParam(defaultValue = "dateRendezVous") String sort,
                                                 @RequestParam(defaultValue = "asc") String direction) {

        Pageable pageable = createPageable(page, size, sort, direction);
        return service.searchByStatut(statut, pageable);
    }

    @PutMapping("/{id}")
    public RendezVousGetDTO update(@PathVariable int id, @Valid @RequestBody RendezVousUpdateDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        service.delete(id);
    }

    @GetMapping("/patient/{id}")
    public PageResponseDTO<RendezVousGetDTO> byPatient(@PathVariable int id,
                                                       @RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "5") int size) {

        Pageable pageable = createPageable(page, size, "dateRendezVous", "asc");
        return service.findByPatient(id, pageable);
    }

    @GetMapping("/medcin/{id}")
    public PageResponseDTO<RendezVousGetDTO> byMedcin(@PathVariable int id,
                                                      @RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "5") int size) {

        Pageable pageable = createPageable(page, size, "dateRendezVous", "asc");
        return service.findByMedcin(id, pageable);
    }

    private Pageable createPageable(int page, int size, String sort, String direction) {
        Sort.Direction sortDirection = direction.equalsIgnoreCase("desc")
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        return PageRequest.of(page, size, Sort.by(sortDirection, sort));
    }
}