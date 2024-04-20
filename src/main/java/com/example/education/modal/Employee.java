package com.example.education.modal;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstname;
    private String lastname;
    private String ditrict;
    private String sector;
    private String cell;
    private String village;
    private String identityNo;
    private String phone;
    

    @ManyToOne
    @JoinColumn(name = "school_id")
    private School school;
}
