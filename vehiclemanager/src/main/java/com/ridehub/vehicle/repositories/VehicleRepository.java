package com.ridehub.vehicle.repositories;

import com.ridehub.vehicle.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    List<Vehicle> findByUsername(String username);
    Optional<Vehicle> findByRegistrationNumber(String registrationNumber);
    Optional<Vehicle> findByIdAndUsername(Long id, String username);
    Optional<Vehicle> findByRegistrationNumberAndUsername(String registrationNumber, String username);
}