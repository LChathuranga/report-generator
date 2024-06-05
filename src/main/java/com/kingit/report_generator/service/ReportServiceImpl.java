package com.kingit.report_generator.service;

import com.kingit.report_generator.entity.Report;
import com.kingit.report_generator.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

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

    private void startReportGeneration(Report report){
        Thread reportGenerationThread = new Thread(() -> {
           try{
               // Todo
               // Generate report by using reportId, startDate and endDate
               int delay = (int) (Math.random() * 10000 + 8000);
               System.out.println(delay);
               System.out.println("Thread is started");
               Thread.sleep(delay);
               report.setStatus("Finished");
               reportRepository.save(report);
           } catch (Exception e){
               report.setStatus("Failed");
               reportRepository.save(report);
               e.printStackTrace();
           }
        });

        reportGenerationThread.start();
    }

    private String generateReportId(){
        return UUID.randomUUID().toString();
    }
}
