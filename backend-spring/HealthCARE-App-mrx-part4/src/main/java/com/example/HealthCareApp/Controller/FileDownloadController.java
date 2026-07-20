package com.example.HealthCareApp.Controller;

import com.example.HealthCareApp.Service.FileDownloadService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/download")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class FileDownloadController {

    private final FileDownloadService fileDownloadService;

    @GetMapping("/dossier-medical/{id}")
    public ResponseEntity<byte[]> downloadDossierMedical(@PathVariable int id) {
        byte[] pdf = fileDownloadService.generateDossierMedicalPdf(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=dossier-medical-" + id + ".pdf")
                .contentType(MediaType.APPLICATION_PDF).body(pdf);
    }
}