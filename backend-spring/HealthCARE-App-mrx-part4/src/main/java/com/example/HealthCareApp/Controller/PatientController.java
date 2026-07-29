package com.example.HealthCareApp.Controller;

import com.example.HealthCareApp.DTO.Patient.PatientGetDTO;
import com.example.HealthCareApp.DTO.Patient.PatientPostDTO;
import com.example.HealthCareApp.DTO.Patient.PatientUpdateDTO;
import com.example.HealthCareApp.Service.PatientService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import com.example.HealthCareApp.DTO.PageResponseDTO;


@RestController
@RequestMapping("/patients")
@SecurityRequirement(name = "bearerAuth")
public class PatientController {

    private final PatientService service;

    public PatientController(PatientService service) {
        this.service = service;
    }

    @PostMapping
    public PatientGetDTO ajouter(@Valid @RequestBody PatientPostDTO dto) {
        return service.save(dto);
    }

    @GetMapping
    public PageResponseDTO<PatientGetDTO> list(@RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "5") int size,
                                               @RequestParam(defaultValue = "nom") String sort,
                                               @RequestParam(defaultValue = "asc") String direction) {

        Pageable pageable = createPageable(page, size, sort, direction);
        return service.getAll(pageable);
    }

    @GetMapping("/search")
    public Page<PatientGetDTO> searchByNom(@RequestParam String nom, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "nom") String sort, @RequestParam(defaultValue = "asc") String direction) {

        Pageable pageable = createPageable(page, size, sort, direction);
        return service.searchByNom(nom, pageable);
    }


    @GetMapping("/me")
    public PatientGetDTO getMyProfile(Authentication authentication) {
        String email = authentication.getName();
        return service.getMyProfile(email);
    }

    @GetMapping("/{id}")
    public PatientGetDTO get(@PathVariable int id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    public PatientGetDTO update(@PathVariable int id, @Valid @RequestBody PatientUpdateDTO dto) {
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


@GetMapping("/findbytel")
public Page<PatientGetDTO> searchBytelephone(@RequestParam String tel,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size ,@RequestParam(defaultValue = "nom") String sort,
                                     @RequestParam(defaultValue = "asc") String diriction)
{
    Pageable pageable=createPageable(page,size,sort,diriction);
    return service.searchByTelephone(tel,pageable);
}


@GetMapping ("/getbyldatenaissance")
    public Page<PatientGetDTO> searchbyDateNaissance(@RequestParam LocalDate date,@RequestParam(defaultValue = "0")int page,
                                                     @RequestParam(defaultValue = "5")int size,@RequestParam(defaultValue = "nom")String sort,
                                                     @RequestParam(defaultValue ="asc")String direction)
{
     Pageable pageable= createPageable( page, size ,sort,direction);

     return service.searchByBerthDay(date,pageable);


     }

}



