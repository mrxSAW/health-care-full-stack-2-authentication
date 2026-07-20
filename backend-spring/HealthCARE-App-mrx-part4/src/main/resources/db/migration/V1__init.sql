CREATE TABLE patient (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         nom VARCHAR(255),
                         prenom VARCHAR(255),
                         email VARCHAR(255),
                         telephone VARCHAR(255),
                         date_naissance DATE
                        );

CREATE TABLE dossier_medical (
                                 id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                 diagnostic VARCHAR(255),
                                 observation VARCHAR(255),
                                 date_creation DATE,
                                 patient_id BIGINT UNIQUE,
                                 CONSTRAINT fk_dossier_patient
                                     FOREIGN KEY (patient_id) REFERENCES patient(id)
);

CREATE TABLE medcin (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        nom VARCHAR(255),
                        specialite VARCHAR(255),
                        email VARCHAR(255),
                        telephone VARCHAR(255)
);

CREATE TABLE rendez_vous (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             date_rendez_vous DATETIME,
                             statut VARCHAR(255),
                             patient_id BIGINT,
                             medcin_id BIGINT,
                             FOREIGN KEY (patient_id) REFERENCES patient(id),
                             FOREIGN KEY (medcin_id) REFERENCES medcin(id)
);