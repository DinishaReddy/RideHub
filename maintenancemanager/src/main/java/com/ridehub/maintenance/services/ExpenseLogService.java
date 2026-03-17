package com.ridehub.maintenance.services;

import com.ridehub.maintenance.model.ExpenseLog;
import com.ridehub.maintenance.repositories.ExpenseLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseLogService {

    @Autowired
    private ExpenseLogRepository expenseLogRepository;

    private static final String VEHICLE_MANAGER_URL = "http://vehiclemanager:8081/api/vehicles/registration/";

    public boolean isVehicleRegistered(String registrationNumber) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            restTemplate.getForObject(VEHICLE_MANAGER_URL + registrationNumber, String.class);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public ExpenseLog addOrUpdateExpenseLog(String registrationNumber,
                                            Double maintenanceCharges,
                                            Double fuelCharges,
                                            Double kilometersDriven,
                                            LocalDate monthYear) {

        if (!isVehicleRegistered(registrationNumber)) {
            throw new RuntimeException("Vehicle is not registered.");
        }

        Optional<ExpenseLog> existingLog =
                expenseLogRepository.findByRegistrationNumberAndMonthYear(registrationNumber, monthYear);

        ExpenseLog expenseLog = existingLog.orElse(new ExpenseLog());

        expenseLog.setRegistrationNumber(registrationNumber);
        expenseLog.setMaintenanceCharges(maintenanceCharges);
        expenseLog.setFuelCharges(fuelCharges);
        expenseLog.setKilometersDriven(kilometersDriven);
        expenseLog.setMonthYear(monthYear);

        return expenseLogRepository.save(expenseLog);
    }

    public List<ExpenseLog> getExpenseLogsForMonth(int year, int month) {
        return expenseLogRepository.findExpenseLogsByYearAndMonth(year, month);
    }
}