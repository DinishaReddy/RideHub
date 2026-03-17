package com.ridehub.maintenance.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class ExpenseLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String registrationNumber;

    @Column(nullable = false)
    private Double maintenanceCharges;

    @Column(nullable = false)
    private Double fuelCharges;

    @Column(nullable = false)
    private Double kilometersDriven;

    @Column(nullable = false)
    private LocalDate monthYear;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public Double getMaintenanceCharges() {
        return maintenanceCharges;
    }

    public void setMaintenanceCharges(Double maintenanceCharges) {
        this.maintenanceCharges = maintenanceCharges;
    }

    public Double getFuelCharges() {
        return fuelCharges;
    }

    public void setFuelCharges(Double fuelCharges) {
        this.fuelCharges = fuelCharges;
    }

    public Double getKilometersDriven() {
        return kilometersDriven;
    }

    public void setKilometersDriven(Double kilometersDriven) {
        this.kilometersDriven = kilometersDriven;
    }

    public LocalDate getMonthYear() {
        return monthYear;
    }

    public void setMonthYear(LocalDate monthYear) {
        this.monthYear = monthYear;
    }
}