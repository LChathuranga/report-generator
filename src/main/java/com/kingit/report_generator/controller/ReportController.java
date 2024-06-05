package com.kingit.report_generator.controller;

import com.kingit.report_generator.entity.Report;
import com.kingit.report_generator.service.ReportService;
import com.kingit.report_generator.validation.CustomDateValidator;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
           CustomDateValidator.validateReportDates(startDate, endDate);
           Report report = reportService.generateReport(LocalDate.parse(startDate), LocalDate.parse(endDate));
           return ResponseEntity.ok().body(report.getId());
       } catch (BadRequestException | IllegalArgumentException e){
           return ResponseEntity.badRequest().body(e.getMessage());
       }
    }
}
