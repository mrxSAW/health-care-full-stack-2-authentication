package com.example.HealthCareApp.Service;

import com.example.HealthCareApp.Entity.DossierMedical;
import com.example.HealthCareApp.Repository.DossierMedicalRepository;
import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
@RequiredArgsConstructor
public class FileDownloadService {

    private final DossierMedicalRepository dossierMedicalRepository;

    public byte[] generateDossierMedicalPdf(int dossierId) {
        DossierMedical dossier = dossierMedicalRepository.findById(dossierId)
                .orElseThrow(() -> new RuntimeException("Dossier médical introuvable"));

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            Document document = new Document();
            PdfWriter.getInstance(document, out);

            document.open();

            document.add(new Paragraph("Dossier Médical"));
            document.add(new Paragraph("----------------------------"));
            document.add(new Paragraph("ID dossier : " + dossier.getId()));
            document.add(new Paragraph("Diagnostic : " + dossier.getDiagnostic()));
            document.add(new Paragraph("Observation : " + dossier.getObservation()));
            document.add(new Paragraph("Date création : " + dossier.getDateCreation()));

            if (dossier.getPatient() != null) {
                document.add(new Paragraph("Patient : "
                        + dossier.getPatient().getNom()
                        + " "
                        + dossier.getPatient().getPrenom()));
            }

            document.close();

            return out.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la génération du PDF");
        }
    }
}