package com.example.demo.model.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.HashSet;
import java.time.LocalDate;

@Entity
@Table(name = "nurses")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Nurse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(name = "EMPLOYEE_ID")
    private String employeeID;

    @CreationTimestamp
    private LocalDate createdAt;

    @UpdateTimestamp
    @Column(name = "EDIT_TIME")
    private LocalDateTime editTIME;
//    , columnDefinition = "DATETIME(6)"

    @ManyToMany
    @JoinTable(
            name = "ns_allocate",
            joinColumns = @JoinColumn(name = "nurse_id"),
            inverseJoinColumns = @JoinColumn(name = "station_id")
    )
    @JsonManagedReference
    private Set<Station> stations = new HashSet<>();

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmployeeID() { return employeeID; }
    public void setEmployeeID(String employeeID) { this.employeeID = employeeID; }

    public LocalDateTime getEditTIME() { return editTIME; }
    public void setEditTIME(LocalDateTime editTIME) { this.editTIME = editTIME; }

    public Set<Station> getStations() { return stations; }
    public void setStations(Set<Station> stations) { this.stations = stations; }
}
