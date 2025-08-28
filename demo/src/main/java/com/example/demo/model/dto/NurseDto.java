package com.example.demo.model.dto;

import java.time.LocalDateTime;

public class NurseDto {
    private Long id;
    private String name;
    private String employeeID;
    private LocalDateTime editTIME;

    public NurseDto(Long id, String name, String employeeID, LocalDateTime editTIME) {
        this.id = id;
        this.name = name;
        this.employeeID = employeeID;
        this.editTIME = editTIME;
    }

    public NurseDto() {}

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public LocalDateTime getEditTIME() {
        return editTIME;
    }
}
