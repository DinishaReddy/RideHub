package com.ridehub.rental.services;

import com.ridehub.rental.model.RentalVehicle;
import com.ridehub.rental.repositories.RentalVehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;

@Service
public class RentalVehicleService {

    @Autowired
    private RentalVehicleRepository rentalVehicleRepository;

    private static final String USER_SERVICE_URL = "http://localhost:8081/api/users/";

    public boolean isUserRegistered(String username) {
        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<String> response =
                    restTemplate.getForEntity(USER_SERVICE_URL + username, String.class);
            return response.getStatusCode().is2xxSuccessful();
        } catch (Exception e) {
            return false;
        }
    }

    public RentalVehicle postVehicle(RentalVehicle vehicle, String username) throws Exception {
        if (!isUserRegistered(username)) {
            throw new Exception("User is not registered. Please register to use rental services.");
        }

        RentalVehicle existingVehicle =
                rentalVehicleRepository.findByRegistrationNumber(vehicle.getRegistrationNumber());

        if (existingVehicle != null) {
            throw new Exception("Vehicle is already posted for rent with registration number: "
                    + vehicle.getRegistrationNumber());
        }

        return rentalVehicleRepository.save(vehicle);
    }

    public RentalVehicle updateVehicle(String registrationNumber, RentalVehicle updatedVehicle, String username)
            throws Exception {

        if (!isUserRegistered(username)) {
            throw new Exception("User is not registered. Please register to use rental services.");
        }

        RentalVehicle existingVehicle = rentalVehicleRepository.findByRegistrationNumber(registrationNumber);

        if (existingVehicle == null) {
            throw new Exception("Vehicle not found with registration number: " + registrationNumber);
        }

        existingVehicle.setVehicleType(updatedVehicle.getVehicleType());
        existingVehicle.setFuelType(updatedVehicle.getFuelType());
        existingVehicle.setBrand(updatedVehicle.getBrand());
        existingVehicle.setStartDate(updatedVehicle.getStartDate());
        existingVehicle.setEndDate(updatedVehicle.getEndDate());
        existingVehicle.setTown(updatedVehicle.getTown());
        existingVehicle.setPrice(updatedVehicle.getPrice());

        return rentalVehicleRepository.save(existingVehicle);
    }

    public void deleteVehicle(String registrationNumber, String username) throws Exception {
        if (!isUserRegistered(username)) {
            throw new Exception("User is not registered. Please register to use rental services.");
        }

        RentalVehicle vehicle = rentalVehicleRepository.findByRegistrationNumber(registrationNumber);

        if (vehicle == null) {
            throw new Exception("Vehicle not found with registration number: " + registrationNumber);
        }

        rentalVehicleRepository.delete(vehicle);
    }

    public List<RentalVehicle> getAvailableVehicles(String vehicleType,
                                                    String town,
                                                    LocalDate startDate,
                                                    LocalDate endDate,
                                                    Double startPrice,
                                                    Double endPrice) {
        return rentalVehicleRepository
                .findByVehicleTypeAndTownAndPriceBetweenAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                        vehicleType, town, startPrice, endPrice, endDate, startDate
                );
    }
}