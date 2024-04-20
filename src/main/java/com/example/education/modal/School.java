package com.example.education.modal;

import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class School {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String schoolCode;
    private String name;
    private String district;
    private String sector;
    private String cell;
    private String village;
    @OneToMany(mappedBy = "school")
    private List<Department> departments;
    
    @OneToMany(mappedBy = "school")
    private List<Employee> employees;

    
}