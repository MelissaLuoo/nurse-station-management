package com.example.demo.service;
import com.example.demo.model.dto.NurseDto;
import com.example.demo.model.dto.StationDto;
import com.example.demo.model.dto.StationRequest;
import com.example.demo.model.entity.Station;
import com.example.demo.repository.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StationService {
    @Autowired
    private StationRepository stationRepository;

    public Station createStation(StationRequest stationRequest) {
        Station station = new Station();
        station.setName(stationRequest.getName());
        return stationRepository.save(station);
    }

    public Station updateStation(Long id, StationRequest stationRequest) {
        Station station = stationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Station not found with id: " + id));
        station.setName(stationRequest.getName());
        return stationRepository.save(station);
    }

    public Optional<String> getStationNameById(Long stationId) {
        return stationRepository.findById(stationId)
                .map(Station::getName);
    }

    public Optional<Set<NurseDto>> findNursesByStationId(Long stationId) {
        Optional<Station> station = stationRepository.findById(stationId);
//        return station.map(Station::getNurses);
        return station.map(s -> s.getNurses().stream()
                .map(nurse -> new NurseDto(nurse.getId(),nurse.getName(), nurse.getEmployeeID(), nurse.getEditTIME()))
                .collect(Collectors.toSet()));
    }

    public void deleteStation(Long stationId) {
        stationRepository.deleteById(stationId);
    }

    public List<StationDto> findAllStations() {
//        return stationRepository.findAll();
        List<Station> stations = stationRepository.findAll();
        return stations.stream()
                .map(station -> new StationDto(station.getId(), station.getName(), station.getEditTIME()))
                .collect(Collectors.toList());
    }
}
