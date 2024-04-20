package com.example.education.modal;

import com.example.education.enumeration.UserRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @ManyToOne
    @JoinColumn(name = "school_id")
    private School school;

    
    @ManyToOne
    @JoinColumn(name = "district_id")
    private District district; // Indicates the district of the user
}
