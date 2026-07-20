ALTER TABLE patient
    ADD COLUMN user_id INT NULL;

ALTER TABLE medcin
    ADD COLUMN user_id INT NULL;

ALTER TABLE patient
    ADD CONSTRAINT uk_patient_user UNIQUE (user_id);

ALTER TABLE medcin
    ADD CONSTRAINT uk_medcin_user UNIQUE (user_id);

ALTER TABLE patient
    ADD CONSTRAINT fk_patient_user
        FOREIGN KEY (user_id) REFERENCES users(id)
            ON DELETE SET NULL;

ALTER TABLE medcin
    ADD CONSTRAINT fk_medcin_user
        FOREIGN KEY (user_id) REFERENCES users(id)
            ON DELETE SET NULL;