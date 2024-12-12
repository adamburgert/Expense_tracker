package org.ppke.itk.expense_tracker.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_seq")
    @SequenceGenerator(name = "category_seq", sequenceName = "categories_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


    public Category() {
        this.updatedAt = LocalDateTime.now();
    }



    public Category(String description, LocalDateTime date, Category category, User user) {
        this.description = description;
        this.date = date;
        this.category = category;
        this.user = user;
        this.updatedAt = LocalDateTime.now();
        this.createdAt = LocalDateTime.now();
    }












}
