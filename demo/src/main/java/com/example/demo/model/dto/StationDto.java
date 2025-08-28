package com.example.demo.model.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class StationDto {
    private Long id;
    private String name;
    private LocalDateTime editTIME;

    public StationDto(Long id, String name, LocalDateTime editTIME) {
        this.id = id;
        this.name = name;
        this.editTIME = editTIME;
    }

    public StationDto() {

    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public LocalDateTime getEditTIME() {
        return editTIME;
    }

    public void setId(Long id) {
    }

    public void setName(String name) {
    }
}
