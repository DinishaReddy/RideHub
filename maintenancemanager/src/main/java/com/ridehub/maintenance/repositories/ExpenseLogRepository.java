package com.ridehub.maintenance.repositories;

import com.ridehub.maintenance.model.ExpenseLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ExpenseLogRepository extends JpaRepository<ExpenseLog, Long> {

    @Query("SELECT e FROM ExpenseLog e WHERE YEAR(e.monthYear) = :year AND MONTH(e.monthYear) = :month")
    List<ExpenseLog> findExpenseLogsByYearAndMonth(int year, int month);

    Optional<ExpenseLog> findByRegistrationNumberAndMonthYear(String registrationNumber, LocalDate monthYear);
}