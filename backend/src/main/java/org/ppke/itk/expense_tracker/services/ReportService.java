package org.ppke.itk.expense_tracker.services;

import org.ppke.itk.expense_tracker.domain.Expense;
import org.ppke.itk.expense_tracker.domain.Report;
import org.ppke.itk.expense_tracker.Repositories.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReportService {

    private final ReportRepository reportRepository;
    private final ExpenseService expenseService;

    @Autowired
    public ReportService(ReportRepository reportRepository, ExpenseService expenseService) {
        this.reportRepository = reportRepository;
        this.expenseService = expenseService;
    }

    public void deleteReport(Long id) {
        if (!reportRepository.existsById(id)) {
            throw new ReportNotFoundException("Report with ID " + id + " does not exist.");
        }
        reportRepository.deleteById(id);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    public class ReportNotFoundException extends RuntimeException {
        public ReportNotFoundException(String message) {
            super(message);
        }
    }


    public Report generateExpenseReport() {
        List<Expense> expenses = expenseService.getAllExpenses();
        double totalAmount = expenses.stream().mapToDouble(Expense::getTotalPrice).sum();

        Report report = new Report();
        report.setDescription("Expense Report for " + expenses.size() + " entries");
        report.setTotalAmount(totalAmount);
        report.setExpenses(expenses);

        return reportRepository.save(report);
    }
    public Report updateReport(Long id, Report updatedReport) {
        return reportRepository.findById(id).map(existingReport -> {
            // Update fields if they are provided in the request body
            if (updatedReport.getReportType() != null) {
                existingReport.setReportType(updatedReport.getReportType());
            }
            if (updatedReport.getFilePath() != null) {
                existingReport.setFilePath(updatedReport.getFilePath());
            }

            existingReport.setUpdatedAt(LocalDateTime.now()); // Update timestamp
            return reportRepository.save(existingReport);
        }).orElseThrow(() -> new ReportNotFoundException("Report with ID " + id + " not found."));
    }




    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }


    public Report getLatestReport() {
        return reportRepository.findTopByOrderByCreatedAtDesc().orElse(null);
    }
}


