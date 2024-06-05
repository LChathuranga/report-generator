package com.kingit.report_generator.controller;

import com.kingit.report_generator.entity.Report;
import com.kingit.report_generator.exception.NotFinishedReportException;
import com.kingit.report_generator.exception.ReportNotFoundException;
import com.kingit.report_generator.service.ReportService;
import com.kingit.report_generator.validation.CustomDataValidator;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;

@RestController
@RequestMapping("/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;
    @GetMapping("/")
    public ResponseEntity<String> generateReport(
            @RequestParam(value = "startDate") String startDate,
            @RequestParam(value = "endDate") String endDate) {
       try{
           CustomDataValidator.validateReportDates(startDate, endDate);
           Report report = reportService.generateReport(LocalDate.parse(startDate), LocalDate.parse(endDate));
           return ResponseEntity.ok().body(report.getId());
       } catch (BadRequestException | IllegalArgumentException e){
           return ResponseEntity.badRequest().body(e.getMessage());
       } catch (Exception e) {
           return ResponseEntity.internalServerError().body(e.getMessage());
       }
    }

    @GetMapping("/{reportId}/status")
    public ResponseEntity<String> getReportStatus(@PathVariable() String reportId){
        try{
            CustomDataValidator.validateReportId(reportId);
            String reportStatus = reportService.getReportStatus(reportId);
            return ResponseEntity.ok().body(reportStatus);
        } catch (BadRequestException | ReportNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/{reportId}/download")
    public ResponseEntity<String> downloadGeneratedReport(@PathVariable() String reportId){
        try{
            CustomDataValidator.validateReportId(reportId);
            String filePath = reportService.downloadReport(reportId);
            byte[] fileContent = Files.readAllBytes(Paths.get(filePath));
            String textContent = new String(fileContent, StandardCharsets.UTF_8);

            return ResponseEntity.ok().contentType(MediaType.TEXT_PLAIN)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filePath)
                    .body(textContent);
        } catch (BadRequestException | ReportNotFoundException | NotFinishedReportException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
