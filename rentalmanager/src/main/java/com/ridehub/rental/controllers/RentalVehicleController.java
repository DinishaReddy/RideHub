package com.ridehub.rental.controllers;

import com.ridehub.rental.model.RentalVehicle;
import com.ridehub.rental.services.RentalVehicleService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/rentals")
public class RentalVehicleController {

    @Autowired
    private RentalVehicleService rentalVehicleService;

    @PostMapping("/post")
    public ResponseEntity<?> postVehicle(@RequestBody RentalVehicle vehicle, HttpSession session) {
        try {
            String username = (String) session.getAttribute("username");

            if (username == null) {
                return new ResponseEntity<>("User is not logged in. Please log in to post a vehicle.",
                        HttpStatus.UNAUTHORIZED);
            }

            RentalVehicle postedVehicle = rentalVehicleService.postVehicle(vehicle, username);
            return ResponseEntity.ok(postedVehicle);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{registrationNumber}")
    public ResponseEntity<?> updateVehicle(@PathVariable String registrationNumber,
                                           @RequestBody RentalVehicle updatedVehicle,
                                           HttpSession session) {
        try {
            String username = (String) session.getAttribute("username");

            if (username == null) {
                return new ResponseEntity<>("User is not logged in. Please log in to update a vehicle.",
                        HttpStatus.UNAUTHORIZED);
            }

            RentalVehicle vehicle = rentalVehicleService.updateVehicle(registrationNumber, updatedVehicle, username);
            return ResponseEntity.ok(vehicle);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{registrationNumber}")
    public ResponseEntity<?> deleteVehicle(@PathVariable String registrationNumber, HttpSession session) {
        try {
            String username = (String) session.getAttribute("username");

            if (username == null) {
                return new ResponseEntity<>("User is not logged in. Please log in to delete a vehicle.",
                        HttpStatus.UNAUTHORIZED);
            }

            rentalVehicleService.deleteVehicle(registrationNumber, username);
            return ResponseEntity.ok("Vehicle deleted successfully!");

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/available")
    public ResponseEntity<?> getAvailableVehicles(
            @RequestParam String vehicleType,
            @RequestParam String town,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam Double startPrice,
            @RequestParam Double endPrice) {
        try {
            List<RentalVehicle> vehicles = rentalVehicleService.getAvailableVehicles(
                    vehicleType, town, startDate, endDate, startPrice, endPrice
            );
            return ResponseEntity.ok(vehicles);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}