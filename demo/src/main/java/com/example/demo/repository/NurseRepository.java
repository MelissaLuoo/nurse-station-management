package com.example.demo.repository;
import com.example.demo.model.entity.Nurse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NurseRepository extends JpaRepository<Nurse, Long>{
    Optional<Object> findByEmployeeID(String employeeID);
}
