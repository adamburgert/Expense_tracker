package org.ppke.itk.expense_tracker.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Entity
public class Report {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    private LocalDateTime createdAt;
    @Setter
    private LocalDateTime updatedAt;
    private String description;
    private Double totalAmount;

    @Getter
    @Setter
    private String reportType;
    @Setter
    @Getter
    private String filePath;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "report_id")
    private List<Expense> expenses;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
    }


}
