package com.example.demo.controller;
import com.example.demo.model.dto.NurseDto;
import com.example.demo.model.dto.StationDto;
import com.example.demo.model.dto.StationRequest;
import com.example.demo.model.entity.Nurse;
import com.example.demo.model.entity.Station;
import com.example.demo.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@RestController
@CrossOrigin(origins = "http://localhost:3000/")
@RequestMapping("/stations")
public class StationController {

    @Autowired
    private StationService stationService;

    @GetMapping
    public List<StationDto> getAllStations() {
        return stationService.findAllStations();
    }

    @PostMapping
    public ResponseEntity<Station> createStation(@RequestBody StationRequest stationRequest) {
        Station createdStation = stationService.createStation(stationRequest);
        return new ResponseEntity<>(createdStation, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Station> updateStation(@PathVariable Long id, @RequestBody StationRequest stationRequest) {
        Station updatedStation = stationService.updateStation(id, stationRequest);
        return ResponseEntity.ok(updatedStation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStation(@PathVariable Long id) {
        stationService.deleteStation(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{stationId}/nurses")
    public ResponseEntity<Set<NurseDto>> getStationNurses(@PathVariable Long stationId) {
        Optional<Set<NurseDto>> nurses = stationService.findNursesByStationId(stationId);
        return nurses.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{stationId}")
    public ResponseEntity<String> getStationName(@PathVariable Long stationId) {
        Optional<String> stationName = stationService.getStationNameById(stationId);
        return stationName.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
