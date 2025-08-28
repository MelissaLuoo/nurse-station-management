package com.example.demo.service;
import com.example.demo.model.dto.NurseDto;
import com.example.demo.model.dto.NurseRequest;
import com.example.demo.model.dto.StationDto;
import com.example.demo.model.entity.Nurse;
import com.example.demo.model.entity.Station;
import com.example.demo.repository.NurseRepository;
import com.example.demo.repository.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class NurseService {
    @Autowired
    private NurseRepository nurseRepository;

    @Autowired
    private StationRepository stationRepository;

    public Optional<Set<StationDto>> findStationsByNurseId(Long nurseId) {
        Optional<Nurse> nurse = nurseRepository.findById(nurseId);
        return nurse.map(n -> n.getStations().stream()
                .map(station -> new StationDto(station.getId(), station.getName(), station.getEditTIME()))
                .collect(Collectors.toSet()));
    }

    public Optional<NurseDto> getNurseById(Long nurseId) {
        Optional<Nurse> nurse = nurseRepository.findById(nurseId);
        return nurse.map(n -> new NurseDto(n.getId(), n.getName(), n.getEmployeeID(), n.getEditTIME()));
    }

    public Nurse createNurse(NurseRequest nurseRequest) {
        if (nurseRepository.findByEmployeeID(nurseRequest.getEmployeeID()).isPresent()) {
            throw new IllegalArgumentException("Employee ID already exists.");
        }

        Nurse nurse = new Nurse();
        nurse.setName(nurseRequest.getName());
        nurse.setEmployeeID(nurseRequest.getEmployeeID());
        System.out.println("Created Nurse ID : " + nurse.getEmployeeID());

        if (nurseRequest.getStationIds() != null && !nurseRequest.getStationIds().isEmpty()) {
            List<Station> stations = stationRepository.findAllById(nurseRequest.getStationIds());
            nurse.setStations(new HashSet<>(stations));
        }

        return nurseRepository.save(nurse);
    }


    public Nurse updateNurse(Long id, NurseRequest nurseRequest) {
        Nurse nurse = nurseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nurse not found with id: " + id));

//        if (newName != null) { nurse.setName(newName);}
        nurse.setName(nurseRequest.getName());
        nurse.setEmployeeID(nurseRequest.getEmployeeID());
        System.out.println("Update Nurse ID : " + nurse.getEmployeeID());
        Set<Station> stations = new HashSet<>(stationRepository.findAllById(nurseRequest.getStationIds()));
        nurse.setStations(stations);

        return nurseRepository.save(nurse);
    }


    public void deleteNurse(Long nurseId) {
        nurseRepository.deleteById(nurseId);
    }

    public List<NurseDto> findAllNurses() {
        List<Nurse> nurses = nurseRepository.findAll();
        return nurses.stream()
                .map(nurse -> new NurseDto(nurse.getId(),nurse.getName(), nurse.getEmployeeID(),nurse.getEditTIME()))
                .collect(Collectors.toList());
    }
}
