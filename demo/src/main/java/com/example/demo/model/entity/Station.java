package com.example.demo.model.entity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.HashSet;
import java.time.LocalDate;

@Entity
@Table(name = "stations")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Station {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @CreationTimestamp
    private LocalDate createdAt;

    @UpdateTimestamp
    @Column(name = "EDIT_TIME")
    private LocalDateTime editTIME;

    @ManyToMany(mappedBy = "stations")
    @JsonBackReference
    private Set<Nurse> nurses = new HashSet<>();

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public LocalDateTime getEditTIME() { return editTIME; }
    public void setEditTIME(LocalDateTime EDIT_TIME) { this.editTIME = EDIT_TIME; }

    public Set<Nurse> getNurses() { return nurses; }
    public void setNurses(Set<Nurse> nurses) { this.nurses = nurses; }
}
