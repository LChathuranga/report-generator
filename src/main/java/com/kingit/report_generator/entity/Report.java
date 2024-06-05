package com.kingit.report_generator.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.sql.Date;
import java.time.LocalDate;

@Entity
public class Report {
    @Id
    private String id;

    private String name;

    private LocalDate startDate;

    private LocalDate endDate;

    private String description;

    private String status;

    protected Report(){}

    public Report(final String id, final String name, final LocalDate startDate, final LocalDate endDate, final String description, final String status) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
