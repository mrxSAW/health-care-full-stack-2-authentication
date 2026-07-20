package com.example.HealthCareApp.Controller;

import com.example.HealthCareApp.DTO.Medcin.MedcinGetDTO;
import com.example.HealthCareApp.DTO.Medcin.MedcinPostDTO;
import com.example.HealthCareApp.DTO.Medcin.MedcinUpdateDTO;
import com.example.HealthCareApp.Service.MedcinService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/medcins")
@SecurityRequirement(name = "bearerAuth")
public class MedcinController {

    private final MedcinService service;

    public MedcinController(MedcinService service) {
        this.service = service;
    }

    @PostMapping
    public MedcinGetDTO ajouter(@Valid @RequestBody MedcinPostDTO dto) {
        return service.save(dto);
    }

    @GetMapping("/me")
    public MedcinGetDTO getMyProfile(Authentication authentication) {
        String email = authentication.getName();
        return service.getMyProfile(email);
    }
    @GetMapping
    public Page<MedcinGetDTO> list(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "specialite") String sort, @RequestParam(defaultValue = "asc") String direction) {

        Pageable pageable = createPageable(page, size, sort, direction);
        return service.getAll(pageable);
    }

    @GetMapping("/search")
    public Page<MedcinGetDTO> searchBySpecialite(@RequestParam String specialite, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "specialite") String sort, @RequestParam(defaultValue = "asc") String direction) {

        Pageable pageable = createPageable(page, size, sort, direction);
        return service.searchBySpecialite(specialite, pageable);
    }

    @GetMapping("/{id}")
    public MedcinGetDTO get(@PathVariable int id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    public MedcinGetDTO update(@PathVariable int id, @Valid @RequestBody MedcinUpdateDTO dto) {
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
