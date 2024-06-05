package com.kingit.report_generator.service;

import com.kingit.report_generator.entity.Report;
import com.kingit.report_generator.exception.NotFinishedReportException;
import com.kingit.report_generator.exception.ReportNotFoundException;
import com.kingit.report_generator.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

import static com.kingit.report_generator.utils.Utility.*;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportRepository reportRepository;

    public Report generateReport(LocalDate startDate, LocalDate endDate){
        String reportId = generateReportId();
        Report report = new Report(reportId, "KIS-" + reportId, startDate, endDate, "Generated report for start date: " + startDate + " end date: " + endDate, "In progress");
        startReportGeneration(report);
        return reportRepository.save(report);
    }

    @Override
    public String getReportStatus(String reportId) throws ReportNotFoundException {
        Optional<Report> optionalReport = reportRepository.findById(reportId);
        if(optionalReport.isPresent()){
            Report report = optionalReport.get();
            return report.getStatus();
        } else {
            throw new ReportNotFoundException("Report with ID: " + reportId + " not found");
        }
    }

    public String downloadReport(String reportId) throws NotFinishedReportException {
        String basePath = Paths.get("").toAbsolutePath() + "\\generated_reports\\";

        Optional<Report> optionalReport = reportRepository.findById(reportId);
        if(optionalReport.isEmpty()) {
            throw new ReportNotFoundException("Report with ID: " + reportId + " not found");
        }

        Report report = optionalReport.get();

        if(!Objects.equals(report.getStatus(), "Finished")){
            throw new NotFinishedReportException();
        }
        String filePath = basePath + report.getName();

        if(Files.exists(Path.of(filePath))) {
            return filePath;
        } else {
            String content = generateReportPath(report);
            try {
                createTextFile(content, basePath, report.getName());
                return filePath;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void startReportGeneration(Report report){
        Thread reportGenerationThread = new Thread(() -> {
           try{
               // Todo
               // Generate report by using reportId, startDate and endDate
               int delay = (int) (Math.random() * 10000 + 8000);
               Thread.sleep(delay);
               report.setStatus("Finished");
               reportRepository.save(report);
           } catch (Exception e){
               report.setStatus("Failed");
               reportRepository.save(report);
           }
        });

        reportGenerationThread.start();
    }
}
