package org.ppke.itk.expense_tracker.Repositories;

import org.ppke.itk.expense_tracker.domain.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

    Optional<Report> findTopByOrderByCreatedAtDesc();
}
