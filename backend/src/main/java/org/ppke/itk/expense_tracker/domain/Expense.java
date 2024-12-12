package org.ppke.itk.expense_tracker.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "expenses")
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "expenses_seq_gen")
    @SequenceGenerator(name = "expenses_seq_gen", sequenceName = "expenses_seq", allocationSize = 1)
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "amount")
    private Integer amount;

    @Column(name = "price")
    private Double price;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "total_Price", columnDefinition = "double default 0")
    private Double totalPrice;

    @Column(name = "min_price", columnDefinition = "double default 0")
    private Double minPrice;

    @Column(name = "max_price", columnDefinition = "double default 0")
    private Double maxPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "report_id")
    private Report report;

    public Expense() {
        this.updatedAt = LocalDateTime.now();
    }

    public Expense(String description, Integer amount, Double price, LocalDateTime date, Category category, User user) {
        this.description = description;
        this.amount = amount;
        this.price = price;
        this.date = date;
        this.category = category;
        this.user = user;
        this.updatedAt = LocalDateTime.now();
        this.price = price;
    }



    public Double getTotalPrice() {
        return this.amount * this.price;
    }












}
