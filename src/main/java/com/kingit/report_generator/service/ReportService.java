package com.kingit.report_generator.service;

import com.kingit.report_generator.entity.Report;
import com.kingit.report_generator.exception.NotFinishedReportException;

import java.io.IOException;
import java.time.LocalDate;

public interface ReportService {
    public Report generateReport(LocalDate startDate, LocalDate endDate);

    public String getReportStatus(String reportId);

    public String downloadReport(String reportId) throws NotFinishedReportException, IOException;
}
