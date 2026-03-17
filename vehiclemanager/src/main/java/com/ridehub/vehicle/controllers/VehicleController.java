package com.ridehub.vehicle.controllers;

import com.ridehub.vehicle.exceptions.UserNotFoundException;
import com.ridehub.vehicle.model.Vehicle;
import com.ridehub.vehicle.services.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @PostMapping("/register")
    public ResponseEntity<?> registerVehicle(@RequestBody Vehicle vehicle, @RequestParam String username) {
        try {
            Vehicle registeredVehicle = vehicleService.registerVehicle(vehicle, username);
            return ResponseEntity.ok(registeredVehicle);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateVehicle(@PathVariable Long id,
                                           @RequestBody Vehicle vehicle,
                                           @RequestParam String username) {
        try {
            Vehicle updatedVehicle = vehicleService.updateVehicle(id, vehicle, username);
            return ResponseEntity.ok(updatedVehicle);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/by-username/{username}")
    public ResponseEntity<?> getVehiclesByUsername(@PathVariable String username) {
        try {
            List<Vehicle> vehicles = vehicleService.getVehiclesByUsername(username);
            return ResponseEntity.ok(vehicles);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteVehicle(@RequestParam String registrationNumber,
                                           @RequestParam String username) {
        try {
            vehicleService.deleteVehicleByRegistrationNumber(registrationNumber, username);
            return ResponseEntity.ok("Vehicle deleted successfully");
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/registration/{registrationNumber}")
    public ResponseEntity<?> getVehicleByRegistrationNumber(@PathVariable String registrationNumber) {
        try {
            Vehicle vehicle = vehicleService.getVehicleByRegistrationNumber(registrationNumber);
            return ResponseEntity.ok(vehicle);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}