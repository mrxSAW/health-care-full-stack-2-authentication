package com.example.HealthCareApp.DTO.Medcin;

import lombok.Data;

import java.io.Serializable;

@Data
public class MedcinGetDTO implements Serializable {

    private static final long serialVersionUID=1L;


    private int id;
    private String nom;
    private String specialite;
    private String email;
    private String telephone;


}
