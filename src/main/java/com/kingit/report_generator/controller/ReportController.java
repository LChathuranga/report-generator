package com.kingit.report_generator.controller;

import com.kingit.report_generator.entity.Report;
import com.kingit.report_generator.exception.ReportNotFoundException;
import com.kingit.report_generator.service.ReportService;
import com.kingit.report_generator.validation.CustomDataValidator;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            System.out.println(reportStatus);
            return ResponseEntity.ok().body(reportStatus);
        } catch (BadRequestException | ReportNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
