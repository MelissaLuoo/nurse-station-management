package com.example.demo.controller;
import com.example.demo.model.dto.NurseDto;
import com.example.demo.model.dto.NurseRequest;
import com.example.demo.model.dto.StationDto;
import com.example.demo.model.entity.Nurse;
import com.example.demo.service.NurseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@RestController
@CrossOrigin(origins = "http://localhost:3000/")
@RequestMapping("/nurses")
public class NurseController {
    @Autowired
    private NurseService nurseService;


    @GetMapping
    public List<NurseDto> getAllNurses() {
        return nurseService.findAllNurses();
    }

    @PostMapping
    public ResponseEntity<Nurse> createNurse(@RequestBody NurseRequest nurseRequest) {
        Nurse createdNurse = nurseService.createNurse(nurseRequest);
        System.out.println("Created Nurse ID : " + nurseRequest.getEmployeeID());
        return new ResponseEntity<>(createdNurse, HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Nurse> updateNurse(@PathVariable Long id, @RequestBody NurseRequest nurseRequest) {
        Nurse updatedNurse = nurseService.updateNurse(id, nurseRequest);
        System.out.println("Update Nurse ID : " + nurseRequest.getEmployeeID());
        return ResponseEntity.ok(updatedNurse);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNurse(@PathVariable Long id) {
        nurseService.deleteNurse(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{nurseId}/stations")
    public ResponseEntity<Set<StationDto>> getNurseStations(@PathVariable Long nurseId) {
        Optional<Set<StationDto>> stations = nurseService.findStationsByNurseId(nurseId);
        return stations.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{nurseId}")
    public ResponseEntity<NurseDto> getNurse(@PathVariable Long nurseId) {
        Optional<NurseDto> nurseDto = nurseService.getNurseById(nurseId);
        return nurseDto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
