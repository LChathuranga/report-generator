package com.kingit.report_generator.service;

import com.kingit.report_generator.entity.Report;

import java.time.LocalDate;

public interface ReportService {
    public Report generateReport(LocalDate startDate, LocalDate endDate);
}
