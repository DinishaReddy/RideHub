package com.ridehub.rental.repositories;

import com.ridehub.rental.model.RentalVehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface RentalVehicleRepository extends JpaRepository<RentalVehicle, Long> {

    RentalVehicle findByRegistrationNumber(String registrationNumber);

    List<RentalVehicle> findByVehicleTypeAndTownAndPriceBetweenAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
            String vehicleType,
            String town,
            Double startPrice,
            Double endPrice,
            LocalDate endDate,
            LocalDate startDate
    );
}