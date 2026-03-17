package com.ridehub.maintenance.controllers;

import com.ridehub.maintenance.model.ExpenseLog;
import com.ridehub.maintenance.services.ExpenseLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseLogController {

    @Autowired
    private ExpenseLogService expenseLogService;

    @PostMapping("/log")
    public ResponseEntity<?> addOrUpdateExpenseLog(
            @RequestParam String registrationNumber,
            @RequestParam Double maintenanceCharges,
            @RequestParam Double fuelCharges,
            @RequestParam Double kilometersDriven,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate monthYear) {
        try {
            ExpenseLog expenseLog = expenseLogService.addOrUpdateExpenseLog(
                    registrationNumber,
                    maintenanceCharges,
                    fuelCharges,
                    kilometersDriven,
                    monthYear
            );
            return ResponseEntity.ok(expenseLog);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/month")
    public ResponseEntity<?> getExpenseLogsForMonth(@RequestParam int year, @RequestParam int month) {
        try {
            List<ExpenseLog> expenseLogs = expenseLogService.getExpenseLogsForMonth(year, month);
            return ResponseEntity.ok(expenseLogs);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}